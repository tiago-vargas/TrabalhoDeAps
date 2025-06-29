package io.github.tiago_vargas.trabalhodeaps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun PetListScreen(
	onPetClicked: (Pet) -> Unit,
	onAddClicked: () -> Unit,
	modifier: Modifier = Modifier,
	viewModel: PetListViewModel = viewModel(factory = PetListViewModel.Factory),
) {
	Scaffold(
		modifier = modifier,
		topBar = { TopBar(onAddClicked = onAddClicked) },
	) { innerPadding ->
		val pets = viewModel.cachedPets.collectAsState(initial = emptyList()).value
		LazyColumn(modifier = Modifier.padding(innerPadding)) {
			items(pets) { pet ->
				PetRow(
					pet = pet,
					modifier = Modifier
						.clickable(onClick = { onPetClicked(pet) })
						.padding(12.dp)
						.fillMaxWidth(),
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(onAddClicked: () -> Unit, modifier: Modifier = Modifier) {
	TopAppBar(
		title = { Text("Pets") },
		modifier = modifier,
		actions = {
			IconButton(onClick = onAddClicked) {
				Icon(
					imageVector = Icons.Filled.Add,
					contentDescription = stringResource(R.string.add_pet),
				)
			}
		},
	)
}

@Composable
fun PetRow(pet: Pet, modifier: Modifier = Modifier) {
	Text(pet.name, modifier = modifier)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PetListScreenPreview() {
	TrabalhoDeApsTheme {
		Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
			PetListScreen(
				onPetClicked = {},
				onAddClicked = {},
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
			)
		}
	}
}
