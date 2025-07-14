package io.github.tiago_vargas.trabalhodeaps.ui.vaccines

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import io.github.tiago_vargas.trabalhodeaps.ui.pets.ComboRow
import io.github.tiago_vargas.trabalhodeaps.ui.pets.petlist.PetListViewModel
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun AddVaccineScreen(
	petId: Int?,
	onDoneClicked: (Vaccine) -> Unit,
	modifier: Modifier = Modifier,
	petListViewModel: PetListViewModel = viewModel(factory = PetListViewModel.Factory)
) {
	val pets = petListViewModel.cachedPets.collectAsState(initial = emptyList()).value
	val (selectedPetId, setSelectedPetId) = remember { mutableStateOf(petId) }

	// If no petId is provided and no pets available, show loading or empty state
	if (petId == null && pets.isEmpty()) {
		Column(
			modifier = modifier.fillMaxSize().padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Text(
				text = "No pets available",
				style = MaterialTheme.typography.headlineSmall,
				textAlign = TextAlign.Center
			)
		}
		return
	}

	// Determine the final petId to use
	val finalPetId = selectedPetId ?: pets.firstOrNull()?.id
	if (finalPetId == null) {
		// This shouldn't happen, but just in case
		return
	}

	val (vaccine, setVaccine) = remember(finalPetId) {
		mutableStateOf(Vaccine(petId = finalPetId, name = "", description = ""))
	}
	val scrollState = rememberScrollState()

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = { TopBar() },
		bottomBar = {
			BottomBar(onDoneClicked = { onDoneClicked(vaccine) })
		},
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.verticalScroll(scrollState)
				.padding(innerPadding)
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(8.dp),
		) {
			// Show pet selection only if no specific petId was provided
			if (petId == null) {
				val selectedPet = pets.find { it.id == selectedPetId }
				ComboRow(
					value = selectedPet ?: pets.first(),
					onEntryChosen = { pet ->
						setSelectedPetId(pet.id)
						setVaccine(vaccine.copy(petId = pet.id))
					},
					label = stringResource(R.string.select_pet),
					contentDescription = stringResource(R.string.select_pet),
					entries = pets,
					entryToString = { pet -> pet.name },
					modifier = Modifier.fillMaxWidth()
				)
			}

			VaccineForm(
				vaccine = vaccine,
				onVaccineChange = setVaccine,
				modifier = Modifier.fillMaxWidth()
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
	TopAppBar(
		title = { Text(stringResource(R.string.add_vaccine)) },
	)
}

@Composable
private fun BottomBar(onDoneClicked: () -> Unit, modifier: Modifier = Modifier) {
	BottomAppBar(
		actions = {
			Button(onClick = { /* TODO! */ }) {
				Text("Cancel")
			}
			Spacer(Modifier.weight(1f))
			Button(onClick = onDoneClicked) {
				Text("Done")
			}
		},
		modifier = modifier,
	)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddVaccineScreenPreview() {
	TrabalhoDeApsTheme {
		AddVaccineScreen(petId = 1, onDoneClicked = { vaccine -> })
	}
}
