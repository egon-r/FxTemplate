package dev.egonr.jfxtemplate.main

import dev.egonr.jfxtemplate.logging.LogConfig
import javafx.application.Application

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            System.setProperty("java.util.logging.config.class", LogConfig::class.java.name)
            System.setProperty("javafx.preloader", MainAppPreloader::class.java.name)
            Application.launch(MainApp::class.java)
        }
    }
}

