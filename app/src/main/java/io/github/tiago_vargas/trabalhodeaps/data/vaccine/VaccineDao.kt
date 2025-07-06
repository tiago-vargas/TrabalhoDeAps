package io.github.tiago_vargas.trabalhodeaps.data.vaccine

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccineDao {
	@Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
	suspend fun insert(vaccine: Vaccine)

	@Update
	suspend fun update(vaccine: Vaccine)

	@Delete
	suspend fun delete(vaccine: Vaccine)

	@Query("SELECT * FROM vaccines")
	fun getAll(): Flow<List<Vaccine>>

	@Query("SELECT * FROM vaccines WHERE id = :id")
	fun getById(id: Int): Flow<Vaccine>
}
