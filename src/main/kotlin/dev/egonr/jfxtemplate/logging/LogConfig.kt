package dev.egonr.jfxtemplate.logging

import dev.egonr.jfxtemplate.main.Main
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Configures java.util.logging used by flogger.
 */
class LogConfig {
    companion object {
        private val packageName = Main::class.java.packageName.substringBeforeLast('.')
        private val logger = Logger.getLogger(packageName)
    }

    init {
        println("LogConfig() - package: $packageName")

        val consoleHandler = ConsoleHandler().apply {
            level = Level.FINEST
            encoding = "UTF-8"
            formatter = LogFormatter(true)
        }

        val rollingFileHandler = RollingFileHandler().apply {
            level = Level.INFO
        }

        logger.apply {
            level = Level.FINEST
            addHandler(consoleHandler)
            addHandler(rollingFileHandler)
        }
    }
}