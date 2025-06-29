package io.github.tiago_vargas.trabalhodeaps.data.vaccine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vaccines")
data class Vaccine(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val name: String,
	val description: String,
	val date: Long = 0,
)
