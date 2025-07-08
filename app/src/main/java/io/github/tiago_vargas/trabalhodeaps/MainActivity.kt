package io.github.tiago_vargas.trabalhodeaps

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.tiago_vargas.trabalhodeaps.ui.ChatScreen
import io.github.tiago_vargas.trabalhodeaps.ui.LoadingScreen
import io.github.tiago_vargas.trabalhodeaps.ui.login.LoginScreen
import io.github.tiago_vargas.trabalhodeaps.ui.pets.AddPetScreen
import io.github.tiago_vargas.trabalhodeaps.ui.pets.EditPetScreen
import io.github.tiago_vargas.trabalhodeaps.ui.pets.PetDetailsScreen
import io.github.tiago_vargas.trabalhodeaps.ui.pets.petlist.PetListScreen
import io.github.tiago_vargas.trabalhodeaps.ui.pets.petlist.PetListViewModel
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import io.github.tiago_vargas.trabalhodeaps.ui.vaccines.AddVaccineScreen
import io.github.tiago_vargas.trabalhodeaps.ui.vaccines.EditVaccineScreen
import io.github.tiago_vargas.trabalhodeaps.ui.vaccines.VaccineDetailsScreen
import io.github.tiago_vargas.trabalhodeaps.ui.vaccines.vaccinelist.VaccineListScreen
import io.github.tiago_vargas.trabalhodeaps.ui.vaccines.vaccinelist.VaccineListViewModel
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
	val navController: NavHostController = rememberNavController()

	TrabalhoDeApsTheme {
		Scaffold(
			modifier = Modifier.fillMaxSize(),
			bottomBar = { BottomBar(navController = navController) },
			snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
		) { innerPadding ->
			Content(
				context = context,
				navController = navController,
				snackbarHostState = snackbarHostState,
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
			)
		}
	}
}

@Composable
fun Content(
	context: Context,
	navController: NavHostController,
	snackbarHostState: SnackbarHostState,
	modifier: Modifier = Modifier,
) {
	val petListViewModel = viewModel<PetListViewModel>(factory = PetListViewModel.Factory)
	val vaccineListViewModel =
		viewModel<VaccineListViewModel>(factory = VaccineListViewModel.Factory)

	NavHost(
		navController = navController,
		startDestination = AppScreen.Login,
		modifier = modifier,
	) {
		loginGraph(
			navController = navController,
			context = context,
			snackbarHostState = snackbarHostState,
		)
		petsGraph(navController = navController, petListViewModel = petListViewModel)
		vaccinesGraph(
			navController = navController, vaccineListViewModel = vaccineListViewModel
		)
		chatGraph()
	}
}

fun NavGraphBuilder.loginGraph(
	navController: NavHostController,
	context: Context,
	snackbarHostState: SnackbarHostState,
) {
	composable<AppScreen.Login> {
		LoginScreen(
			context = context,
			snackbarHostState = snackbarHostState,
			onLoginSuccess = {
				navController.navigate(route = AppScreen.PetList) {
					popUpTo(route = AppScreen.Login) { inclusive = true }
				}
			},
		)
	}
}

private fun NavGraphBuilder.petsGraph(
	navController: NavHostController,
	petListViewModel: PetListViewModel,
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
		val pet = petListViewModel
			.getPet(id = id)
			.collectAsState(initial = null)
			.value
		val photos = petListViewModel
			.getPhotosForPet(petId = id)
			.collectAsState(initial = emptyList())
			.value
		if (pet == null) {
			// This prevents the app from crashing
			LoadingScreen()
		} else {
			PetDetailsScreen(
				pet = pet,
				photos = photos,
				onEditClicked = { navController.navigate(route = AppScreen.EditPet(petId = id)) },
				onNavigateUp = { navController.navigateUp() },
			)
		}
	}
	composable<AppScreen.AddPet> {
		AddPetScreen(
			onDoneClicked = { pet ->
				petListViewModel.insertPet(pet)
				navController.navigateUp()
			},
		)
	}
	composable<AppScreen.EditPet> { navBackStackEntry ->
		val editPet = navBackStackEntry.toRoute<AppScreen.EditPet>()
		val id = editPet.petId
		val pet = petListViewModel
			.getPet(id = id)
			.collectAsState(initial = null)
			.value
		val photos = petListViewModel
			.getPhotosForPet(petId = id)
			.collectAsState(initial = emptyList())
			.value
		if (pet == null) {
			// This prevents the app from crashing
			LoadingScreen()
		} else {
			EditPetScreen(
				pet = pet,
				photos = photos,
				onDoneClicked = { pet ->
					petListViewModel.updatePet(pet)
					navController.navigateUp()
				},
				onDeleteClicked = { pet ->
					petListViewModel.deletePet(pet)
					navController.popBackStack(route = AppScreen.PetList, inclusive = false)
				},
				onAddPhoto = { uri ->
					petListViewModel.addPhoto(id, uri)
				},
				onRemovePhoto = { photo ->
					petListViewModel.removePhoto(photo)
				}
			)
		}
	}
}

private fun NavGraphBuilder.vaccinesGraph(
	navController: NavHostController,
	vaccineListViewModel: VaccineListViewModel,
) {
	composable<AppScreen.VaccineList> { navBackStackEntry ->
		VaccineListScreen(
			onVaccineClicked = { vaccine ->
				navController.navigate(route = AppScreen.VaccineDetails(vaccineId = vaccine.id))
			},
			onAddClicked = { navController.navigate(route = AppScreen.AddVaccine) },
		)
	}
	composable<AppScreen.VaccineDetails> { navBackStackEntry ->
		val details = navBackStackEntry.toRoute<AppScreen.VaccineDetails>()
		val id = details.vaccineId
		val vaccine = vaccineListViewModel
			.getVaccine(id = id)
			.collectAsState(initial = null)
			.value
		if (vaccine == null) {
			// This prevents the app from crashing
			LoadingScreen()
		} else {
			VaccineDetailsScreen(
				vaccine = vaccine,
				onEditClicked = { navController.navigate(route = AppScreen.EditVaccine(vaccineId = id)) },
				onNavigateUp = { navController.navigateUp() },
			)
		}
	}
	composable<AppScreen.AddVaccine> {
		AddVaccineScreen(
			onDoneClicked = { vaccine ->
				vaccineListViewModel.insertVaccine(vaccine)
				navController.navigateUp()
			},
		)
	}
	composable<AppScreen.EditVaccine> { navBackStackEntry ->
		val editVaccine = navBackStackEntry.toRoute<AppScreen.EditVaccine>()
		val id = editVaccine.vaccineId
		val vaccine = vaccineListViewModel
			.getVaccine(id = id)
			.collectAsState(initial = null)
			.value
		if (vaccine == null) {
			// This prevents the app from crashing
			LoadingScreen()
		} else {
			EditVaccineScreen(
				vaccine = vaccine,
				onDoneClicked = { vaccine ->
					vaccineListViewModel.updateVaccine(vaccine)
					navController.navigateUp()
				},
				onDeleteClicked = { vaccine ->
					vaccineListViewModel.deleteVaccine(vaccine)
					navController.popBackStack(route = AppScreen.VaccineList, inclusive = false)
				},
			)
		}
	}
}

private fun NavGraphBuilder.chatGraph() {
	composable<AppScreen.Chat> { ChatScreen() }
}

@Composable
private fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
	val selectedItem = rememberSaveable { mutableStateOf(BottomBarItem.PET_LIST) }

	NavigationBar(modifier = modifier, windowInsets = NavigationBarDefaults.windowInsets) {
		for (item in BottomBarItem.entries) {
			NavigationBarItem(
				selected = selectedItem.value == item,
				onClick = {
					val route = when (item) {
						BottomBarItem.PET_LIST -> AppScreen.PetList
						BottomBarItem.VACCINE_LIST -> AppScreen.VaccineList
						BottomBarItem.CHAT -> AppScreen.Chat
					}
					navController.navigate(route = route)
					selectedItem.value = item
				},
				icon = {
					Icon(
						painter = painterResource(id = item.iconResourceId),
						contentDescription = stringResource(item.contentDescriptionResourceId),
					)
				},
				label = { Text(stringResource(item.labelResourceId)) },
			)
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

	@Serializable
	data object VaccineList : AppScreen()

	@Serializable
	data object AddVaccine : AppScreen()

	@Serializable
	data class VaccineDetails(val vaccineId: Int) : AppScreen()

	@Serializable
	data class EditVaccine(val vaccineId: Int) : AppScreen()

	@Serializable
	data object Chat : AppScreen()
}

private enum class BottomBarItem(
	@StringRes val labelResourceId: Int,
	@DrawableRes val iconResourceId: Int,
	@StringRes val contentDescriptionResourceId: Int,
) {
	PET_LIST(
		labelResourceId = R.string.pet_list,
		iconResourceId = R.drawable.pets_24px,
		contentDescriptionResourceId = R.string.pet_list_description,
	),
	VACCINE_LIST(
		labelResourceId = R.string.vaccine_list,
		iconResourceId = R.drawable.vaccines_24px,
		contentDescriptionResourceId = R.string.vaccine_list_description,
	),
	CHAT(
		labelResourceId = R.string.chat,
		iconResourceId = R.drawable.chat_24px,
		contentDescriptionResourceId = R.string.chat_description,
	),
}
