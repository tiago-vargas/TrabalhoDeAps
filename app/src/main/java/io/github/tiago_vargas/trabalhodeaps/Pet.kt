package io.github.tiago_vargas.trabalhodeaps

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "pets")
data class Pet(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val name: String,
	val species: Species,
	val birthDate: Long = 0,
	val weight: Double = 0.0,
	val gender: Gender = Gender.Male,
	val wasSterilized: Boolean = false,
)

enum class Species {
	Cat,
}

enum class Gender {
	Male,
	Female,
}

@Dao
interface PetDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
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

@Database(entities = [Pet::class], version = 1, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {
	abstract fun petDao(): PetDao

	companion object {
		@Volatile
		private var Instance: PetDatabase? = null

		fun getDatabase(context: Context): PetDatabase {
			return Instance ?: synchronized(this) {
				Room.databaseBuilder(
					context.applicationContext,
					PetDatabase::class.java,
					"pet_database",
				)
				.build()
				.also { Instance = it }
			}
		}
	}
}
