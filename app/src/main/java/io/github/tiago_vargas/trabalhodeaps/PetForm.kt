package io.github.tiago_vargas.trabalhodeaps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetForm(
	petName: String,
	onPetNameChange: (String) -> Unit,
	petSpecies: Species,
	onPetSpeciesChange: (Species) -> Unit,
	petBirthDate: Long,
	onPetBirthDateChange: (Long) -> Unit,
	petWeight: Double,
	onPetWeightChange: (Double) -> Unit,
	petGender: Gender,
	onPetGenderChange: (Gender) -> Unit,
	petIsSterilized: Boolean,
	onPetIsSterilizedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.Companion.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {
		Icon(
			imageVector = Icons.Filled.Person,
			contentDescription = null,
			modifier = Modifier.size(200.dp),
		)

		OutlinedTextField(
			petName,
			onValueChange = onPetNameChange,
			label = { Text("Name") },  // TODO! Extract
		)

		val speciesDropdownMenuIsExpanded = remember { mutableStateOf(false) }
		Box(contentAlignment = Alignment.Companion.TopEnd) {
			OutlinedTextField(
				petSpecies.toString(),
				readOnly = true,
				onValueChange = { s -> },
				label = { Text("Species") },  // TODO! Extract
				trailingIcon = {
					IconButton(onClick = { speciesDropdownMenuIsExpanded.value = true }) {
						Icon(
							Icons.Filled.ArrowDropDown,
							contentDescription = "Pick Species",  // TODO! Extract
						)
					}
				})
			DropdownMenu(
				expanded = speciesDropdownMenuIsExpanded.value,
				onDismissRequest = { speciesDropdownMenuIsExpanded.value = false },
			) {
				for (species in Species.entries) {
					DropdownMenuItem(
						text = { Text(species.toString()) },
						onClick = {
							onPetSpeciesChange(species)
							speciesDropdownMenuIsExpanded.value = false
						},
					)
				}
			}
		}

		val showDatePickerDialog = remember { mutableStateOf(false) }
		val datePickerState = rememberDatePickerState()
		val selectedDate = remember { mutableStateOf("") }
		if (showDatePickerDialog.value) {
			DatePickerDialog(
				onDismissRequest = { showDatePickerDialog.value = false },
				confirmButton = {
					Button(
						onClick = {
							datePickerState.selectedDateMillis?.let { millis ->
								selectedDate.value = millis.toString()
								onPetBirthDateChange(millis)
							}
							showDatePickerDialog.value = false
						},
					) {
						Text(text = "Ok")  // TODO! Extract
					}
				},
				dismissButton = {
					Button(onClick = { showDatePickerDialog.value = false }) {
						Text("Cancel")  // TODO! Extract
					}
				},
			) {
				DatePicker(state = datePickerState)
			}
		}
		OutlinedTextField(
			value = {
				val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
				sdf.format(Date(petBirthDate))
			}(),
			readOnly = true,
			onValueChange = { s -> },
			label = { Text("Birth Date") },  // TODO! Extract
			trailingIcon = {
				IconButton(onClick = { showDatePickerDialog.value = true }) {
					Icon(
						imageVector = Icons.Filled.DateRange,
						contentDescription = "Pick Date",  // TODO! Extract
					)
				}
			},
		)

		val genderDropdownMenuIsExpanded = remember { mutableStateOf(false) }
		Box(contentAlignment = Alignment.Companion.TopEnd) {
			OutlinedTextField(
				petGender.toString(),
				readOnly = true,
				onValueChange = { s -> },
				label = { Text("Gender") },  // TODO! Extract
				trailingIcon = {
					IconButton(onClick = { genderDropdownMenuIsExpanded.value = true }) {
						Icon(
							Icons.Filled.ArrowDropDown,
							contentDescription = "Pick Gender",  // TODO! Extract
						)
					}
				},
			)
			DropdownMenu(
				expanded = genderDropdownMenuIsExpanded.value,
				onDismissRequest = { genderDropdownMenuIsExpanded.value = false },
			) {
				for (gender in Gender.entries) {
					DropdownMenuItem(
						text = { Text(gender.toString()) },
						onClick = {
							onPetGenderChange(gender)
							genderDropdownMenuIsExpanded.value = false
						},
					)
				}
			}
		}

		OutlinedTextField(
			petWeight.toString(),
			onValueChange = { s -> onPetWeightChange(s.toDouble()) },
			label = { Text("Weight") },  // TODO! Extract
			suffix = { Text("kg") },
		)

		val isSterilizedDropdownMenuIsExpanded = remember { mutableStateOf(false) }
		Box(contentAlignment = Alignment.Companion.TopEnd) {
			OutlinedTextField(
				if (petIsSterilized) "Yes" else "No",  // TODO! Extract
				readOnly = true,
				onValueChange = { s -> },
				label = { Text("Is Sterilized?") },  // TODO! Extract
				trailingIcon = {
					IconButton(onClick = { isSterilizedDropdownMenuIsExpanded.value = true }) {
						Icon(
							Icons.Filled.ArrowDropDown,
							contentDescription = "Pick Species",  // TODO! Extract
						)
					}
				},
			)
			DropdownMenu(
				expanded = isSterilizedDropdownMenuIsExpanded.value,
				onDismissRequest = { isSterilizedDropdownMenuIsExpanded.value = false },
			) {
				for (option in listOf(true, false)) {
					DropdownMenuItem(
						text = { Text(if (option) "Yes" else "No") },  // TODO! Extract
						onClick = {
							onPetIsSterilizedChange(option)
							isSterilizedDropdownMenuIsExpanded.value = false
						},
					)
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun PetFormPreview() {
	TrabalhoDeApsTheme {
		PetForm(
			petName = "Cashew",
			onPetNameChange = {},
			petSpecies = Species.Cat,
			onPetSpeciesChange = {},
			petBirthDate = 0,
			onPetBirthDateChange = {},
			petWeight = 4.5,
			onPetWeightChange = {},
			petGender = Gender.Male,
			onPetGenderChange = {},
			petIsSterilized = false,
			onPetIsSterilizedChange = {},
		)
	}
}
