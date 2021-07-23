package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function

interface DataModifier<D> {
    fun modifyData(patch: Function<D, D>): Single<Unit>
}

interface DataPatch<D> {
    fun patch(input: D): D
}
