package org.dimomite.brbuttonsystem.domain.common

sealed class DataContainer<T> {
    interface Visitor<R> {
        fun <D> visitOk(v: Ok<D>): R
        fun visitPending(v: Pending): R
        fun visitError(v: Error): R
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
