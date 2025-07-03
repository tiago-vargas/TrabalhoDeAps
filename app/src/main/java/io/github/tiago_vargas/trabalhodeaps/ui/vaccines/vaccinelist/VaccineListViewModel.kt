package io.github.tiago_vargas.trabalhodeaps.ui.vaccines.vaccinelist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.tiago_vargas.trabalhodeaps.data.PetDatabase
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import io.github.tiago_vargas.trabalhodeaps.ui.vaccines.LocalVaccineRepository
import io.github.tiago_vargas.trabalhodeaps.ui.vaccines.VaccineRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class VaccineListViewModel(private val repository: VaccineRepository) : ViewModel() {
	val cachedVaccines = repository.getAllVaccines()

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val db = PetDatabase.getDatabase(
					context = (this[APPLICATION_KEY] as Application).applicationContext,
				)
				VaccineListViewModel(repository = LocalVaccineRepository(vaccineDao = db.vaccineDao()))
			}
		}
	}

	fun insertVaccine(vaccine: Vaccine) = viewModelScope.launch {
		repository.insert(vaccine)
	}

	fun deleteVaccine(vaccine: Vaccine) = viewModelScope.launch {
		repository.delete(vaccine)
	}

	fun updateVaccine(vaccine: Vaccine) = viewModelScope.launch {
		repository.update(vaccine)
	}

	fun getVaccine(id: Int) = cachedVaccines.map { list -> list.find { v -> v.id == id } }
}
