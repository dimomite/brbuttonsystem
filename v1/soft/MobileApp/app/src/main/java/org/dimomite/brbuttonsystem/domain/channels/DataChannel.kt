package org.dimomite.brbuttonsystem.domain.channels

/**
 * Container for data provided by a source.
 * Combines data value itself, information about progress of it receiving
 * and exceptional cases which should be notified to higher levels (up to UI).
 */
class DataChannel<D>(
    val data: DataWrap<D>,
    val progress: ProgressWrap = ProgressWrap.Ready.ins(),
    val unhandledStates: Array<UnhandledState<*>> = emptyArray(),
) {
    fun <E> getUnhandledStatesByClass(errorClass: Class<E>): Collection<UnhandledState<E>> {
        val result = mutableListOf<UnhandledState<E>>()
        for (us in unhandledStates) {
            if (us.type == errorClass) {
                @Suppress("UNCHECKED_CAST")
                result.add(us as UnhandledState<E>)
            }
        }

        return result
    }

    fun getUnhandledStatesByCategory(category: Class<out UnhandledState<*>>): Collection<UnhandledState<*>> {
        val result = mutableListOf<UnhandledState<*>>()
        for (us in unhandledStates) {
            if (us.javaClass == category) {
                result.add(us)
            }
        }

        return result
    }

    fun <E> getUnhandledStateByClassAndCategory(errorClass: Class<E>, category: Class<out UnhandledState<*>>): Collection<UnhandledState<E>> {
        val result = mutableListOf<UnhandledState<E>>()
        for (us in unhandledStates) {
            if ((us.type == errorClass) && (us.javaClass == category)) {
                @Suppress("UNCHECKED_CAST")
                result.add(us as UnhandledState<E>)
            }
        }

        return result
    }
}
