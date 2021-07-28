package org.dimomite.brbuttonsystem.domain.common

import android.os.Bundle
import io.reactivex.rxjava3.functions.BiFunction
import java.util.*

sealed class ErrorWrap(
    val id: Long,
    val desc: String,
    var retryCallback: BiFunction<Bundle?, Bundle, Unit>? = null, // excluded from equals() / hashcode()
    var args: Bundle? = null,
) {
    companion object {
        val UNDEFINED_ID: Long = -1L // temp solution
    }

    // no return type here because will be hard to provide default value
    interface Visitor {
        fun visitTextError(v: TextError)
        fun visitDataError(v: DataError)
        fun visitNoInternetConnection(v: NoInternetConnection)
        fun visitNoPermission(v: NoPermission)

        fun visitSyntheticContainer(v: SyntheticContainer)
    }

    internal interface VisitorR<R> {
        fun visitRTextError(v: TextError): R
        fun visitRDataError(v: DataError): R
        fun visitRNoInternetConnection(v: NoInternetConnection): R
        fun visitRNoPermission(v: NoPermission): R
        fun visitRSyntheticContainer(v: SyntheticContainer): R
    }

    abstract fun exec(v: Visitor)

    internal abstract fun <R> execR(v: VisitorR<R>): R

    internal fun baseToString(): String = "id: $id, desc: \"$desc\", args: {$args}"

    class TextError(id: Long, desc: String) : ErrorWrap(id, desc) {
        override fun toString(): String = "Text: " + baseToString()

        override fun exec(v: Visitor) {
            v.visitTextError(this)
        }

        override fun <R> execR(v: VisitorR<R>): R = v.visitRTextError(this)
    }

    class DataError(id: Long, desc: String) : ErrorWrap(id, desc) {
        override fun toString(): String = "Data: " + baseToString()

        override fun exec(v: Visitor) {
            v.visitDataError(this)
        }

        override fun <R> execR(v: VisitorR<R>): R = v.visitRDataError(this)
    }

    class NoInternetConnection(id: Long, desc: String) : ErrorWrap(id, desc) {
        override fun toString(): String = "NoInternet: " + baseToString()

        override fun exec(v: Visitor) {
            v.visitNoInternetConnection(this)
        }

        override fun <R> execR(v: VisitorR<R>): R = v.visitRNoInternetConnection(this)
    }

    class NoPermission(id: Long, desc: String) : ErrorWrap(id, desc) {
        override fun toString(): String = "NoPermission: " + baseToString()

        override fun exec(v: Visitor) {
            v.visitNoPermission(this)
        }

        override fun <R> execR(v: VisitorR<R>): R = v.visitRNoPermission(this)
    }

    class SyntheticContainer internal constructor(val children: Array<ErrorWrap>) : ErrorWrap(UNDEFINED_ID, "SyntheticContainer") {
        override fun toString(): String = "Synth: {${children.joinToString("}, {")}}"

        override fun exec(v: Visitor) {
            v.visitSyntheticContainer(this)
        }

        override fun <R> execR(v: VisitorR<R>): R = v.visitRSyntheticContainer(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ErrorWrap

        if ((id != other.id) || (!desc.equals(other.desc))) return false
        if (args != null) {
            if (!args!!.equals(other.args)) return false
        } else {
            if (other.args != null) return false
        }

        return this.execR(object : VisitorR<Boolean> {
            override fun visitRTextError(v: TextError): Boolean = true
            override fun visitRDataError(v: DataError): Boolean = true
            override fun visitRNoInternetConnection(v: NoInternetConnection): Boolean = true
            override fun visitRNoPermission(v: NoPermission): Boolean = true

            override fun visitRSyntheticContainer(v: SyntheticContainer): Boolean = v.children.contentEquals((other as SyntheticContainer).children)
        })
    }

    internal fun baseHash(): Int = 31 * (31 * id.hashCode() + desc.hashCode()) + (args?.hashCode() ?: 0)

    override fun hashCode(): Int = this.execR(hashReturningVisitor)

    fun retry(extra: Bundle) {
        retryCallback?.apply(args, extra)
    }

}

private val hashReturningVisitor = object : ErrorWrap.VisitorR<Int> {
    override fun visitRTextError(v: ErrorWrap.TextError): Int = v.baseHash()
    override fun visitRDataError(v: ErrorWrap.DataError): Int = v.baseHash()
    override fun visitRNoInternetConnection(v: ErrorWrap.NoInternetConnection): Int = v.baseHash()
    override fun visitRNoPermission(v: ErrorWrap.NoPermission): Int = v.baseHash()
    override fun visitRSyntheticContainer(v: ErrorWrap.SyntheticContainer): Int = 31 * v.baseHash() + v.children.contentHashCode()
}

private val contentReturningVisitor = object : ErrorWrap.VisitorR<Array<ErrorWrap>> {
    override fun visitRTextError(v: ErrorWrap.TextError): Array<ErrorWrap> = arrayOf(v)
    override fun visitRDataError(v: ErrorWrap.DataError): Array<ErrorWrap> = arrayOf(v)
    override fun visitRNoInternetConnection(v: ErrorWrap.NoInternetConnection): Array<ErrorWrap> = arrayOf(v)
    override fun visitRNoPermission(v: ErrorWrap.NoPermission): Array<ErrorWrap> = arrayOf(v)
    override fun visitRSyntheticContainer(v: ErrorWrap.SyntheticContainer): Array<ErrorWrap> = v.children
}

fun combineErrorWrap(vararg err: ErrorWrap): ErrorWrap {
    val content = LinkedList<ErrorWrap>()
    for (e in err) {
        content.addAll(e.execR(contentReturningVisitor))
    }

    return ErrorWrap.SyntheticContainer(content.toTypedArray())
}
