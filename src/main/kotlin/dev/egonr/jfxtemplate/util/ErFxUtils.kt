package dev.egonr.jfxtemplate.util

import javafx.geometry.Insets
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color


class ErFxUtils {
    companion object {
        const val GOLDEN_RATIO = 1.61803398875;

        fun backgroundFromColor(color: Color): Background {
            return Background(BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY))
        }
    }
}