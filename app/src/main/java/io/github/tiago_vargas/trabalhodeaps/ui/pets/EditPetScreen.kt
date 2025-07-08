package io.github.tiago_vargas.trabalhodeaps.ui.pets

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.pet.Gender
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetPhoto
import io.github.tiago_vargas.trabalhodeaps.data.pet.Species
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPetScreen(
	pet: Pet,
	photos: List<PetPhoto>,
	onDoneClicked: (Pet) -> Unit,
	onDeleteClicked: (Pet) -> Unit,
	onAddPhoto: (String) -> Unit,
	onRemovePhoto: (PetPhoto) -> Unit,
	modifier: Modifier = Modifier,
) {
	val (sandboxPet, setSandboxPet) = remember { mutableStateOf(pet.copy()) }
	val scrollState = rememberScrollState()

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			TopAppBar(
				title = { Text(stringResource(R.string.edit_pet)) },
			)
		},
		bottomBar = {
			BottomBar(onDoneClicked = { onDoneClicked(sandboxPet) })
		},
	) { innerPadding ->
		Column(
			modifier = modifier
				.fillMaxWidth()
				.verticalScroll(scrollState)
				.padding(innerPadding),
		) {
			PetForm(
				pet = sandboxPet,
				photos = photos,
				onPetChange = setSandboxPet,
				onAddPhoto = onAddPhoto,
				onRemovePhoto = onRemovePhoto,
				modifier = modifier
					.fillMaxWidth()
					.padding(innerPadding),
			)
			Button(onClick = { onDeleteClicked(sandboxPet) }) {
				Text("Delete")  // TODO! Extract
			}
		}
	}
}

@Composable
private fun BottomBar(onDoneClicked: () -> Unit, modifier: Modifier = Modifier) {
	BottomAppBar(
		actions = {
			Button(onClick = { /* TODO! */ }) {
				Text("Cancel")
			}
			Spacer(Modifier.weight(1.0f))
			Button(onClick = onDoneClicked) {
				Text("Done")
			}
		},
		modifier = modifier,
	)
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
		EditPetScreen(
			pet = pet,
			photos = emptyList(),
			onDoneClicked = { pet -> },
			onDeleteClicked = { pet -> },
			onAddPhoto = { },
			onRemovePhoto = { }
		)
	}
}
