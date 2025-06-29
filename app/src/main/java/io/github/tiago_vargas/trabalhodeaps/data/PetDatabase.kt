package io.github.tiago_vargas.trabalhodeaps.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetDao

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
