package org.dimomite.brbuttonsystem.domain.channels

sealed class ProgressWrap {
    interface VisitorR<R> {
        fun visitReady(v: Ready): R
        fun visitInProgress(v: InProgress): R
        fun visitFlagProgress(v: FlagProgress): R
        fun visitToValueProgress(v: ToValueProgress): R
        fun visitComposite(v: Composite): R
    }

    interface Visitor {
        fun visitReady(v: Ready)
        fun visitInProgress(v: InProgress)
        fun visitFlagProgress(v: FlagProgress)
        fun visitToValueProgress(v: ToValueProgress)
        fun visitComposite(v: Composite)
    }

    abstract fun <R> execR(v: VisitorR<R>): R
    abstract fun exec(v: Visitor)

    /**
     * When all data is available.
     * Expected to be used when DataWrap is [DataWrap.Ok]
     */
    class Ready private constructor() : ProgressWrap() {
        companion object {
            private val instance = Ready()
            fun ins(): Ready = instance
        }

        override fun toString(): String = "ProgressWrap.Ready"
        override fun equals(other: Any?): Boolean = this === other // Singleton
        override fun hashCode(): Int = -1

        override fun <R> execR(v: VisitorR<R>): R = v.visitReady(this)

        override fun exec(v: Visitor) {
            v.visitReady(this)
        }
    }

    /**
     * When data loading is in progress.
     */
    class InProgress private constructor() : ProgressWrap() {
        companion object {
            private val instance = InProgress()
            fun ins(): InProgress = instance
        }

        override fun toString(): String = "ProgressWrap.InProgress"
        override fun equals(other: Any?): Boolean = this === other // Singleton
        override fun hashCode(): Int = -2

        override fun <R> execR(v: VisitorR<R>): R = v.visitInProgress(this)

        override fun exec(v: Visitor) {
            v.visitInProgress(this)
        }
    }

    class FlagProgress private constructor(val ready: Boolean) : ProgressWrap() {
        companion object {
            private val instanceTrue: FlagProgress = FlagProgress(true)
            private val instanceFalse: FlagProgress = FlagProgress(false)
            private fun ins(ready: Boolean): FlagProgress = if (ready) instanceTrue else instanceFalse
        }

        override fun toString(): String = "ProgressWrap.FlagProgress: ready: $ready"
        override fun equals(other: Any?): Boolean = this === other // Singletons
        override fun hashCode(): Int = if (ready) -3 else -4

        override fun <R> execR(v: VisitorR<R>): R = v.visitFlagProgress(this)

        override fun exec(v: Visitor) {
            v.visitFlagProgress(this)
        }
    }

    class ToValueProgress(val progress: Int, val max: Int) : ProgressWrap() {
        override fun toString(): String = "ProgressWrap.ToValueProgress: {$progress of $max}"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ToValueProgress
            return (progress == other.progress) && (max == other.max)
        }

        override fun hashCode(): Int = 31 * max + progress

        override fun <R> execR(v: VisitorR<R>): R = v.visitToValueProgress(this)

        override fun exec(v: Visitor) {
            v.visitToValueProgress(this)
        }
    }

    class Composite(val children: Array<ProgressWrap>) : ProgressWrap() {
        override fun toString(): String = "ProgressWrap.Composite: {${children.contentToString()}}"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Composite
            return children.contentEquals(other.children)
        }

        override fun hashCode(): Int = children.contentHashCode()

        override fun <R> execR(v: VisitorR<R>): R = v.visitComposite(this)

        override fun exec(v: Visitor) {
            v.visitComposite(this)
        }
    }

}
