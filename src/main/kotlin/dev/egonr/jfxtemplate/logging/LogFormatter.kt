package dev.egonr.jfxtemplate.logging

import java.util.*
import java.util.logging.LogRecord
import java.util.logging.SimpleFormatter


class LogFormatter(private val newline: Boolean = false) : SimpleFormatter() {
    override fun format(log: LogRecord): String {
        var logformat = "[%1\$-7s] [%2\$td.%2\$tm.%2\$tY %2\$tT%2\$tZ] [%3\$s@%4\$s]:"
        if(newline) {
            logformat += "\n"
        }
        logformat += " %5\$s %n"
        return String.format(
            logformat,
            log.level.localizedName,
            Date(log.millis),
            log.sourceClassName + "." + log.sourceMethodName + "()",
            log.threadID,
            log.message
        )
    }
}