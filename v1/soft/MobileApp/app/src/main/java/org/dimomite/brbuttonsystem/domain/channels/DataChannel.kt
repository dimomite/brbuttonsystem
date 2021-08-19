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
    companion object {
        fun <D> create(data: D): DataChannel<D> = DataChannel(data = DataWrap.Ok<D>(data))

        fun <D> createPending(): DataChannel<D> = DataChannel(data = DataWrap.None.ins(), progress = ProgressWrap.InProgress.ins())

        fun <D> createError(us: Array<UnhandledState<*>>): DataChannel<D> =
            DataChannel(data = DataWrap.None.ins(), progress = ProgressWrap.InProgress.ins(), unhandledStates = us)

        fun <D> createError(us: UnhandledState<*>): DataChannel<D> =
            DataChannel(data = DataWrap.None.ins(), progress = ProgressWrap.InProgress.ins(), unhandledStates = arrayOf(us))
    }

    interface Visitor<D> {
        fun visitDataWrap(v: DataWrap<D>)
        fun visitProgressWrap(v: ProgressWrap)
        fun visitUnhandledState(v: UnhandledState<*>)
    }

    override fun toString(): String = "DataChannel: data: {$data}, progress: {$progress}, unhandled states: {${unhandledStates.contentToString()}}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataChannel<*>

        if (data != other.data) return false
        if (progress != other.progress) return false
        if (!unhandledStates.contentEquals(other.unhandledStates)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.hashCode()
        result = 31 * result + progress.hashCode()
        result = 31 * result + unhandledStates.contentHashCode()
        return result
    }

    fun exec(v: DataChannel.Visitor<D>) {
        v.visitDataWrap(data)
        v.visitProgressWrap(progress)
        for (us in unhandledStates) {
            v.visitUnhandledState(us)
        }
    }

    fun <R> execOnData(dh: ChannelDataHandler<D, R>): R = data.execR(dh)

    fun <R> execROnProgress(v: ProgressWrap.VisitorR<R>): R = progress.execR(v)

    fun execOnProgress(v: ProgressWrap.Visitor) {
        progress.exec(v)
    }

    fun <E> execUnhandledStateByClass(errorClass: Class<E>, v: UnhandledState.Visitor<E>) {
        for (us: UnhandledState<E> in getUnhandledStatesByClass(errorClass)) {
            us.exec(v)
        }
    }

    fun <E> execUnhandledStatesByClassAndCategory(errorClass: Class<E>, category: Class<out UnhandledState<*>>, v: UnhandledState.Visitor<E>) {
        for (us: UnhandledState<E> in getUnhandledStateByClassAndCategory(errorClass, category)) {
            us.exec(v)
        }
    }

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
