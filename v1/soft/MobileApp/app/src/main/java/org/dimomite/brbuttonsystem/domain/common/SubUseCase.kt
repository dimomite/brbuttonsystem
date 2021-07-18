package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.functions.*
import io.reactivex.rxjava3.functions.Function

class SubUseCase<R>(private val inFlow: Flowable<DataContainer<R>>, params: SubscriptionUseCaseParams) : DataProvider<R> {
    companion object {
        fun <T, R> from(
            uc: SubUseCase<T>,
            params: SubscriptionUseCaseParams,
            convert: Function<Flowable<DataContainer<T>>, Flowable<DataContainer<R>>>
        ) = SubUseCase<R>(convert.apply(uc.inFlow), params)

        fun <T1, T2, R> from(
            uc1: SubUseCase<T1>, uc2: SubUseCase<T2>,
            params: SubscriptionUseCaseParams,
            convert: BiFunction<Flowable<DataContainer<T1>>, Flowable<DataContainer<T2>>, Flowable<DataContainer<R>>>
        ) = SubUseCase<R>(convert.apply(uc1.inFlow, uc2.inFlow), params)

        fun <T1, T2, T3, R> from(
            uc1: SubUseCase<T1>, uc2: SubUseCase<T2>, uc3: SubUseCase<T3>,
            params: SubscriptionUseCaseParams,
            convert: Function3<Flowable<DataContainer<T1>>, Flowable<DataContainer<T2>>, Flowable<DataContainer<T3>>, Flowable<DataContainer<R>>>
        ) = SubUseCase<R>(convert.apply(uc1.inFlow, uc2.inFlow, uc3.inFlow), params)

        fun <T1, T2, T3, T4, R> from(
            uc1: SubUseCase<T1>, uc2: SubUseCase<T2>, uc3: SubUseCase<T3>, uc4: SubUseCase<T4>,
            params: SubscriptionUseCaseParams,
            convert: Function4<Flowable<DataContainer<T1>>, Flowable<DataContainer<T2>>, Flowable<DataContainer<T3>>, Flowable<DataContainer<T4>>, Flowable<DataContainer<R>>>
        ) = SubUseCase<R>(convert.apply(uc1.inFlow, uc2.inFlow, uc3.inFlow, uc4.inFlow), params)

        fun <T1, T2, T3, T4, T5, R> from(
            uc1: SubUseCase<T1>, uc2: SubUseCase<T2>, uc3: SubUseCase<T3>, uc4: SubUseCase<T4>, uc5: SubUseCase<T5>,
            params: SubscriptionUseCaseParams,
            convert: Function5<Flowable<DataContainer<T1>>, Flowable<DataContainer<T2>>, Flowable<DataContainer<T3>>, Flowable<DataContainer<T4>>, Flowable<DataContainer<T5>>, Flowable<DataContainer<R>>>
        ) = SubUseCase<R>(convert.apply(uc1.inFlow, uc2.inFlow, uc3.inFlow, uc4.inFlow, uc5.inFlow), params)
    }

    private val result = inFlow
        .observeOn(params.observationScheduler)
        .subscribeOn(params.subscriptionScheduler)
        .unsubscribeOn(params.unsubscriptionScheduler)

    override fun out(): Flowable<DataContainer<R>> = result
}
