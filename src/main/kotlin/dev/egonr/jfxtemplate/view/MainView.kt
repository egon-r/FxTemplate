package dev.egonr.jfxtemplate.view

import com.google.common.flogger.FluentLogger
import de.jensd.fx.glyphs.GlyphIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import dev.egonr.jfxtemplate.model.PersonViewModel
import dev.egonr.jfxtemplate.viewmodel.MainViewModel
import javafx.beans.binding.Bindings
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*


class MainView : StackPane() {
    companion object {
        private val logger = FluentLogger.forEnclosingClass()
    }

    val viewModel = MainViewModel()

    init {
        children += VBox().apply {
            alignment = Pos.CENTER

            children += HBox().apply {
                spacing = 16.0
                alignment = Pos.CENTER_LEFT
                children += Button("Increment Counter", MaterialIconView(MaterialIcon.ADD)).apply {
                    id = "bt_increment"
                    setOnAction {
                        logger.atFinest().log("$id clicked!")
                        viewModel.incrementCounter()
                    }
                }
                children += Label().apply {
                    textProperty().bind(Bindings.concat("Counter: ").concat(viewModel.counterValue.asString()))
                }
                children += Pane().apply {
                    maxWidth = Double.MAX_VALUE
                    HBox.setHgrow(this, Priority.ALWAYS)
                }
                children += Button("Log Test", MaterialIconView(MaterialIcon.CHAT_BUBBLE)).apply {
                    setOnAction {
                        logger.atFinest().log("finest log")
                        logger.atFiner().log("finer log")
                        logger.atFine().log("fine log")
                        logger.atConfig().log("config log")
                        logger.atInfo().log("info log")
                        logger.atWarning().log("warning log")
                        logger.atSevere().log("severe log")
                    }
                }
            }

            val splitLeft = VBox().apply {
                children += ListView(viewModel.persons).apply {
                    id = "list_persons"
                    setCellFactory {
                        object : ListCell<PersonViewModel>() {
                            override fun updateItem(item: PersonViewModel?, empty: Boolean) {
                                super.updateItem(item, empty)
                                if (empty || item == null) {
                                    text = null
                                    graphic = null
                                } else {
                                    text = "${item.fullName} (${item.age})"
                                }
                            }
                        }
                    }
                }
                children += Button("Add Person", MaterialIconView(MaterialIcon.ADD)).apply {
                    id = "bt_add_person"
                    setOnAction {
                        logger.atFinest().log("$id clicked!")
                        viewModel.addGeneratedPerson()
                    }
                }
            }

            val splitRight = AsyncExampleView()

            children += SplitPane(splitLeft, splitRight)
        }
    }
}