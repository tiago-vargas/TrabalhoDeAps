package io.github.tiago_vargas.trabalhodeaps.data

import io.github.tiago_vargas.trabalhodeaps.data.pet.PetPhoto
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetPhotoDao
import kotlinx.coroutines.flow.Flow

class LocalPetPhotoRepository(private val petPhotoDao: PetPhotoDao) : PetPhotoRepository {
	override suspend fun insert(petPhoto: PetPhoto) = petPhotoDao.insert(petPhoto)
	override suspend fun delete(petPhoto: PetPhoto) = petPhotoDao.delete(petPhoto)
	override fun getPhotosForPet(petId: Int): Flow<List<PetPhoto>> = petPhotoDao.getPhotosForPet(petId)
	override suspend fun deleteAllPhotosForPet(petId: Int) = petPhotoDao.deleteAllPhotosForPet(petId)
}
