package core

/**
 * Result of sync call.
 */
sealed class Result<out T : Any, out E : Any> {
    data class Ok<T : Any>(val data: T) : Result<T, Nothing>()

    data class Error<E : Any>(val e: E) : Result<Nothing, E>()
}