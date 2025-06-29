package io.github.tiago_vargas.trabalhodeaps

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class Pet(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val name: String,
	val species: Species,
	val birthDate: Long = 0,
	val weight: Double = 0.0,
	val gender: Gender = Gender.Male,
	val wasSterilized: Boolean = false,
)

enum class Species {
	Cat,
}

enum class Gender {
	Male,
	Female,
}
