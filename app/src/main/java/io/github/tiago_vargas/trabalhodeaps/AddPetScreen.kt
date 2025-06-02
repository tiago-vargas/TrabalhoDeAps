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
//fun AddPetScreen(onCancelClicked: () -> Unit, modifier: Modifier = Modifier) {
fun AddPetScreen(onDoneClicked: (Pet) -> Unit, modifier: Modifier = Modifier) {
	val petName = remember { mutableStateOf("") }
	val petSpecies = remember { mutableStateOf(Species.Cat) }
	val petBirthDate = remember { mutableLongStateOf(0L) }
	val petWeight = remember { mutableDoubleStateOf(0.0) }
	val petGender = remember { mutableStateOf(Gender.Male) }
	val petIsSterilized = remember { mutableStateOf(false) }
	val scrollState = rememberScrollState()

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
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
		},
	) { innerPadding ->
		PetForm(
			petName = petName.value,
			onPetNameChange = { s -> petName.value = s },
			petSpecies = petSpecies.value,
			onPetSpeciesChange = { species -> petSpecies.value = species },
			petBirthDate = petBirthDate.longValue,
			onPetBirthDateChange = { millis -> petBirthDate.longValue = millis },
			petWeight = petWeight.doubleValue,
			onPetWeightChange = { weight -> petWeight.doubleValue = weight },
			petGender = petGender.value,
			onPetGenderChange = { gender -> petGender.value = gender },
			petIsSterilized = petIsSterilized.value,
			onPetIsSterilizedChange = { isSterilized -> petIsSterilized.value = isSterilized },
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
