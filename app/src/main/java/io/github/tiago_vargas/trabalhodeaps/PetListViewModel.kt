package io.github.tiago_vargas.trabalhodeaps

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class PetListViewModel(val petDao: PetDao) : ViewModel() {
	val cachedPets = petDao.getAll()

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val db = PetDatabase.getDatabase(
					context = (this[APPLICATION_KEY] as Application).applicationContext,
				)
				PetListViewModel(petDao = db.petDao())
			}
		}
	}
}
