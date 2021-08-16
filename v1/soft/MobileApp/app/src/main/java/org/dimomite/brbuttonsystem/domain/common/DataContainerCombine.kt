package org.dimomite.brbuttonsystem.domain.common

open class DataContainerCombine<D>(
    val classes: Array<Class<*>>,
    private val splits: Splits
) {
    data class Splits(
        val values: Map<Class<*>, Any>,
        val pendings: Map<Class<*>, PendingProgress>,
        val errors: Map<Class<*>, ErrorWrap>,
        val containers: Map<Class<*>, DataContainer<*>>,
    ) {
        class Builder {
            private val values = mutableMapOf<Class<*>, Any>()
            private val pendings = mutableMapOf<Class<*>, PendingProgress>()
            private val errors = mutableMapOf<Class<*>, ErrorWrap>()
            private val containers = mutableMapOf<Class<*>, DataContainer<*>>()

            fun <D> container(clazz: Class<D>, value: DataContainer<D>): Builder {
                containers[clazz] = value

                value.exec(object : DataContainer.Visitor<D, Unit> {
                    override fun visitOk(v: DataContainer.Ok<D>) {
                        values[clazz] = v.data!!
                    }

                    override fun visitPending(v: DataContainer.Pending<D>) {
                        pendings[clazz] = v.progress
                    }

                    override fun visitError(v: DataContainer.Error<D>) {
                        errors[clazz] = v.er
                    }
                })

                return this
            }

            fun build(): Splits = Splits(this.values, this.pendings, this.errors, this.containers)
        }
    }

    val allPendings: PendingProgress.SyntheticComposite = combinePendingProgress(PendingProgress.nextId(), splits.pendings.values)
    val allErrors: ErrorWrap.SyntheticContainer = combineErrorWrap(splits.errors.values)

    fun <T> get(clazz: Class<T>): DataContainer<T> = containers()[clazz] as DataContainer<T>

    fun values(): Map<Class<*>, Any> = splits.values
    fun pendings(): Map<Class<*>, PendingProgress> = splits.pendings
    fun errors(): Map<Class<*>, ErrorWrap> = splits.errors
    fun containers(): Map<Class<*>, DataContainer<*>> = splits.containers
}

class DataContainer2Combine<T1, T2, D>(
    c1: Class<T1>,
    private val v1: DataContainer<T1>,
    c2: Class<T2>,
    private val v2: DataContainer<T2>,
) : DataContainerCombine<D>(
    classes = arrayOf(c1, c2),
    splits = Splits.Builder().container(c1, v1).container(c2, v2).build(),
) {
    fun get0(): DataContainer<T1> = v1
    fun get1(): DataContainer<T2> = v2
}

class DataContainer3Combine<T1, T2, T3, D>(
    c1: Class<T1>,
    private val v1: DataContainer<T1>,
    c2: Class<T2>,
    private val v2: DataContainer<T2>,
    c3: Class<T3>,
    private val v3: DataContainer<T3>,
) : DataContainerCombine<D>(
    classes = arrayOf(c1, c2),
    splits = Splits.Builder().container(c1, v1).container(c2, v2).container(c3, v3).build(),
) {
    fun get0(): DataContainer<T1> = v1
    fun get1(): DataContainer<T2> = v2
    fun get3(): DataContainer<T3> = v3
}
