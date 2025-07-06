package io.github.tiago_vargas.trabalhodeaps.ui.pets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.Species
import io.github.tiago_vargas.trabalhodeaps.ui.PropertyRow
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun PetDetailsScreen(
	pet: Pet,
	onEditClicked: () -> Unit,
	onNavigateUp: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = { TopBar(onEditClicked, onNavigateUp) },
	) { innerPadding ->
		Column(
			modifier = Modifier.padding(innerPadding),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Banner(pet.name)

			Column(modifier = Modifier.padding(16.dp)) {
				PropertyRow(
					"Species",
					pet.species.toString(),
					modifier = Modifier.fillMaxWidth(),
				)
				PropertyRow(
					"Birth Date",
					pet.birthDate.toString(),
					modifier = Modifier.fillMaxWidth()
				)
				PropertyRow(
					"Weight",
					pet.weight.toString(),
					modifier = Modifier.fillMaxWidth()
				)
				PropertyRow(
					"Gender",
					pet.gender.toString(),
					modifier = Modifier.fillMaxWidth(),
				)
				PropertyRow(
					"Was Sterilized",
					pet.wasSterilized.toString(),
					modifier = Modifier.fillMaxWidth()
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
	onEditClicked: () -> Unit,
	onNavigateUp: () -> Unit,
	modifier: Modifier = Modifier,
) {
	TopAppBar(
		title = { Text(stringResource(R.string.pet_details)) },
		modifier = modifier,
		navigationIcon = {
			IconButton(onClick = onNavigateUp) {
				Icon(
					imageVector = Icons.AutoMirrored.Filled.ArrowBack,
					contentDescription = null,
				)
			}
		},
		actions = {
			IconButton(onClick = onEditClicked) {
				Icon(
					imageVector = Icons.Filled.Edit,
					contentDescription = stringResource(R.string.edit_pet),
				)
			}
		},
	)
}

@Composable
fun Banner(petName: String, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Icon(
			imageVector = Icons.Filled.Person,
			contentDescription = null,
			modifier = Modifier.size(200.dp),
		)
		Text(
			petName,
			fontWeight = FontWeight.ExtraBold,
			style = MaterialTheme.typography.headlineLarge,
		)
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PetDetailsScreenPreview() {
	val pet = Pet(name = "Cashew", species = Species.Cat)

	TrabalhoDeApsTheme {
		PetDetailsScreen(pet = pet, onEditClicked = {}, onNavigateUp = {})
	}
}
