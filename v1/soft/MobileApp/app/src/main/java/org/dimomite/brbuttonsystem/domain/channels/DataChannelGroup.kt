package org.dimomite.brbuttonsystem.domain.channels

abstract class DataChannelGroup internal constructor(internal val content: Content) {
    fun <D> getOrNull(dataType: Class<D>): DataChannel<D>? = content.getData(dataType)
}

/**
 * Wrapper around [Map] to ensure that key-class matches type parameter of [DataChannel].
 */
internal class Content private constructor(private val values: Map<Class<*>, DataChannel<*>>) {
    class Builder {
        private val values = mutableMapOf<Class<*>, DataChannel<*>>()
        fun <T> put(clazz: Class<T>, data: DataChannel<T>): Builder {
            values[clazz] = data
            return this
        }

        fun build(): Content = Content(values)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getData(dataType: Class<T>): DataChannel<T>? = values.get(dataType) as DataChannel<T>?
}

class DataChannel1Groups<T1>(
    c1: Class<T1>, private val d1: DataChannel<T1>,
) : DataChannelGroup(Content.Builder().put(c1, d1).build()) {
    fun get1(): DataChannel<T1> = d1
}

class DataChannel2Groups<T1, T2>(
    c1: Class<T1>, private val d1: DataChannel<T1>,
    c2: Class<T2>, private val d2: DataChannel<T2>,
) : DataChannelGroup(Content.Builder().put(c1, d1).put(c2, d2).build()) {
    fun get1(): DataChannel<T1> = d1
    fun get2(): DataChannel<T2> = d2
}

class DataChannel3Groups<T1, T2, T3>(
    c1: Class<T1>, private val d1: DataChannel<T1>,
    c2: Class<T2>, private val d2: DataChannel<T2>,
    c3: Class<T3>, private val d3: DataChannel<T3>,
) : DataChannelGroup(Content.Builder().put(c1, d1).put(c2, d2).put(c3, d3).build()) {
    fun get1(): DataChannel<T1> = d1
    fun get2(): DataChannel<T2> = d2
    fun get3(): DataChannel<T3> = d3
}

class DataChannel4Groups<T1, T2, T3, T4>(
    c1: Class<T1>, private val d1: DataChannel<T1>,
    c2: Class<T2>, private val d2: DataChannel<T2>,
    c3: Class<T3>, private val d3: DataChannel<T3>,
    c4: Class<T4>, private val d4: DataChannel<T4>,
) : DataChannelGroup(Content.Builder().put(c1, d1).put(c2, d2).put(c3, d3).put(c4, d4).build()) {
    fun get1(): DataChannel<T1> = d1
    fun get2(): DataChannel<T2> = d2
    fun get3(): DataChannel<T3> = d3
    fun get4(): DataChannel<T4> = d4
}
