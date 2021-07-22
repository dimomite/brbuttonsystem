package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Function3
import java.util.*

sealed class DataContainer<out T> {
    interface Visitor<R> {
        fun <D> visitOk(v: DataContainer.Ok<D>): R
        fun visitPending(v: DataContainer.Pending): R
        fun visitError(v: DataContainer.Error): R
    }

    class Ok<D>(val data: D) : DataContainer<D>() {
        override fun toString(): String = "Ok: $data"
        override fun <R> exec(visitor: Visitor<R>): R = visitor.visitOk(this)
    }

    object Pending : DataContainer<Nothing>() {
        override fun toString(): String = "Pending"
        override fun <R> exec(visitor: Visitor<R>): R = visitor.visitPending(this)
    }

    class Error(val er: String) : DataContainer<Nothing>() {
        override fun toString(): String = "Error: \"$er\""
        override fun <R> exec(visitor: Visitor<R>): R = visitor.visitError(this)
    }

    abstract fun <R> exec(visitor: Visitor<R>): R
}

open class NonReturningDataContainerVisitor : DataContainer.Visitor<Unit> {
    override fun <D> visitOk(v: DataContainer.Ok<D>) = Unit
    override fun visitPending(v: DataContainer.Pending) = Unit
    override fun visitError(v: DataContainer.Error) = Unit
}

// Internal for unit testing
internal enum class ContainerType { Ok, Pending, Error }
internal object typeResolutionVisitor : DataContainer.Visitor<ContainerType> {
    override fun <D> visitOk(v: DataContainer.Ok<D>): ContainerType = ContainerType.Ok
    override fun visitPending(v: DataContainer.Pending): ContainerType = ContainerType.Pending
    override fun visitError(v: DataContainer.Error): ContainerType = ContainerType.Error
}

internal fun resolveContainersType(vararg container: DataContainer<*>): ContainerType {
    var result: ContainerType = ContainerType.Ok
    for (c in container) {
        val t = c.exec(typeResolutionVisitor)
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
            DataContainer.Pending -> hasPending = true
            is DataContainer.Error -> errors.add(c.er)
        }
    }

    if (errors.isNotEmpty()) {
        return DataContainer.Error("{${errors.joinToString("}, {")}}")
    }
    return if (hasPending) DataContainer.Pending else DataContainer.Ok(true)
}

private class ExtractDataValueVisitor<R> : DataContainer.Visitor<R> {
    override fun <D> visitOk(v: DataContainer.Ok<D>): R = v.data as R
    override fun visitPending(v: DataContainer.Pending): R = throw IllegalStateException("Data container is not Ok but Pending")
    override fun visitError(v: DataContainer.Error): R = throw IllegalStateException("Data container is not Ok but Error")
}

fun <T1, R> convertDataContainer(dc: DataContainer<T1>, mapper: Function<T1, R>): DataContainer<R> {
    return when (dc) {
        DataContainer.Pending -> DataContainer.Pending
        is DataContainer.Error -> DataContainer.Error(dc.er)
        is DataContainer.Ok -> DataContainer.Ok(mapper.apply(dc.data))
    }
}

fun <T1, T2, R> convert2DataContainers(dc1: DataContainer<T1>, dc2: DataContainer<T2>, mapper: BiFunction<T1, T2, R>): DataContainer<R> {
    val compose = composeDataContainer(dc1, dc2)
    return when (compose) {
        DataContainer.Pending -> DataContainer.Pending
        is DataContainer.Error -> DataContainer.Error(compose.er)
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
        DataContainer.Pending -> DataContainer.Pending
        is DataContainer.Error -> DataContainer.Error(compose.er)
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
