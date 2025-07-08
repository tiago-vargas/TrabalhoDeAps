package io.github.tiago_vargas.trabalhodeaps.data.pet

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PetPhotoDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(petPhoto: PetPhoto)

	@Delete
	suspend fun delete(petPhoto: PetPhoto)

	@Query("SELECT * FROM pet_photos WHERE petId = :petId ORDER BY createdAt ASC")
	fun getPhotosForPet(petId: Int): Flow<List<PetPhoto>>

	@Query("DELETE FROM pet_photos WHERE petId = :petId")
	suspend fun deleteAllPhotosForPet(petId: Int)
}
