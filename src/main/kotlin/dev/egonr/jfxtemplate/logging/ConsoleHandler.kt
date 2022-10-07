package dev.egonr.jfxtemplate.logging

import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.SimpleFormatter
import java.util.logging.StreamHandler

/**
 * A ConsoleHandler that prints to stderr when the level is above WARNING
 */
class ConsoleHandler : StreamHandler() {
    init {
        level = Level.INFO
        formatter = SimpleFormatter()
    }

    override fun publish(record: LogRecord?) {
        if((record?.level?.intValue() ?: 0) >= Level.WARNING.intValue()) {
            System.err.print(formatter.format(record))
        } else {
            print(formatter.format(record))
        }
    }
}