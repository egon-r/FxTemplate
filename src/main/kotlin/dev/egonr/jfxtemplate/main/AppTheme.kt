package dev.egonr.jfxtemplate.main

import javafx.scene.Scene

class AppTheme {
    companion object {
        var currentTheme: Themes = Themes.DARK

        fun setTheme(theme: Themes, scene: Scene) {
            currentTheme = theme
            when(theme) {
                Themes.CUSTOM -> TODO("not implemented")
                else -> {
                    scene.stylesheets.removeAll(Themes.values().map { it.path })
                    scene.stylesheets.add(theme.path)
                }
            }
        }
    }

    enum class Themes(val path: String) {
        DARK(Main::class.java.getResource("/css/theme/AppDark.css")!!.toExternalForm()),
        LIGHT(Main::class.java.getResource("/css/theme/AppLight.css")!!.toExternalForm()),
        CUSTOM("")
    }
}