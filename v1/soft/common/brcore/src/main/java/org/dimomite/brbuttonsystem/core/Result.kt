package core

/**
 * Result of sync call.
 */
// TODO I don't like how it's used in client code: a lot of casts and internal information leak.
// Think about better API. Maybe some "visitor" solution
sealed class Result<out T : Any, out E : Any> {
    data class Ok<T : Any>(val data: T) : Result<T, Nothing>()

    data class Error<E : Any>(val e: E) : Result<Nothing, E>()

    object None : Result<Nothing, Nothing>()
}