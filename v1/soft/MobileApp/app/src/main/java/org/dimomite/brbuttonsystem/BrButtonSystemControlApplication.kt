package org.dimomite.brbuttonsystem

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.dimomite.brbuttonsystem.data.remotecontrol.RemoteControlRepository
import org.slf4j.LoggerFactory
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BrButtonSystemControlApplication : Application() {
    companion object {
        val TAG = "BrButtonSystemControl"
    }

    @Inject
    lateinit var remoteControlRepo: RemoteControlRepository

    override fun onCreate() {
        super.onCreate()

        initLogging()

        remoteControlRepo.start()
    }

    override fun onTerminate() {
        remoteControlRepo.stop()

        super.onTerminate()
    }

    private fun initLogging() {
        if (Timber.forest().size == 0) {
            Timber.plant(Timber.DebugTree())
            val log = LoggerFactory.getLogger(TAG)
            log.debug("Timber is initialized")
        } else {
            val log = LoggerFactory.getLogger(TAG)
            log.warn("Timber is already initialized")
        }
    }

}