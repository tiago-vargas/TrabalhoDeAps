package io.github.tiago_vargas.trabalhodeaps

import io.github.tiago_vargas.trabalhodeaps.data.PetRepository
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetDao
import kotlinx.coroutines.flow.Flow

class LocalPetRepository(private val petDao: PetDao) : PetRepository {
	override suspend fun insert(pet: Pet) = petDao.insert(pet)
	override suspend fun update(pet: Pet) = petDao.update(pet)
	override suspend fun delete(pet: Pet) = petDao.delete(pet)
	override fun getAllPets(): Flow<List<Pet>> = petDao.getAll()
	override fun getPet(id: Int): Flow<Pet> = petDao.getById(id)
}
