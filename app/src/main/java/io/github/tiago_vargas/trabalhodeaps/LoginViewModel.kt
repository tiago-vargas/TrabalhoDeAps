package io.github.tiago_vargas.trabalhodeaps

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel() : ViewModel() {
	private val _isLoggedIn = MutableStateFlow(false)
	val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

	fun setIsLoggedIn(value: Boolean) {
		_isLoggedIn.value = value
	}
}
