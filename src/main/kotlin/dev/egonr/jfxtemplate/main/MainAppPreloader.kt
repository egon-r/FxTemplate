package dev.egonr.jfxtemplate.main

import com.google.common.flogger.FluentLogger
import dev.egonr.jfxtemplate.util.ErFxUtils
import javafx.application.Preloader
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import javafx.stage.StageStyle
import kotlin.system.exitProcess


class MainAppPreloader : Preloader() {
    companion object {
        private val logger = FluentLogger.forEnclosingClass()
    }

    private lateinit var preloaderStage: Stage
    private val preloaderProgress = SimpleDoubleProperty(ProgressBar.INDETERMINATE_PROGRESS)
    private val preloaderRoot = StackPane().apply {
        children += VBox().apply {
            children += Label("Loading...").apply {
                alignment = Pos.CENTER
                maxWidth = Double.MAX_VALUE
            }
            children += ProgressBar().apply {
                progressProperty().bind(preloaderProgress)
                maxWidth = Double.MAX_VALUE
            }
            alignment = Pos.CENTER
        }
    }

    override fun start(primaryStage: Stage?) {
        if (primaryStage == null) {
            logger.atSevere().log("primaryStage == null")
            exitProcess(1)
        }

        preloaderStage = primaryStage
        preloaderStage.apply {
            scene = Scene(preloaderRoot)
            height = 200.0
            width = height * ErFxUtils.GOLDEN_RATIO * 2
            initStyle(StageStyle.UNDECORATED)
            show()
            centerOnScreen()
        }
    }

    override fun handleApplicationNotification(info: PreloaderNotification?) {
        super.handleApplicationNotification(info)
        if (info is ProgressNotification) {
            preloaderProgress.set(info.progress)
            if (info.progress >= 1.0) {
                preloaderStage.close()
            }
        }
    }
}
