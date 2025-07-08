package io.github.tiago_vargas.trabalhodeaps.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetDao
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetPhoto
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetPhotoDao
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.VaccineDao

@Database(
	entities = [Pet::class, PetPhoto::class, Vaccine::class],
	version = 5,
	exportSchema = false,
)
abstract class PetDatabase : RoomDatabase() {
	abstract fun petDao(): PetDao
	abstract fun petPhotoDao(): PetPhotoDao
	abstract fun vaccineDao(): VaccineDao

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
