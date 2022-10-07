package dev.egonr.jfxtemplate.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.application.Platform
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import kotlinx.coroutines.*
import java.util.*
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis


class AsyncExampleView : VBox() {
    val currentProgress = SimpleDoubleProperty(0.0)
    val logTextArea = TextArea().apply {
        maxHeight = Double.MAX_VALUE
    }
    val numberOfCoroutines = SimpleIntegerProperty(10)

    init {
        children += ScrollPane(logTextArea).apply {
            isFitToWidth = true
            isFitToHeight = true
            maxHeight = Double.MAX_VALUE
            VBox.setVgrow(this, Priority.ALWAYS)
        }
        children += ProgressBar().apply {
            maxWidth = Double.MAX_VALUE
            progressProperty().bind(currentProgress)
        }
        children += VBox().apply {
            children += HBox().apply {
                spacing = 4.0
                alignment = Pos.CENTER_LEFT
                children += Label("Number of coroutines:")
                children += TextField(numberOfCoroutines.get().toString()).apply {
                    textProperty().addListener { observable, oldValue, newValue ->
                        val intValue = newValue.toIntOrNull()
                        if (intValue != null) {
                            numberOfCoroutines.set(intValue)
                            style = "-fx-text-fill: black;"
                        } else {
                            style = "-fx-text-fill: red;"
                        }
                    }
                }
            }
            children += Button("Launch", MaterialIconView(MaterialIcon.FAST_FORWARD)).apply {
                setOnAction {
                    runAsyncExample(numberOfCoroutines.get())
                }
            }
        }
    }

    private fun runAsyncExample(numCoroutines: Int) {
        Platform.runLater {
            logTextArea.text = "Launching $numCoroutines coroutines..."
            currentProgress.set(ProgressBar.INDETERMINATE_PROGRESS)
        }

        // switch to a new thread to free up the main thread
        thread {
            var resultSum = 0
            val millis = measureTimeMillis {
                runBlocking {
                    val jobs = arrayListOf<Deferred<Int>>()
                    val rng = Random()
                    for (i in 0..numCoroutines) {
                        val jobDelay = rng.nextLong(1000, 3000)
                        jobs += async {
                            delay(jobDelay)
                            val result = i + i
                            result
                        }
                    }
                    resultSum = jobs.awaitAll().sum()
                }
            }

            Platform.runLater {
                currentProgress.set(1.0)
                logTextArea.appendText("\nAll coroutines finished in ${millis}ms!")
                logTextArea.appendText("\nResultSum: $resultSum")
                logTextArea.scrollTop = Double.MAX_VALUE
            }
        }
    }
}
