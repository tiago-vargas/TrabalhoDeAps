package io.github.tiago_vargas.trabalhodeaps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
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
		Avatar()
		NameEntryRow(petName = petName, onPetNameChange = onPetNameChange)
		SpeciesComboRow(petSpecies = petSpecies, onPetSpeciesChange = onPetSpeciesChange)
		BirthDateActionRow(petBirthDate = petBirthDate, onPetBirthDateChange = onPetBirthDateChange)
		GenderComboRow(petGender = petGender, onPetGenderChange = onPetGenderChange)
		WeightEntryRow(petWeight = petWeight, onPetWeightChange = onPetWeightChange)
		IsSterilizedComboRow(
			petIsSterilized = petIsSterilized,
			onPetIsSterilizedChange = onPetIsSterilizedChange,
		)
	}
}

@Composable
fun Avatar(modifier: Modifier = Modifier) {
	Icon(
		imageVector = Icons.Filled.Person,
		contentDescription = null,
		modifier = modifier.size(200.dp),
	)
}

@Composable
fun NameEntryRow(
	petName: String,
	onPetNameChange: (String) -> Unit,
	modifier: Modifier = Modifier,
) {
	OutlinedTextField(
		petName,
		onValueChange = onPetNameChange,
		modifier = modifier,
		label = { Text("Name") },  // TODO! Extract
	)
}

@Composable
fun SpeciesComboRow(
	petSpecies: Species,
	onPetSpeciesChange: (Species) -> Unit,
	modifier: Modifier = Modifier,
) {
	val dropdownMenuIsExpanded = remember { mutableStateOf(false) }
	Box(modifier = modifier, contentAlignment = Alignment.Companion.TopEnd) {
		OutlinedTextField(
			petSpecies.toString(),
			readOnly = true,
			onValueChange = { s -> },
			label = { Text("Species") },  // TODO! Extract
			trailingIcon = {
				DropDownIconButton(
					onClick = { dropdownMenuIsExpanded.value = true },
					contentDescription = "Pick Species",  // TODO! Extract
				)
			},
		)
		DropdownMenu(
			expanded = dropdownMenuIsExpanded.value,
			onDismissRequest = { dropdownMenuIsExpanded.value = false },
		) {
			for (species in Species.entries) {
				DropdownMenuItem(
					text = { Text(species.toString()) },
					onClick = {
						onPetSpeciesChange(species)
						dropdownMenuIsExpanded.value = false
					},
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateActionRow(
	petBirthDate: Long,
	onPetBirthDateChange: (Long) -> Unit,
	modifier: Modifier = Modifier,
) {
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
		onValueChange = { s -> },
		modifier = modifier,
		readOnly = true,
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
}

@Composable
fun GenderComboRow(
	petGender: Gender,
	onPetGenderChange: (Gender) -> Unit,
	modifier: Modifier = Modifier,
) {
	val dropdownMenuIsExpanded = remember { mutableStateOf(false) }
	Box(modifier = modifier, contentAlignment = Alignment.Companion.TopEnd) {
		OutlinedTextField(
			petGender.toString(),
			readOnly = true,
			onValueChange = { s -> },
			label = { Text("Gender") },  // TODO! Extract
			trailingIcon = {
				DropDownIconButton(
					onClick = { dropdownMenuIsExpanded.value = true },
					contentDescription = "Pick Gender",  // TODO! Extract
				)
			},
		)
		DropdownMenu(
			expanded = dropdownMenuIsExpanded.value,
			onDismissRequest = { dropdownMenuIsExpanded.value = false },
		) {
			for (gender in Gender.entries) {
				DropdownMenuItem(
					text = { Text(gender.toString()) },
					onClick = {
						onPetGenderChange(gender)
						dropdownMenuIsExpanded.value = false
					},
				)
			}
		}
	}
}

@Composable
fun WeightEntryRow(
	petWeight: Double,
	onPetWeightChange: (Double) -> Unit,
	modifier: Modifier = Modifier,
) {
	OutlinedTextField(
		petWeight.toString(),
		onValueChange = { s -> onPetWeightChange(s.toDouble()) },
		modifier = modifier,
		label = { Text("Weight") },  // TODO! Extract
		suffix = { Text("kg") },
	)
}

@Composable
fun IsSterilizedComboRow(
	petIsSterilized: Boolean,
	onPetIsSterilizedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
) {
	val dropdownMenuIsExpanded = remember { mutableStateOf(false) }
	Box(modifier = modifier, contentAlignment = Alignment.Companion.TopEnd) {
		OutlinedTextField(
			if (petIsSterilized) "Yes" else "No",  // TODO! Extract
			readOnly = true,
			onValueChange = { s -> },
			label = { Text("Is Sterilized?") },  // TODO! Extract
			trailingIcon = {
				DropDownIconButton(
					onClick = { dropdownMenuIsExpanded.value = true },
					contentDescription = "Pick Species",  // TODO! Extract
				)
			},
		)
		DropdownMenu(
			expanded = dropdownMenuIsExpanded.value,
			onDismissRequest = { dropdownMenuIsExpanded.value = false },
		) {
			for (option in listOf(true, false)) {
				DropdownMenuItem(
					text = { Text(if (option) "Yes" else "No") },  // TODO! Extract
					onClick = {
						onPetIsSterilizedChange(option)
						dropdownMenuIsExpanded.value = false
					},
				)
			}
		}
	}
}

@Composable
private fun DropDownIconButton(
	onClick: () -> Unit,
	contentDescription: String,
	modifier: Modifier = Modifier,
) {
	IconButton(onClick = onClick, modifier = modifier) {
		Icon(
			imageVector = Icons.Filled.ArrowDropDown,
			contentDescription = contentDescription,
		)
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
