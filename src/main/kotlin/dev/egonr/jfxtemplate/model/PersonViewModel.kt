package dev.egonr.jfxtemplate.model

import java.time.Duration
import java.time.LocalDateTime


/**
 * PersonViewModel stores data from a PersonModel in a way that's more useful to the view.
 * (Can be omitted if PersonModel is sufficient)
 */
class PersonViewModel(person: PersonModel) {
    val fullName: String = person.firstName + " " + person.lastName
    val age: Int = (Duration.between(person.birthDate, LocalDateTime.now()).toDays() / 365).toInt()
}
