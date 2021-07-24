package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Function3
import java.util.*

sealed class DataContainer<T> {
    companion object {
        const val NAME_OK = "Ok"
        const val NAME_PENDING = "Pending"
        const val NAME_ERROR = "Error"
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other === this) return true
        if (other.javaClass != this.javaClass) return false

        return this.exec(object : Visitor<T, Boolean> {
            override fun visitOk(v: Ok<T>): Boolean {
                val o = other as Ok<*>
                return if (v.data != null) v.data.equals(o.data) else o.data == null
            }

            override fun visitPending(v: Pending<T>): Boolean = true // all Pending are equal
            override fun visitError(v: Error<T>): Boolean = v.er.equals((other as Error<*>).er)
        })
    }

    override fun hashCode(): Int {
        return this.exec(object : Visitor<T, Int> {
            override fun visitOk(v: Ok<T>): Int = 31 * v.data.hashCode()
            override fun visitPending(v: Pending<T>): Int = 123
            override fun visitError(v: Error<T>): Int = 17 * v.er.hashCode()
        })
    }

    interface Visitor<D, R> {
        fun visitOk(v: DataContainer.Ok<D>): R
        fun visitPending(v: DataContainer.Pending<D>): R
        fun visitError(v: DataContainer.Error<D>): R
    }

    class Ok<D>(val data: D) : DataContainer<D>() {
        override fun toString(): String = "Ok: $data"
        override fun stateName(): String = NAME_OK
        override fun <R> exec(visitor: Visitor<D, R>): R = visitor.visitOk(this)
    }

    class Pending<D> : DataContainer<D>() {
        override fun toString(): String = "Pending"
        override fun stateName(): String = NAME_PENDING
        override fun <R> exec(visitor: Visitor<D, R>): R = visitor.visitPending(this)
    }

    class Error<D>(val er: String) : DataContainer<D>() {
        override fun toString(): String = "Error: \"$er\""
        override fun stateName(): String = NAME_ERROR
        override fun <R> exec(visitor: Visitor<D, R>): R = visitor.visitError(this)
    }

    abstract fun <R> exec(visitor: Visitor<T, R>): R

    /**
     * @return state name only; not affected by container content
     */
    abstract fun stateName(): String
}

class OkOnlyPassingTransformer<T> : FlowableTransformer<DataContainer<T>, T> {
    override fun apply(upstream: Flowable<DataContainer<T>>?): @NonNull Flowable<T>? {
        return upstream?.flatMap { dc ->
            val outFlow: Flowable<T> = when (dc) {
                is DataContainer.Ok<*> -> Flowable.just(dc.data as T)
                is DataContainer.Pending -> Flowable.empty()
                is DataContainer.Error -> Flowable.empty()
                else -> throw IllegalStateException("Unhandled data container: $dc")
            }
            return@flatMap outFlow
        }
    }
}

open class NonReturningDataContainerVisitor<D> : DataContainer.Visitor<D, Unit> {
    override fun visitOk(v: DataContainer.Ok<D>) = Unit
    override fun visitPending(v: DataContainer.Pending<D>) = Unit
    override fun visitError(v: DataContainer.Error<D>) = Unit
}

// Internal for unit testing
internal enum class ContainerType { Ok, Pending, Error }
internal class TypeResolutionVisitor<T> : DataContainer.Visitor<T, ContainerType> {
    override fun visitOk(v: DataContainer.Ok<T>): ContainerType = ContainerType.Ok
    override fun visitPending(v: DataContainer.Pending<T>): ContainerType = ContainerType.Pending
    override fun visitError(v: DataContainer.Error<T>): ContainerType = ContainerType.Error
}

internal val anyTypeResolutionVisitor = TypeResolutionVisitor<Any?>()

internal fun resolveContainersType(vararg container: DataContainer<Any?>): ContainerType {
    var result: ContainerType = ContainerType.Ok
    for (c in container) {
        val t = c.exec(anyTypeResolutionVisitor)
        when (t) {
            ContainerType.Error -> return ContainerType.Error
            ContainerType.Pending -> result = ContainerType.Pending
            ContainerType.Ok -> {
            }
        }
    }

    return result
}

internal fun composeDataContainer(vararg containers: DataContainer<*>): DataContainer<Boolean> {
    val errors = LinkedList<String>()
    var hasPending = false
    for (c in containers) {
        when (c) {
            is DataContainer.Pending -> hasPending = true
            is DataContainer.Error -> errors.add(c.er)
        }
    }

    if (errors.isNotEmpty()) {
        return DataContainer.Error<Boolean>("{${errors.joinToString("}, {")}}")
    }
    return if (hasPending) DataContainer.Pending() else DataContainer.Ok(true)
}

private class ExtractDataValueVisitor<R> : DataContainer.Visitor<R, R> {
    override fun visitOk(v: DataContainer.Ok<R>): R = v.data
    override fun visitPending(v: DataContainer.Pending<R>): R = throw IllegalStateException("Data container is not Ok but Pending")
    override fun visitError(v: DataContainer.Error<R>): R = throw IllegalStateException("Data container is not Ok but Error")
}

class DataContainerConverter<T1, R>(private val mapper: Function<T1, R>) : DataContainer.Visitor<T1, DataContainer<R>> {
    override fun visitOk(v: DataContainer.Ok<T1>): DataContainer<R> = DataContainer.Ok(mapper.apply(v.data))
    override fun visitPending(v: DataContainer.Pending<T1>): DataContainer<R> = DataContainer.Pending()
    override fun visitError(v: DataContainer.Error<T1>): DataContainer<R> = DataContainer.Error(v.er)
}

fun <T1, R> convertDataContainer(dc: DataContainer<T1>, mapper: Function<T1, R>): DataContainer<R> {
    return when (dc) {
        is DataContainer.Pending<T1> -> DataContainer.Pending()
        is DataContainer.Error -> DataContainer.Error(dc.er)
        is DataContainer.Ok -> DataContainer.Ok(mapper.apply(dc.data))
    }
}

fun <T1, T2, R> convert2DataContainers(dc1: DataContainer<T1>, dc2: DataContainer<T2>, mapper: BiFunction<T1, T2, R>): DataContainer<R> {
    val compose: DataContainer<Boolean> = composeDataContainer(dc1, dc2)
    return when (compose) {
        is DataContainer.Pending -> DataContainer.Pending()
        is DataContainer.Error<Boolean> -> DataContainer.Error(compose.er)
        is DataContainer.Ok -> if (compose.data) {
            val d1 = (dc1 as DataContainer.Ok).data
            val d2 = (dc2 as DataContainer.Ok).data
            val res = mapper.apply(d1, d2)
            DataContainer.Ok(res)
        } else {
            throw IllegalStateException("Could not compose")
        }
    }
}

fun <T1, T2, T3, R> convert3DataContainers(dc1: DataContainer<T1>, dc2: DataContainer<T2>, dc3: DataContainer<T3>, mapper: Function3<T1, T2, T3, R>): DataContainer<R> {
    val compose = composeDataContainer(dc1, dc2)
    return when (compose) {
        is DataContainer.Pending -> DataContainer.Pending()
        is DataContainer.Error<Boolean> -> DataContainer.Error(compose.er)
        is DataContainer.Ok -> if (compose.data) {
            val d1 = (dc1 as DataContainer.Ok).data
            val d2 = (dc2 as DataContainer.Ok).data
            val d3 = (dc3 as DataContainer.Ok).data
            val res = mapper.apply(d1, d2, d3)
            DataContainer.Ok(res)
        } else {
            throw IllegalStateException("Could not compose")
        }
    }
}
