package org.dimomite.brbuttonsystem.core

/**
 * Result to be used in callbacks of async calls.
 */
sealed class AsyncResult<out T : Any, out E : Any> {
    data class Ok<T : Any>(val data: T) : AsyncResult<T, Nothing>()

    /**
     * Mostly for instant invocation on callback from async calls
     * to notify that all preconditions to execute it are met.
     */
    object InProgress : AsyncResult<Nothing, Nothing>()

    data class Error<E : Any>(val e: E) : AsyncResult<Nothing, E>()
}