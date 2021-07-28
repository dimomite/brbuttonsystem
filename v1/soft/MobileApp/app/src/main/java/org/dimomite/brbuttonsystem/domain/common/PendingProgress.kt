package org.dimomite.brbuttonsystem.domain.common


sealed class PendingProgress(internal val type: Long) {
    companion object {
        const val TYPE_IN_PROGRESS = 0L
        const val TYPE_FLAGGED = 1L
        const val TYPE_TO_MAX = 2L

        internal fun checkType(type: Long): Long = if (type > TYPE_TO_MAX) type else throw IllegalArgumentException("Type should be greater than $TYPE_TO_MAX but was: $type")

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

    class CompositeProgress(type: Long, val children: Array<PendingProgress>) : PendingProgress(checkType(type)) {
        override fun toString(): String = "CompositeProgress: type: $type, content: {${children.contentToString()}}"
        override fun <R> exec(v: Visitor<R>): R = v.visitCompositeProgress(this)

        fun type(): Long = type
    }

    class SyntheticComposite(type: Long, val children: Array<PendingProgress>) : PendingProgress(checkType(type)) {
        override fun toString(): String = "SyntheticComposite: type: $type, content: {${children.contentToString()}}"
        override fun <R> exec(v: Visitor<R>): R = v.visitSyntheticComposite(this)

        fun type(): Long = type
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
        if (this.type != other.type) return false

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
        override fun visitCompositeProgress(v: CompositeProgress): Int = 3 + 31 * v.children.contentHashCode() + 31 * 31 * v.type.hashCode()
        override fun visitSyntheticComposite(v: SyntheticComposite): Int = 3 + 31 * v.children.contentHashCode() + 31 * 31 * v.type.hashCode()
    })

}

// CompositeProgress is returned as a single instance while for SyntheticComposite it returns its content
private val contentReturningVisitor = object : PendingProgress.Visitor<Array<PendingProgress>> {
    override fun visitInProgress(v: PendingProgress.InProgress): Array<PendingProgress> = arrayOf(v)
    override fun visitFlagProgress(v: PendingProgress.FlagProgress): Array<PendingProgress> = arrayOf(v)
    override fun visitToMaxProgress(v: PendingProgress.ToMaxProgress): Array<PendingProgress> = arrayOf(v)
    override fun visitCompositeProgress(v: PendingProgress.CompositeProgress): Array<PendingProgress> = arrayOf(v)
    override fun visitSyntheticComposite(v: PendingProgress.SyntheticComposite): Array<PendingProgress> = v.children
}

/**
 * Combines all provided PendingProgress instances into one SyntheticComposite.
 * When input is a SyntheticComposite itself result will include its content but not SyntheticComposite instance itself.
 * @param unique specifies whether result should contain only unique values (equals() == false)
 *               When value is true order in result is not guaranteed to match input.
 */
fun combinePendingProgress(resultType: Long, vararg progress: PendingProgress, unique: Boolean = true): PendingProgress {
    val checkedId = PendingProgress.checkType(resultType) // quick check before big work

    val children: Array<PendingProgress> = if (unique) {
        val uniqueContent = mutableSetOf<PendingProgress>()
        for (p in progress) {
            uniqueContent.addAll(p.exec(contentReturningVisitor))
        }
        uniqueContent.toTypedArray()
    } else {
        val content = mutableListOf<PendingProgress>()
        for (p in progress) {
            content.addAll(p.exec(contentReturningVisitor))
        }
        content.toTypedArray()
    }

    return PendingProgress.SyntheticComposite(checkedId, children)
}
