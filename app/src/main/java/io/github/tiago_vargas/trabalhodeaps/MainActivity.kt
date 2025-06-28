package io.github.tiago_vargas.trabalhodeaps

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			App(context = this)
		}
	}
}

@Composable
fun App(context: Context) {
	val snackbarHostState = remember { SnackbarHostState() }

	TrabalhoDeApsTheme {
		Scaffold(
			modifier = Modifier.fillMaxSize(),
			snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
		) { innerPadding ->
			Content(
				context = context,
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
				snackbarHostState = snackbarHostState,
			)
		}
	}
}

@Composable
fun Content(
	context: Context,
	snackbarHostState: SnackbarHostState,
	modifier: Modifier = Modifier,
) {
	val navController: NavHostController = rememberNavController()
	val viewModel = viewModel<LoginViewModel>()
	val coroutineScope = rememberCoroutineScope()
	val petListViewModel = viewModel<PetListViewModel>(factory = PetListViewModel.Factory)

	NavHost(
		navController = navController,
		startDestination = AppScreen.Login,
		modifier = modifier,
	) {
		composable<AppScreen.Login> {
			LoginScreen(
				context = context,
				viewModel = viewModel,
				snackbarHostState = snackbarHostState,
				onLoginSuccess = {
					navController.navigate(route = AppScreen.PetList) {
						popUpTo(route = AppScreen.Login) { inclusive = true }
					}
				},
				modifier = modifier,
			)
		}
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
			val pet = petListViewModel
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
					coroutineScope.launch { petListViewModel.petDao.insert(pet) }
					navController.navigateUp()
				},
			)
		}
		composable<AppScreen.EditPet> { navBackStackEntry ->
			val editPet = navBackStackEntry.toRoute<AppScreen.EditPet>()
			val id = editPet.petId
			val pet = petListViewModel
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
							petListViewModel.petDao.update(pet)
							navController.navigateUp()
						}
					},
					onDeleteClicked = { pet ->
						coroutineScope.launch {
							petListViewModel.petDao.delete(pet)
							navController.navigate(route = AppScreen.PetList)
						}
					},
				)
			}
		}
	}
}

private sealed class AppScreen {
	@Serializable
	data object Login : AppScreen()

	@Serializable
	data object PetList : AppScreen()

	@Serializable
	data class PetDetails(val petId: Int) : AppScreen()

	@Serializable
	data object AddPet : AppScreen()

	@Serializable
	data class EditPet(val petId: Int) : AppScreen()
}
