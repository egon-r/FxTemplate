package dev.egonr.jfxtemplate.logging

import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.FileHandler
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


/**
 * Custom [FileHandler] which rolls log files in daily intervals.
 * Contains functions to limit the size of the log directory and to compress old log files.
 */
class RollingFileHandler() : FileHandler(initLogFile(), 0, 1, true) {
    init {
        level = Level.FINEST
        formatter = LogFormatter()
        encoding = "UTF-8"
    }

    override fun publish(record: LogRecord?) {
        if (sizeCheckCounter > logLinesUntilSizeCheck) {
            limitLogDirSize()
            sizeCheckCounter = 0
        }
        sizeCheckCounter++
        super.publish(record)
    }

    companion object {
        private val logDir = "logs"
        private val logDirMaxSize = 10 * 1024 * 1024
        private val prefix = "log_"
        private val logLinesUntilSizeCheck = 1000
        private val alwaysKeepLatestNFiles = 2
        private var sizeCheckCounter = 0

        fun initLogFile(): String {
            // create file name, the smallest unit determines the grouping of log files
            val date = SimpleDateFormat("yyyy_MMM_dd").format(Date(System.currentTimeMillis()))
            val logFileName = "$prefix$date.log"

            // create log directory
            val logPath = Paths.get(logDir)
            if (Files.notExists(logPath)) {
                Files.createDirectory(logPath)
            }

            compressExistingLogs(logPath, logFileName)
            limitLogDirSize()

            return "$logDir/$logFileName"
        }

        private fun getLogDirSizeBytes(): Long {
            return Files.walk(Paths.get(logDir))
                .filter { it.toFile().isFile }
                .mapToLong { it.toFile().length() }
                .sum();
        }

        fun limitLogDirSize() {
            try {
                var logDirSize = getLogDirSizeBytes()

                // delete until below max size
                if (logDirSize > logDirMaxSize) {
                    val bytesToDelete = logDirSize - logDirMaxSize
                    var bytesDeleted = 0L

                    val files = Files.walk(Paths.get(logDir))
                        .filter { it.toFile().isFile && it.fileName.toString().startsWith(prefix) }
                        .sorted { o1, o2 -> o1.toFile().lastModified().compareTo(o2.toFile().lastModified()) }
                        .toArray()
                        .dropLast(alwaysKeepLatestNFiles)

                    files.forEach {
                        if (it is Path && bytesDeleted < bytesToDelete) {
                            val size = it.toFile().length()
                            if (Files.deleteIfExists(it)) {
                                bytesDeleted += size
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
                println("Exception while limiting log dir size: $ex")
            }
        }

        private fun compressExistingLogs(logPath: Path, logFileName: String) {
            Files.walk(logPath).forEach {
                val itname = it.fileName.toString()
                if (itname.startsWith(prefix) && itname.endsWith(".log") && itname != logFileName) {
                    if (zipFile(it)) {
                        Files.deleteIfExists(it)
                    }
                }
            }
        }

        fun zipFile(file: Path): Boolean {
            try {
                val fos = FileOutputStream("$logDir/" + file.fileName.toString() + ".zip")
                val zipOut = ZipOutputStream(fos)
                val fis = FileInputStream(file.toFile())
                val zipEntry = ZipEntry(file.fileName.toString())
                zipOut.putNextEntry(zipEntry)
                fis.copyTo(zipOut)
                zipOut.close()
                fis.close()
                fos.close()
                return true
            } catch (ex: Exception) {
                println(ex)
                return false
            }
        }
    }
}