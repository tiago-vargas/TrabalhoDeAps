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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Composable
fun PrePetListScreen(
	modifier: Modifier = Modifier,
	navController: NavHostController = rememberNavController(),
) {
	val coroutineScope = rememberCoroutineScope()
	val viewModel = viewModel<PetListViewModel>(factory = PetListViewModel.Factory)

	NavHost(
		navController = navController,
		startDestination = AppScreen.PetList,
		modifier = modifier,
	) {
		composable<AppScreen.PetList> { navBackStackEntry ->
			PetListScreen(
				onPetClicked = { pet ->
					navController.navigate(route = AppScreen.PetDetails(petId = pet.id))
				},
				onAddClicked = { navController.navigate(route = AppScreen.AddPet) },
			)
		}
		composable<AppScreen.PetDetails> { navBackStackEntry ->
			val details = navBackStackEntry.toRoute<AppScreen.PetDetails>()
			val id = details.petId
			val pet = viewModel
				.cachedPets
				.collectAsState(initial = emptyList())
				.value
				.find { it.id == id }
			if (pet == null) {
				// This prevents the app from crashing
				Text("Loading…")
			} else {
				PetDetailsScreen(
					pet = pet,
					onEditClicked = { navController.navigate(route = AppScreen.EditPet(petId = id)) },
					onNavigateUp = { navController.navigateUp() },
				)
			}
		}
		composable<AppScreen.AddPet> {
			AddPetScreen(
				onDoneClicked = { pet ->
					coroutineScope.launch { viewModel.petDao.insert(pet) }
					navController.navigateUp()
				},
			)
		}
		composable<AppScreen.EditPet> { navBackStackEntry ->
			val editPet = navBackStackEntry.toRoute<AppScreen.EditPet>()
			val id = editPet.petId
			val pet = viewModel
				.cachedPets
				.collectAsState(initial = emptyList())
				.value
				.find { it.id == id }
			if (pet == null) {
				// This prevents the app from crashing
				Text("Loading…")
			} else {
				EditPetScreen(
					pet = pet,
					onDoneClicked = { pet ->
						coroutineScope.launch {
							viewModel.petDao.update(pet)
							navController.navigateUp()
						}
					},
					onDeleteClicked = { pet ->
						coroutineScope.launch {
							viewModel.petDao.delete(pet)
							navController.navigate(route = AppScreen.PetList)
						}
					},
				)
			}
		}
	}
}

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

sealed class AppScreen {
	@Serializable
	data object PetList : AppScreen()

	@Serializable
	data class PetDetails(val petId: Int) : AppScreen()

	@Serializable
	data object AddPet : AppScreen()

	@Serializable
	data class EditPet(val petId: Int) : AppScreen()
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
