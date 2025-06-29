package io.github.tiago_vargas.trabalhodeaps.ui.pets.petlist

import io.github.tiago_vargas.trabalhodeaps.data.pet.Gender
import io.github.tiago_vargas.trabalhodeaps.data.pet.Species

data class PetFilter(
	val species: Set<Species> = emptySet(),
	val gender: Set<Gender> = emptySet(),
	val wasSterilized: Set<Boolean> = emptySet(),
)
