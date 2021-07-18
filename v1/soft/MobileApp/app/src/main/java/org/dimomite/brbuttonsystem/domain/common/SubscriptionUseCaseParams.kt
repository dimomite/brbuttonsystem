package org.dimomite.brbuttonsystem.domain.common

import io.reactivex.rxjava3.core.Scheduler

data class SubscriptionUseCaseParams(
    val subscriptionScheduler: Scheduler,
    val unsubscriptionScheduler: Scheduler,
    val observationScheduler: Scheduler,
)