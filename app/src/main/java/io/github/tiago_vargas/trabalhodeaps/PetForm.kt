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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tiago_vargas.trabalhodeaps.data.pet.Gender
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.Species
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PetForm(pet: Pet, onPetChange: (Pet) -> Unit, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.Companion.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {
		Avatar()
		NameEntryRow(
			petName = pet.name,
			onPetNameChange = { name -> onPetChange(pet.copy(name = name)) },
		)
		SpeciesComboRow(
			petSpecies = pet.species,
			onPetSpeciesChange = { species -> onPetChange(pet.copy(species = species)) },
		)
		BirthDateActionRow(
			petBirthDate = pet.birthDate,
			onPetBirthDateChange = { birthDate -> onPetChange(pet.copy(birthDate = birthDate)) },
		)
		GenderComboRow(
			petGender = pet.gender,
			onPetGenderChange = { gender -> onPetChange(pet.copy(gender = gender)) },
		)
		WeightEntryRow(
			petWeight = pet.weight,
			onPetWeightChange = { weight -> onPetChange(pet.copy(weight = weight)) },
		)
		IsSterilizedComboRow(
			petIsSterilized = pet.wasSterilized,
			onPetIsSterilizedChange = { wasSterilized -> onPetChange(pet.copy(wasSterilized = wasSterilized)) },
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
		label = { Text(stringResource(R.string.form_field_name)) },
	)
}

@Composable
fun SpeciesComboRow(
	petSpecies: Species,
	onPetSpeciesChange: (Species) -> Unit,
	modifier: Modifier = Modifier,
) {
	ComboRow(
		value = petSpecies,
		onEntryChosen = onPetSpeciesChange,
		label = stringResource(R.string.form_field_species),
		contentDescription = stringResource(R.string.pick_species),
		entries = Species.entries,
		modifier = modifier,
	)
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
					Text(text = stringResource(R.string.okay))
				}
			},
			dismissButton = {
				Button(onClick = { showDatePickerDialog.value = false }) {
					Text(stringResource(R.string.cancel))
				}
			},
		) {
			DatePicker(state = datePickerState)
		}
	}
	OutlinedTextField(
		value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(petBirthDate)),
		onValueChange = { s -> },
		modifier = modifier,
		readOnly = true,
		label = { Text(stringResource(R.string.form_field_birth_date)) },
		trailingIcon = {
			IconButton(onClick = { showDatePickerDialog.value = true }) {
				Icon(
					imageVector = Icons.Filled.DateRange,
					contentDescription = stringResource(R.string.pick_date),
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
	ComboRow(
		value = petGender,
		onEntryChosen = onPetGenderChange,
		label = stringResource(R.string.form_field_gender),
		contentDescription = stringResource(R.string.pick_gender),
		entries = Gender.entries,
		modifier = modifier,
	)
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
		label = { Text(stringResource(R.string.form_field_weight)) },
		suffix = { Text("kg") },
	)
}

@Composable
fun IsSterilizedComboRow(
	petIsSterilized: Boolean,
	onPetIsSterilizedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
) {
	ComboRow(
		value = petIsSterilized,
		onEntryChosen = onPetIsSterilizedChange,
		label = stringResource(R.string.form_field_is_sterilized),
		contentDescription = stringResource(R.string.pick_sterilization_status),
		entries = listOf(true, false),
		modifier = modifier,
		entryToString = { e -> if (e) stringResource(R.string.yes) else stringResource(R.string.no) },
	)
}


@Composable
private fun <T> ComboRow(
	value: T,
	onEntryChosen: (T) -> Unit,
	label: String,
	contentDescription: String,
	entries: Iterable<T>,
	modifier: Modifier = Modifier,
	entryToString: @Composable (T) -> String = { e -> e.toString() },
) {
	val dropdownMenuIsExpanded = remember { mutableStateOf(false) }
	Box(modifier = modifier, contentAlignment = Alignment.Companion.TopEnd) {
		OutlinedTextField(
			value = entryToString(value),
			onValueChange = { s -> },
			readOnly = true,
			label = { Text(label) },
			trailingIcon = {
				DropDownIconButton(
					onClick = { dropdownMenuIsExpanded.value = true },
					contentDescription = contentDescription,
				)
			},
		)
		DropdownMenu(
			expanded = dropdownMenuIsExpanded.value,
			onDismissRequest = { dropdownMenuIsExpanded.value = false },
		) {
			for (option in entries) {
				DropdownMenuItem(
					text = { Text(entryToString(option)) },
					onClick = {
						onEntryChosen(option)
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
			pet = Pet(name = "", species = Species.Cat),
			onPetChange = { pet -> },
		)
	}
}
