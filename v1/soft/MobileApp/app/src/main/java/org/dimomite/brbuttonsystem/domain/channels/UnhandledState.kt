package org.dimomite.brbuttonsystem.domain.channels

import io.reactivex.rxjava3.functions.BiFunction

typealias StringMap = Map<String, Any>

/**
 * Callback to notify object producer that problem was resolved
 * and it should try to obtain data again.
 * First parameter is an nullable extras object from UnhandledState itself,
 * second parameter - nullable retry() call argument
 */
typealias ErrorRetryCallback = BiFunction<StringMap?, StringMap?, Unit>

/**
 * Extended concept of an error state in data source.
 * In general means that [DataChannel] not able to provide full data.
 * And instances of this class give information why.
 */
sealed class UnhandledState<E>(val type: Class<E>, val extras: StringMap?, val retryCallback: ErrorRetryCallback?) {
    interface VisitorR<D, R> {
        fun visitNotExist(v: NotExist<D>): R
        fun visitNotAvailable(v: NotAvailable<D>): R
        fun visitNotAllowed(v: NotAllowed<D>): R
        fun visitDataError(v: DataError<D>): R
    }

    interface Visitor<D> {
        fun visitNotExist(v: NotExist<D>)
        fun visitNotAvailable(v: NotAvailable<D>)
        fun visitNotAllowed(v: NotAllowed<D>)
        fun visitDataError(v: DataError<D>)
    }

    abstract fun <R> execR(v: VisitorR<E, R>): R
    abstract fun exec(v: Visitor<E>)


    /**
     * Signal to this object producer that problem was resolved on higher level
     * and it should try to obtain data again.
     */
    fun retry(retryExtras: StringMap? = null) {
        retryCallback?.apply(this.extras, retryExtras)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UnhandledState<*>
        // retryCallback is excluded
        return (type == other.type) && (extras == other.extras)
    }

    override fun hashCode(): Int {
        // retryCallback is excluded
        var result = type.hashCode()
        result = 31 * result + (extras?.hashCode() ?: 0)
        return result
    }

    protected fun baseToString(): String = "type: ${type.simpleName}, extras: $extras, retryCallback: " + if (retryCallback != null) "yes" else "no"

    /**
     * When source of data is permanently not available.
     */
    class NotExist<D>(type: Class<D>, extras: Map<String, Any>? = null, retryCallback: ErrorRetryCallback? = null) : UnhandledState<D>(type, extras, retryCallback) {
        override fun toString(): String = "UnhandledState.NotExist: ${baseToString()}"

        override fun <R> execR(v: VisitorR<D, R>): R = v.visitNotExist(this)

        override fun exec(v: Visitor<D>) {
            v.visitNotExist(this)
        }
    }

    /**
     * When source of data is temporary not available and may require additional actions to turn on the source.
     * Like "Bluetooth is off" of "WiFi is off".
     */
    class NotAvailable<D>(type: Class<D>, extras: Map<String, Any>? = null, retryCallback: ErrorRetryCallback? = null) : UnhandledState<D>(type, extras, retryCallback) {
        override fun toString(): String = "UnhandledState.NotAvailable: ${baseToString()}"

        override fun <R> execR(v: VisitorR<D, R>): R = v.visitNotAvailable(this)

        override fun exec(v: Visitor<D>) {
            v.visitNotAvailable(this)
        }
    }

    /**
     * When access to data source is not allowed and require actions to grant it.
     * Like Android permissions.
     */
    class NotAllowed<D>(type: Class<D>, extras: Map<String, Any>? = null, retryCallback: ErrorRetryCallback? = null) : UnhandledState<D>(type, extras, retryCallback) {
        override fun toString(): String = "UnhandledState.NotAllowed: ${baseToString()}"

        override fun <R> execR(v: VisitorR<D, R>): R = v.visitNotAllowed(this)

        override fun exec(v: Visitor<D>) {
            v.visitNotAllowed(this)
        }
    }

    /**
     * Data from a source is corrupted.
     */
    class DataError<D>(type: Class<D>, extras: Map<String, Any>? = null, retryCallback: ErrorRetryCallback? = null) : UnhandledState<D>(type, extras, retryCallback) {
        override fun toString(): String = "UnhandledState.DataError: ${baseToString()}"

        override fun <R> execR(v: VisitorR<D, R>): R = v.visitDataError(this)

        override fun exec(v: Visitor<D>) {
            v.visitDataError(this)
        }
    }

}
