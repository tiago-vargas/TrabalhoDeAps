package io.github.tiago_vargas.trabalhodeaps.data

import io.github.tiago_vargas.trabalhodeaps.data.pet.PetPhoto
import kotlinx.coroutines.flow.Flow

interface PetPhotoRepository {
	suspend fun insert(petPhoto: PetPhoto)
	suspend fun delete(petPhoto: PetPhoto)
	fun getPhotosForPet(petId: Int): Flow<List<PetPhoto>>
	suspend fun deleteAllPhotosForPet(petId: Int)
}
