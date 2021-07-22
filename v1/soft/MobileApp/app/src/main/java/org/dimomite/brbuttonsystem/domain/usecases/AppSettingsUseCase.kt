package org.dimomite.brbuttonsystem.domain.usecases

import org.dimomite.brbuttonsystem.domain.common.DataRepository
import org.dimomite.brbuttonsystem.domain.common.SubUseCase
import org.dimomite.brbuttonsystem.domain.common.SubscriptionUseCaseParams
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import javax.inject.Inject

class AppSettingsUseCase @Inject constructor(subRepo: DataRepository<AppSettingsModel>, params: SubscriptionUseCaseParams) :
    SubUseCase<AppSettingsModel>(subRepo.provider().outFlow(), params) {
}
