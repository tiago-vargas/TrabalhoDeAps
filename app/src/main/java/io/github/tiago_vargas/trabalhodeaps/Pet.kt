package io.github.tiago_vargas.trabalhodeaps

data class Pet(
	val name: String,
	val species: Species,
	val birthDate: String? = null,
	val weight: Float? = null,
	val gender: String? = null,
	val wasSterilized: Boolean? = null,
)

enum class Species {
	Cat,
}
