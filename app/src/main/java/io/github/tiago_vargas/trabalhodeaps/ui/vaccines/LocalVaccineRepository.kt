package io.github.tiago_vargas.trabalhodeaps.ui.vaccines

import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.VaccineDao
import kotlinx.coroutines.flow.Flow

class LocalVaccineRepository(private val vaccineDao: VaccineDao) : VaccineRepository {
	override suspend fun insert(vaccine: Vaccine) = vaccineDao.insert(vaccine)
	override suspend fun update(vaccine: Vaccine) = vaccineDao.update(vaccine)
	override suspend fun delete(vaccine: Vaccine) = vaccineDao.delete(vaccine)
	override fun getAllVaccines(): Flow<List<Vaccine>> = vaccineDao.getAll()
	override fun getVaccine(id: Int): Flow<Vaccine> = vaccineDao.getById(id)
	override fun getVaccinesForPet(petId: Int): Flow<List<Vaccine>> = vaccineDao.getVaccinesForPet(petId)
	override suspend fun deleteAllVaccinesForPet(petId: Int) = vaccineDao.deleteAllVaccinesForPet(petId)
	override fun getPetIdsWithVaccines(): Flow<List<Int>> = vaccineDao.getPetIdsWithVaccines()
}
