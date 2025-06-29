package io.github.tiago_vargas.trabalhodeaps.data

import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import kotlinx.coroutines.flow.Flow

interface PetRepository {
	suspend fun insert(pet: Pet)
	suspend fun update(pet: Pet)
	suspend fun delete(pet: Pet)
	fun getAllPets(): Flow<List<Pet>>
	fun getPet(id: Int): Flow<Pet>
}
