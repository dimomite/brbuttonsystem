package org.dimomite.brbuttonsystem

import android.app.Application
import org.slf4j.LoggerFactory
import timber.log.Timber

class BrButtonSystemControlApplication : Application() {
    companion object {
        val TAG = "BrButtonSystemControl"
    }

    override fun onCreate() {
        super.onCreate()

        initLogging()
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