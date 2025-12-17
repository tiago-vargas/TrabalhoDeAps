package io.github.tiago_vargas.trabalhodeaps.ui.pets.petlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.tiago_vargas.trabalhodeaps.data.LocalPetPhotoRepository
import io.github.tiago_vargas.trabalhodeaps.data.LocalPetRepository
import io.github.tiago_vargas.trabalhodeaps.data.PetDatabase
import io.github.tiago_vargas.trabalhodeaps.data.PetPhotoRepository
import io.github.tiago_vargas.trabalhodeaps.data.PetRepository
import io.github.tiago_vargas.trabalhodeaps.data.pet.Gender
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetPhoto
import io.github.tiago_vargas.trabalhodeaps.data.pet.Species
import io.github.tiago_vargas.trabalhodeaps.ui.vaccines.LocalVaccineRepository
import io.github.tiago_vargas.trabalhodeaps.ui.vaccines.VaccineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetListViewModel(
	private val repository: PetRepository,
	private val photoRepository: PetPhotoRepository,
	private val vaccineRepository: VaccineRepository
) : ViewModel() {
	private val _filter = MutableStateFlow(PetFilter())
	val filter = _filter.asStateFlow()
	val cachedPets = repository.getAllPets()
	private val petIdsWithVaccines = vaccineRepository.getPetIdsWithVaccines()

	val filteredPets = combine(cachedPets, filter, petIdsWithVaccines) { pets, filter, vaccinatedPetIds ->
		pets.filter { pet ->
			(filter.species.isEmpty() || pet.species in filter.species)
					&& (filter.gender.isEmpty() || pet.gender in filter.gender)
					&& (filter.wasSterilized.isEmpty() || pet.wasSterilized in filter.wasSterilized)
					&& (filter.vaccinationStatus.isEmpty() ||
						filter.vaccinationStatus.any { status ->
							when (status) {
								VaccinationStatus.Vaccinated -> vaccinatedPetIds.contains(pet.id)
								VaccinationStatus.Unvaccinated -> !vaccinatedPetIds.contains(pet.id)
							}
						})
		}
	}
	.stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val db = PetDatabase.getDatabase(
					context = (this[APPLICATION_KEY] as Application).applicationContext,
				)
				PetListViewModel(
					repository = LocalPetRepository(petDao = db.petDao()),
					photoRepository = LocalPetPhotoRepository(petPhotoDao = db.petPhotoDao()),
					vaccineRepository = LocalVaccineRepository(vaccineDao = db.vaccineDao())
				)
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

	private fun <T> Set<T>.toggle(value: T): Set<T> =
		if (contains(value)) this - value else this + value

	fun toggleSpecies(species: Species) {
		_filter.update { it.copy(species = it.species.toggle(species)) }
	}

	fun toggleGender(gender: Gender) {
		_filter.update { it.copy(gender = it.gender.toggle(gender)) }
	}

	fun toggleSterilized(value: Boolean) {
		_filter.update { it.copy(wasSterilized = it.wasSterilized.toggle(value)) }
	}

	fun toggleVaccinationStatus(status: VaccinationStatus) {
		_filter.update { it.copy(vaccinationStatus = it.vaccinationStatus.toggle(status)) }
	}

	// Photo management methods
	fun getPhotosForPet(petId: Int) = photoRepository.getPhotosForPet(petId)

	fun addPhoto(petId: Int, uri: String) = viewModelScope.launch {
		photoRepository.insert(PetPhoto(petId = petId, uri = uri))
	}

	fun removePhoto(photo: PetPhoto) = viewModelScope.launch {
		photoRepository.delete(photo)
	}

	// Vaccine methods
	fun getVaccinesForPet(petId: Int) = vaccineRepository.getVaccinesForPet(petId)
	fun getPetIdsWithVaccines() = vaccineRepository.getPetIdsWithVaccines()
}
