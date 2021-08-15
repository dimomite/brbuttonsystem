package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.functions.Function

/**
 * Extracts [DataContainer.Ok.data] value from provided container.
 * For [DataContainer.Pending] and [DataContainer.Error] returns [defaultValue]
 */
class ValueExtractingVisitor<T>(private val defaultValue: T) : DataContainer.Visitor<T, T> {
    override fun visitOk(v: DataContainer.Ok<T>): T = v.data
    override fun visitPending(v: DataContainer.Pending<T>): T = defaultValue
    override fun visitError(v: DataContainer.Error<T>): T = defaultValue
}

val extractBoolOrFalse: DataContainer.Visitor<Boolean, Boolean> = ValueExtractingVisitor<Boolean>(false)
val extractBoolOrTrue: DataContainer.Visitor<Boolean, Boolean> = ValueExtractingVisitor<Boolean>(true)

/**
 * Extracts [DataContainer.Ok.data] value from provided container applying provided [preconvert] transformation on it.
 * For [DataContainer.Pending] and [DataContainer.Error] returns [defaultValue]
 */
class ValueExtractingVisitorWithPreConversion<D, R>(
    private val defaultValue: R,
    private val preconvert: Function<D, R>
) : DataContainer.Visitor<D, R> {
    override fun visitOk(v: DataContainer.Ok<D>): R = preconvert.apply(v.data)
    override fun visitPending(v: DataContainer.Pending<D>): R = defaultValue
    override fun visitError(v: DataContainer.Error<D>): R = defaultValue
}

class ValueExtractingVisitorWithDataContainerPreConversion<D, R>(
    private val defaultValue: R,
    private val preconvert: Function<D, DataContainer<R>>
) : DataContainer.Visitor<D, R> {
    private val postConvert = ValueExtractingVisitor<R>(defaultValue)

    override fun visitOk(v: DataContainer.Ok<D>): R {
        val dc = preconvert.apply(v.data)
        return dc.exec(postConvert)
    }

    override fun visitPending(v: DataContainer.Pending<D>): R = defaultValue
    override fun visitError(v: DataContainer.Error<D>): R = defaultValue
}

class DataContainerOkValuesUpdater<T>(private val updater: Function<T, T>) : DataContainer.Visitor<T, DataContainer<T>> {
    override fun visitOk(v: DataContainer.Ok<T>): DataContainer<T> {
        val updatedValue: T = updater.apply(v.data)
        return DataContainer.Ok<T>(updatedValue)
    }

    override fun visitPending(v: DataContainer.Pending<T>): DataContainer<T> = v
    override fun visitError(v: DataContainer.Error<T>): DataContainer<T> = v
}
