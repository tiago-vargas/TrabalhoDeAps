package io.github.tiago_vargas.trabalhodeaps.ui.pets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPetScreen(
	pet: Pet,
	photos: List<PetPhoto>,
	vaccines: List<Vaccine>,
	onDoneClicked: (Pet) -> Unit,
	onDeleteClicked: (Pet) -> Unit,
	onAddPhoto: (String) -> Unit,
	onRemovePhoto: (PetPhoto) -> Unit,
	onAddVaccine: () -> Unit,
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

			// Vaccines Section
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
			) {
				Text(
					text = stringResource(R.string.vaccines),
					style = MaterialTheme.typography.titleMedium
				)
				IconButton(onClick = onAddVaccine) {
					Icon(
						imageVector = Icons.Filled.Add,
						contentDescription = stringResource(R.string.add_vaccine),
						tint = MaterialTheme.colorScheme.primary
					)
				}
			}

			if (vaccines.isNotEmpty()) {
				vaccines.forEach { vaccine ->
					VaccineEditItem(vaccine = vaccine)
				}
			} else {
				Text(
					text = stringResource(R.string.no_vaccines_yet),
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
				)
			}

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

@Composable
fun VaccineEditItem(
	vaccine: Vaccine,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp, vertical = 4.dp)
	) {
		Text(
			text = vaccine.name,
			style = MaterialTheme.typography.titleSmall
		)
		Text(
			text = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
				.format(java.util.Date(vaccine.date)),
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)
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
		EditPetScreen(
			pet = pet,
			photos = emptyList(),
			vaccines = emptyList(),
			onDoneClicked = { pet -> },
			onDeleteClicked = { pet -> },
			onAddPhoto = { },
			onRemovePhoto = { },
			onAddVaccine = { }
		)
	}
}
