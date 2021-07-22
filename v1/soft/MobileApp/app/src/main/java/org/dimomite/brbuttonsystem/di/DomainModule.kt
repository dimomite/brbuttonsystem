package org.dimomite.brbuttonsystem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.dimomite.brbuttonsystem.domain.common.SubscriptionUseCaseParams

private val subParams: SubscriptionUseCaseParams = SubscriptionUseCaseParams(
    subscriptionScheduler = Schedulers.io(),
    unsubscriptionScheduler = Schedulers.io(),
    observationScheduler = AndroidSchedulers.mainThread(),
)

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun subscriptionUseParams(): SubscriptionUseCaseParams = subParams
}
