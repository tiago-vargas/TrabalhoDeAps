package io.github.tiago_vargas.trabalhodeaps.ui.pets.petlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.pet.Gender
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.Species
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun PetListScreen(
	onPetClicked: (Pet) -> Unit,
	onAddClicked: () -> Unit,
	modifier: Modifier = Modifier,
	viewModel: PetListViewModel = viewModel(factory = PetListViewModel.Factory),
) {
	val shouldShowFilterSelector = remember { mutableStateOf(false) }
	Scaffold(
		modifier = modifier,
		topBar = {
			TopBar(
				onFilterClicked = { shouldShowFilterSelector.value = !shouldShowFilterSelector.value },
				onAddClicked = onAddClicked,
			)
		},
	) { innerPadding ->
		val pets = viewModel.cachedPets.collectAsState(initial = emptyList()).value
		LazyColumn(modifier = Modifier.padding(innerPadding)) {
			item {
				if (shouldShowFilterSelector.value) {
					FilterSelector(
						modifier = Modifier.padding(12.dp)
					)
				}
			}

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
private fun TopBar(
	onFilterClicked: () -> Unit,
	onAddClicked: () -> Unit,
	modifier: Modifier = Modifier,
) {
	TopAppBar(
		title = { Text("Pets") },
		modifier = modifier,
		actions = {
			IconButton(onClick = onFilterClicked) {
				Icon(
					painter = painterResource(id = R.drawable.filter_alt_24px),
					contentDescription = stringResource(R.string.filter),
				)
			}
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

@Composable
fun FilterSelector(modifier: Modifier = Modifier) {
	Column(modifier = modifier) {
		FilterSection(
			options = Species.entries,
			header = stringResource(R.string.form_field_species),
		)
		FilterSection(
			options = Gender.entries,
			header = stringResource(R.string.form_field_gender),
		)
		FilterSection(
			options = listOf(true, false),
			header = stringResource(R.string.form_field_is_sterilized),
		)
	}
}

@Composable
fun <T> FilterSection(options: Iterable<T>, header: String, modifier: Modifier = Modifier) {
	Column(modifier = modifier) {
		Text(header, style = MaterialTheme.typography.titleMedium)
		Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
			for (option in options) {
				val selected = remember { mutableStateOf(false) }
				FilterChipWithIcon(
					selected = selected.value,
					onClick = { selected.value = !selected.value },
					label = option.toString(),
				)
			}
		}
	}
}

@Composable
fun FilterChipWithIcon(
	selected: Boolean,
	onClick: () -> Unit,
	label: String,
	modifier: Modifier = Modifier,
) {
	FilterChip(
		selected = selected,
		onClick = onClick,
		label = { Text(label) },
		modifier = modifier,
		leadingIcon = if (selected) {
			{
				Icon(
					imageVector = Icons.Filled.Done,
					contentDescription = "Done icon",
					modifier = Modifier.size(FilterChipDefaults.IconSize),
				)
			}
		} else {
			null
		},
	)
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

@Preview(showBackground = true)
@Composable
fun FilterSelectorPreview() {
	FilterSelector()
}
