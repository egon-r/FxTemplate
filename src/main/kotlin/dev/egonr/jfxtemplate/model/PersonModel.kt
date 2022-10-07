package dev.egonr.jfxtemplate.model

import dev.egonr.jfxtemplate.util.ErKtUtils
import java.time.LocalDateTime
import java.util.*

/**
 * The model resembles the backend data structure (from a database, file, ...)
 */
data class PersonModel (val firstName: String, val lastName: String, val birthDate: LocalDateTime) {
    companion object {
        fun generateRandom(): PersonModel {
            return PersonModel(
                ErKtUtils.randomString(6),
                ErKtUtils.randomString(10),
                LocalDateTime.now().minusYears(Random().nextInt(10, 20).toLong())
            )
        }
    }
}