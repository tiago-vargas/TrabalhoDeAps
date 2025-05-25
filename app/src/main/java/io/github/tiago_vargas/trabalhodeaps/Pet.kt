package io.github.tiago_vargas.trabalhodeaps

data class Pet(
	val id: Int? = null,
	val name: String,
	val species: Species,
	val birthDate: String? = null,
	val weight: Float? = null,
	val gender: String? = null,
	val wasSterilized: Boolean? = null,
) {
	fun fromId(id: Int): Pet = pets[id]
}

enum class Species {
	Cat,
}

private val pets = listOf(
	Pet(id = 0, name = "Cajú", species = Species.Cat),
	Pet(id = 1, name = "Branquinho", species = Species.Cat),
	Pet(id = 2, name = "Salomão", species = Species.Cat),
	Pet(id = 3, name = "Pretinho", species = Species.Cat),
	Pet(id = 4, name = "Jeremias", species = Species.Cat),
	Pet(id = 5, name = "Pingo", species = Species.Cat),
	Pet(id = 6, name = "Jiló", species = Species.Cat),
	Pet(id = 7, name = "Pitoquinho", species = Species.Cat),
	Pet(id = 8, name = "Caramelo", species = Species.Cat),
)
