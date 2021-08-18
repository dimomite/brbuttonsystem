package org.dimomite.brbuttonsystem.domain.channels

sealed class DataWrap<D> {
    /**
     * Visits instance of [DataWrap] and returns result of corresponding call from [ChannelDataHandler].
     */
    abstract fun <R> execR(ch: ChannelDataHandler<D, R>): R

    /**
     * Visits instance of [DataWrap] without return.
     */
    abstract fun exec(ch: ChannelDataHandler<D, Unit>)

    /**
     * Represents "empty" state of DataWrap without data.
     */
    class None<D> : DataWrap<D>() {
        override fun toString(): String = "DataWrap.None"

        override fun <R> execR(ch: ChannelDataHandler<D, R>): R = ch.onNothing()

        override fun exec(ch: ChannelDataHandler<D, Unit>) {
            ch.onNothing()
        }
    }

    /**
     * Represents state with data.
     */
    data class Ok<D>(var data: D) : DataWrap<D>() {
        override fun toString(): String = "DataWrap.Single: $data"

        override fun <R> execR(ch: ChannelDataHandler<D, R>): R = ch.onData(data)

        override fun exec(ch: ChannelDataHandler<D, Unit>) {
            ch.onData(data)
        }
    }
}

interface ChannelDataHandler<D, R> {
    /**
     * Invoked when [DataWrap] contains data (type is [DataWrap.Ok])
     */
    fun onData(data: D): R

    /**
     * Invoked when [DataWrap] is empty (type is [DataWrap.None])
     */
    fun onNothing(): R
}

/**
 * Implementation for non-returning data handler (R is [Unit])
 * and empty implementation of [onNothing]
 */
abstract class BaseChannelDataHandler<D> : ChannelDataHandler<D, Unit> {
    override fun onNothing() {}
}
