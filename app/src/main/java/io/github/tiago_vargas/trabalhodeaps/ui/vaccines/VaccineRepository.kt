package io.github.tiago_vargas.trabalhodeaps.ui.vaccines

import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import kotlinx.coroutines.flow.Flow

interface VaccineRepository {
	suspend fun insert(vaccine: Vaccine)
	suspend fun update(vaccine: Vaccine)
	suspend fun delete(vaccine: Vaccine)
	fun getAllVaccines(): Flow<List<Vaccine>>
	fun getVaccine(id: Int): Flow<Vaccine>
}
