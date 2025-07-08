package io.github.tiago_vargas.trabalhodeaps.data.pet

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
	tableName = "pet_photos",
	foreignKeys = [
		ForeignKey(
			entity = Pet::class,
			parentColumns = ["id"],
			childColumns = ["petId"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class PetPhoto(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val petId: Int,
	val uri: String,
	val createdAt: Long = System.currentTimeMillis()
)
