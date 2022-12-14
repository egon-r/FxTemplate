package dev.egonr.jfxtemplate.main

import com.google.common.flogger.FluentLogger
import dev.egonr.jfxtemplate.util.ErFxUtils
import dev.egonr.jfxtemplate.view.MainView
import javafx.application.Application
import javafx.application.Preloader.ProgressNotification
import javafx.scene.Scene
import javafx.scene.text.Font
import javafx.stage.Stage
import kotlin.system.exitProcess


class MainApp : Application() {
    companion object {
        private val logger = FluentLogger.forEnclosingClass()
    }

    override fun init() {
        super.init()

        loadFont("/fonts/Roboto-Regular.ttf")
        loadFont("/fonts/Roboto-Light.ttf")

        // do heavy lifting here and use notifyPreloader to display progress
        doStartupWork()
    }

    private fun loadFont(resourcePath: String) {
        val resource = javaClass.getResource(resourcePath)
        if(resource == null) {
            logger.atWarning().log("Error loading font '$resourcePath'")
            return
        }

        val font = Font.loadFont(resource.toExternalForm(), 12.0)
        logger.atFiner().log("Loaded font '${font.name}'")
    }

    private fun doStartupWork() {
        notifyPreloader(ProgressNotification(0.0))
        Thread.sleep(1000)
        notifyPreloader(ProgressNotification(0.5))
        Thread.sleep(1000)
        notifyPreloader(ProgressNotification(1.0))
    }

    override fun start(primaryStage: Stage?) {
        if (primaryStage == null) {
            logger.atSevere().log("primaryStage == null")
            System.exit(1)
        }

        // load fxml
        // val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/MainView.fxml"))
        // val scene = Scene(fxmlLoader.load(), 320.0, 240.0)

        primaryStage?.apply {
            scene = Scene(MainView())
            AppTheme.setTheme(AppTheme.currentTheme, scene)
            title = "FxTemplate"
            height = 400.0
            width = 400.0 * ErFxUtils.GOLDEN_RATIO
            show()
        }
    }

    override fun stop() {
        super.stop()
        exitProcess(0)
    }
}