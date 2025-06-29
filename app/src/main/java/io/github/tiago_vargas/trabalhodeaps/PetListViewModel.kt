package io.github.tiago_vargas.trabalhodeaps

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PetListViewModel(private val repository: PetRepository) : ViewModel() {
	val cachedPets = repository.getAllPets()

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val db = PetDatabase.getDatabase(
					context = (this[APPLICATION_KEY] as Application).applicationContext,
				)
				PetListViewModel(repository = LocalPetRepository(petDao = db.petDao()))
			}
		}
	}

	fun insertPet(pet: Pet) = viewModelScope.launch {
		repository.insert(pet)
	}

	fun deletePet(pet: Pet) = viewModelScope.launch {
		repository.delete(pet)
	}

	fun updatePet(pet: Pet) = viewModelScope.launch {
		repository.update(pet)
	}

	fun getPet(id: Int) = cachedPets.map { list -> list.find { p -> p.id == id } }
}
