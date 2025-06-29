package io.github.tiago_vargas.trabalhodeaps.data.pet

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
	@Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
	suspend fun insert(pet: Pet)

	@Update
	suspend fun update(pet: Pet)

	@Delete
	suspend fun delete(pet: Pet)

	@Query("SELECT * FROM pets")
	fun getAll(): Flow<List<Pet>>

	@Query("SELECT * FROM pets WHERE id = :id")
	fun getById(id: Int): Flow<Pet>
}
