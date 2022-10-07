package dev.egonr.jfxtemplate.viewmodel

import dev.egonr.jfxtemplate.model.PersonModel
import dev.egonr.jfxtemplate.model.PersonViewModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections


/**
 * MainViewModel is the ViewModel for MainView and holds all data displayed in MainView.
 * Keep the logic here to a minimum, business logic should be in the Model!
 */
class MainViewModel {
    val counterValue = SimpleIntegerProperty(0)
    val persons = SimpleListProperty(FXCollections.observableArrayList<PersonViewModel>())

    fun incrementCounter() {
        counterValue.set(counterValue.get() + 1)
    }

    fun addGeneratedPerson() {
        persons.add(PersonViewModel(PersonModel.generateRandom()))
    }
}
