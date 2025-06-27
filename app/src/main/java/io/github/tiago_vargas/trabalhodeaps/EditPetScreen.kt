package io.github.tiago_vargas.trabalhodeaps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPetScreen(
	pet: Pet,
	onDoneClicked: (Pet) -> Unit,
	onDeleteClicked: (Pet) -> Unit,
	modifier: Modifier = Modifier,
) {
	val sandboxPet = pet.copy()
	val (petName, setPetName) = remember { mutableStateOf(sandboxPet.name) }
	val (petSpecies, setPetSpecies) = remember { mutableStateOf(sandboxPet.species) }
	val (petBirthDate, setPetBirthDate) = remember { mutableLongStateOf(sandboxPet.birthDate) }
	val (petWeight, setPetWeight) = remember { mutableDoubleStateOf(sandboxPet.weight) }
	val (petGender, setPetGender) = remember { mutableStateOf(sandboxPet.gender) }
	val (petIsSterilized, setPetIsSterilized) = remember { mutableStateOf(sandboxPet.wasSterilized) }
	val scrollState = rememberScrollState()

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			TopAppBar(
				title = { Text(stringResource(R.string.edit_pet)) },
			)
		},
		bottomBar = {
			BottomAppBar(
				actions = {
					Button(onClick = { /* TODO! */ }) {
						Text("Cancel")
					}
					Spacer(Modifier.weight(1f))
					Button(
						onClick = {
							val pet = Pet(
								id = pet.id,
								name = petName,
								species = petSpecies,
								birthDate = petBirthDate,
								weight = petWeight,
								gender = petGender,
								wasSterilized = petIsSterilized,
							)
							onDoneClicked(pet)
						},
					) {
						Text("Done")
					}
				},
			)
		},
	) { innerPadding ->
		Column(
			modifier = modifier
				.fillMaxWidth()
				.verticalScroll(scrollState)
				.padding(innerPadding),
		) {
			PetForm(
				petName = petName,
				onPetNameChange = setPetName,
				petSpecies = petSpecies,
				onPetSpeciesChange = setPetSpecies,
				petBirthDate = petBirthDate,
				onPetBirthDateChange = setPetBirthDate,
				petWeight = petWeight,
				onPetWeightChange = setPetWeight,
				petGender = petGender,
				onPetGenderChange = setPetGender,
				petIsSterilized = petIsSterilized,
				onPetIsSterilizedChange = setPetIsSterilized,
				modifier = modifier
					.fillMaxWidth()
					.padding(innerPadding),
			)
			Button(onClick = { onDeleteClicked(pet) }) {
				Text("Delete")  // TODO! Extract
			}
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditPetScreenPreview() {
	val pet = Pet(
		name = "Cashew",
		species = Species.Cat,
		birthDate = 1_700_000_000_000L,
		weight = 4.5,
		gender = Gender.Male,
		wasSterilized = false,
	)
	TrabalhoDeApsTheme {
		EditPetScreen(pet, onDoneClicked = { pet -> }, onDeleteClicked = { pet -> })
	}
}
