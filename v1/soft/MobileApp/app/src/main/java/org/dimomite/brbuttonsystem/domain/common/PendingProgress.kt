package org.dimomite.brbuttonsystem.domain.common


sealed class PendingProgress(val id: Long) {
    companion object {
        const val TYPE_IN_PROGRESS = 0L
        const val TYPE_FLAGGED = 1L
        const val TYPE_TO_MAX = 2L

        private fun checkId(id: Long): Long = if (id > TYPE_TO_MAX) id else throw IllegalArgumentException("Id should be greater than $TYPE_TO_MAX")

        /**
         * Shortcut for InProgress.instance()
         */
        fun ins(): PendingProgress = InProgress.instance()
    }

    class InProgress private constructor() : PendingProgress(TYPE_IN_PROGRESS) {
        companion object {
            private val instance = InProgress()
            fun instance(): PendingProgress = instance
        }

        override fun toString(): String = "InProgress"
        override fun <R> exec(v: Visitor<R>): R = v.visitInProgress(this)
    }

    class FlagProgress private constructor(val ready: Boolean) : PendingProgress(TYPE_FLAGGED) {
        companion object {
            private val readyInstance = FlagProgress(true)
            private val notReadyInstance = FlagProgress(false)

            fun instance(ready: Boolean): PendingProgress = if (ready) readyInstance else notReadyInstance
        }

        override fun toString(): String = "FlagProgress: ready: $ready"
        override fun <R> exec(v: Visitor<R>): R = v.visitFlagProgress(this)
    }

    class ToMaxProgress(val progress: Int, val max: Int) : PendingProgress(TYPE_TO_MAX) {
        override fun toString(): String = "ToMaxProgress: $progress of $max"
        override fun <R> exec(v: Visitor<R>): R = v.visitToMaxProgress(this)
    }

    class CompositeProgress(id: Long, val children: Array<PendingProgress>) : PendingProgress(checkId(id)) {
        override fun toString(): String = "CompositeProgress: id: $id, content: {${children.contentToString()}}"
        override fun <R> exec(v: Visitor<R>): R = v.visitCompositeProgress(this)
    }

    class SyntheticComposite(id: Long, val children: Array<PendingProgress>) : PendingProgress(checkId(id)) {
        override fun toString(): String = "SyntheticComposite: id: $id, content: {${children.contentToString()}}"
        override fun <R> exec(v: Visitor<R>): R = v.visitSyntheticComposite(this)
    }

    abstract fun <R> exec(v: Visitor<R>): R

    interface Visitor<R> {
        fun visitInProgress(v: InProgress): R
        fun visitFlagProgress(v: FlagProgress): R
        fun visitToMaxProgress(v: ToMaxProgress): R
        fun visitCompositeProgress(v: CompositeProgress): R
        fun visitSyntheticComposite(v: SyntheticComposite): R
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false
        other as PendingProgress
        if (this.id != other.id) return false

        return this.exec(object : Visitor<Boolean> {
            // Private ctor - instance is singleton. If code reached this point - something is wrong in implementation
            override fun visitInProgress(v: InProgress): Boolean = throw IllegalStateException("In progress objects are not singletons")

            // Private ctor - 2 singleton instances. If code didn't pass check "this === other" than we have values with different flag values
            override fun visitFlagProgress(v: FlagProgress): Boolean = false

            override fun visitToMaxProgress(v: ToMaxProgress): Boolean {
                val o = other as ToMaxProgress
                return (v.progress == o.progress) && (v.max == o.max)
            }

            override fun visitCompositeProgress(v: CompositeProgress): Boolean {
                val o = other as CompositeProgress
                return v.children.contentEquals(o.children)
            }

            override fun visitSyntheticComposite(v: SyntheticComposite): Boolean {
                val o = other as SyntheticComposite
                return v.children.contentEquals(o.children)
            }
        })
    }

    override fun hashCode(): Int = this.exec(object : Visitor<Int> {
        override fun visitInProgress(v: InProgress): Int = 0
        override fun visitFlagProgress(v: FlagProgress): Int = if (v.ready) 2 else 1
        override fun visitToMaxProgress(v: ToMaxProgress): Int = 3 + 31 * (31 * v.max + v.progress)
        override fun visitCompositeProgress(v: CompositeProgress): Int = 3 + 31 * v.children.contentHashCode() + 31 * 31 * v.id.hashCode()
        override fun visitSyntheticComposite(v: SyntheticComposite): Int = 3 + 31 * v.children.contentHashCode() + 31 * 31 * v.id.hashCode()
    })

}

