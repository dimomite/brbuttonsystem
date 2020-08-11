package org.dimomite.brbuttonsystem

import org.apache.logging.log4j.LogManager
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
    configureLogging()

    println("Stating BrButtonSystem control PC app")

    val app = MainApp()
    app.doLaunch(args)


    println("App is shutting down")
}

private fun configureLogging() {
    val l = LogManager.getRootLogger()

    LoggerFactory.getLogger("appmain").debug("Logging from main(): DBG")
}

