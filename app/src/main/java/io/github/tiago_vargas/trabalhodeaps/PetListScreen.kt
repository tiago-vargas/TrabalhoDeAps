package io.github.tiago_vargas.trabalhodeaps

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrePetListScreen(
	modifier: Modifier = Modifier,
	navController: NavHostController = rememberNavController(),
) {
	NavHost(
		navController = navController,
		startDestination = AppScreen.PetList.name,
		modifier = modifier,
	) {
		composable(route = AppScreen.PetList.name) {
			PetListScreen(
				onPetClicked = { pet ->
					navController.navigate(route = "${AppScreen.PetDetails.name}/${pet.id}")
				},
				onAddClicked = {
					navController.navigate(route = AppScreen.AddPet.name)
				}
			)
		}
		composable(route = "${AppScreen.PetDetails.name}/{petId}") { navBackStackEntry ->
			val petId = navBackStackEntry.arguments?.getString("petId")?.toInt()
			val pet = Pet(name = "", species = Species.Cat).fromId(petId ?: 0)
			PetDetailsScreen(pet)
		}
		composable(route = AppScreen.AddPet.name) {
			AddPetScreen()
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetListScreen(
	onPetClicked: (Pet) -> Unit,
	onAddClicked: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Scaffold(
		modifier = modifier,
		topBar = {
			TopAppBar(
				title = { Text("Pets") },
				actions = {
					IconButton(onClick = onAddClicked) {
						Icon(
							imageVector = Icons.Filled.Add,
							contentDescription = stringResource(R.string.add_pet),
						)
					}
				},
			)
		},
	) { innerPadding ->
		val pets = listOf(
			Pet(id = 0, name = "Cajú", species = Species.Cat),
			Pet(id = 1, name = "Branquinho", species = Species.Cat),
			Pet(id = 2, name = "Salomão", species = Species.Cat),
			Pet(id = 3, name = "Pretinho", species = Species.Cat),
			Pet(id = 4, name = "Jeremias", species = Species.Cat),
			Pet(id = 5, name = "Pingo", species = Species.Cat),
			Pet(id = 6, name = "Jiló", species = Species.Cat),
			Pet(id = 7, name = "Pitoquinho", species = Species.Cat),
			Pet(id = 8, name = "Caramelo", species = Species.Cat),
		)
//		TODO! Make this a Lazy Column to remember rows after scrolling them out of view
		Column(modifier = Modifier.padding(innerPadding)) {
			for (pet in pets) {
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

private enum class AppScreen(@StringRes val title: Int) {
	PetList(title = R.string.pet_list),
	PetDetails(title = R.string.pet_details),
	AddPet(title = R.string.add_pet),
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
