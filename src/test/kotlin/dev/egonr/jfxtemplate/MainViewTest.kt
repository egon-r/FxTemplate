package dev.egonr.jfxtemplate

import dev.egonr.jfxtemplate.util.ErFxUtils
import dev.egonr.jfxtemplate.view.MainView
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.testfx.api.FxRobot
import org.testfx.framework.junit5.ApplicationExtension
import org.testfx.framework.junit5.Start

@ExtendWith(ApplicationExtension::class)
class MainViewTest {
    lateinit var mainView: MainView

    @Start
    fun start(stage: Stage) {
        mainView = MainView()
        stage.apply {
            scene = Scene(mainView)
            height = 400.0
            width = stage.height * ErFxUtils.GOLDEN_RATIO
            if (!stage.isShowing) {
                initStyle(StageStyle.DECORATED)
            }
            show()
        }
    }

    @Test
    fun test_counter(robot: FxRobot) {
        val incrementButton = mainView.lookup("#bt_increment") as Button
        Assertions.assertNotNull(incrementButton)

        for (i in 0..10) {
            val beforeIncrement = mainView.viewModel.counterValue.get()
            robot.clickOn(incrementButton)
            val afterIncrement = mainView.viewModel.counterValue.get()
            Assertions.assertTrue(afterIncrement == beforeIncrement + 1)
        }
    }

    @Test
    fun test_add_person(robot: FxRobot) {
        val addPersonButton = mainView.lookup("#bt_add_person") as Button
        Assertions.assertNotNull(addPersonButton)

        val personsList = mainView.lookup("#list_persons") as ListView<*>
        Assertions.assertNotNull(personsList)

        for (i in 0..10) {
            val beforeIncrement = personsList.items.count()
            robot.clickOn(addPersonButton)
            val afterIncrement = personsList.items.count()
            Assertions.assertTrue(afterIncrement == beforeIncrement + 1)
        }
    }
}