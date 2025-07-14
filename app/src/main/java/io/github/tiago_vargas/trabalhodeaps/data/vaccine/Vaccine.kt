package io.github.tiago_vargas.trabalhodeaps.data.vaccine

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet

@Entity(
	tableName = "vaccines",
	foreignKeys = [
		ForeignKey(
			entity = Pet::class,
			parentColumns = ["id"],
			childColumns = ["petId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class Vaccine(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val petId: Int, // Foreign key to Pet
	val name: String,
	val description: String,
	val date: Long = 0,
)
