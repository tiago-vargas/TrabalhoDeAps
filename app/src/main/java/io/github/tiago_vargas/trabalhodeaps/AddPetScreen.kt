package io.github.tiago_vargas.trabalhodeaps

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
fun AddPetScreen(onDoneClicked: (Pet) -> Unit, modifier: Modifier = Modifier) {
	val (petName, setPetName) = remember { mutableStateOf("") }
	val (petSpecies, setPetSpecies) = remember { mutableStateOf(Species.Cat) }
	val (petBirthDate, setPetBirthDate) = remember { mutableLongStateOf(0L) }
	val (petWeight, setPetWeight) = remember { mutableDoubleStateOf(0.0) }
	val (petGender, setPetGender) = remember { mutableStateOf(Gender.Male) }
	val (petIsSterilized, setPetIsSterilized) = remember { mutableStateOf(false) }
	val scrollState = rememberScrollState()

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			TopAppBar(
				title = { Text(stringResource(R.string.add_pet)) },
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
				.verticalScroll(scrollState)
				.padding(innerPadding),
		)
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddPetScreenPreview() {
	TrabalhoDeApsTheme {
		AddPetScreen(onDoneClicked = { pet -> })
	}
}
