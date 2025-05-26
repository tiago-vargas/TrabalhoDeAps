package io.github.tiago_vargas.trabalhodeaps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
//fun AddPetScreen(onCancelClicked: () -> Unit, modifier: Modifier = Modifier) {
fun AddPetScreen(onDoneClicked: (Pet) -> Unit, modifier: Modifier = Modifier) {
	val petName = remember { mutableStateOf("") }
	val petSpecies = remember { mutableStateOf(Species.Cat) }
	val petBirthDate = remember { mutableLongStateOf(0L) }
	val petWeight = remember { mutableDoubleStateOf(0.0) }
	val petGender = remember { mutableStateOf(Gender.Male) }
	val petIsSterilized = remember { mutableStateOf(false) }

	Scaffold(modifier = modifier.fillMaxSize(), topBar = {
		TopAppBar(
			title = { Text(stringResource(R.string.add_pet)) },
//				navigationIcon = {
//					IconButton(onClick = { /* TODO! onNavigateUp */ }) {
//						Icon(
//							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//							contentDescription = null,
//						)
//					}
//				},
		)
	}, bottomBar = {
		BottomAppBar(
			actions = {
				Button(onClick = { /* TODO! */ }) {
					Text("Cancel")
				}
				Spacer(Modifier.weight(1f))
				Button(
					onClick = {
						val pet = Pet(
							name = petName.value,
							species = petSpecies.value,
							birthDate = petBirthDate.longValue,
							weight = petWeight.doubleValue.toDouble(),
							gender = petGender.value,
							wasSterilized = petIsSterilized.value,
						)
						onDoneClicked(pet)
					},
				) {
					Text("Done")
				}
			},
		)
	}) { innerPadding ->
		val scrollState = rememberScrollState()
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(innerPadding)
				.verticalScroll(scrollState),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp),
		) {
			Icon(
				imageVector = Icons.Filled.Person,
				contentDescription = null,
				modifier = Modifier.size(200.dp),
			)

			OutlinedTextField(
				petName.value,
				onValueChange = { s -> petName.value = s },
				label = { Text("Name") },  // TODO! Extract
			)

			val speciesDropdownMenuIsExpanded = remember { mutableStateOf(false) }
			Box(contentAlignment = Alignment.TopEnd) {
				OutlinedTextField(
					petSpecies.value.toString(),
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
								petSpecies.value = species
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
										petBirthDate.longValue = millis
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
					sdf.format(Date(petBirthDate.longValue))
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
			Box(contentAlignment = Alignment.TopEnd) {
				OutlinedTextField(
					petGender.value.toString(),
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
								petGender.value = gender
								genderDropdownMenuIsExpanded.value = false
							},
						)
					}
				}
			}

			OutlinedTextField(
				petWeight.doubleValue.toString(),
				onValueChange = { s -> petWeight.doubleValue = s.toDouble() },
				label = { Text("Weight") },  // TODO! Extract
				suffix = { Text("kg") },
			)

			val isSterilizedDropdownMenuIsExpanded = remember { mutableStateOf(false) }
			Box(contentAlignment = Alignment.TopEnd) {
				OutlinedTextField(
					if (petIsSterilized.value) "Yes" else "No",  // TODO! Extract
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
								petIsSterilized.value = option
								isSterilizedDropdownMenuIsExpanded.value = false
							},
						)
					}
				}
			}
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddPetScreenPreview() {
	TrabalhoDeApsTheme {
		AddPetScreen(onDoneClicked = { pet -> })
	}
}
