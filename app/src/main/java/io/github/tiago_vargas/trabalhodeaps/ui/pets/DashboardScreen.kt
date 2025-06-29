package io.github.tiago_vargas.trabalhodeaps.ui.pets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.tiago_vargas.trabalhodeaps.data.pet.Gender
import io.github.tiago_vargas.trabalhodeaps.data.pet.Species
import io.github.tiago_vargas.trabalhodeaps.ui.pets.petlist.PetListViewModel

@Composable
fun Dashboard(
	modifier: Modifier = Modifier,
	petListViewModel: PetListViewModel = viewModel(factory = PetListViewModel.Factory),
) {
	val pets = petListViewModel.cachedPets.collectAsState(initial = emptyList()).value
	val catCount = pets.count { it.species == Species.Cat }
	val dogCount = pets.count { it.species == Species.Dog }
	val sterilizedCount = pets.count { it.wasSterilized }
	val nonSterilizedCount = pets.count { !it.wasSterilized }
	val maleCount = pets.count { it.gender == Gender.Male }
	val femaleCount = pets.count { it.gender == Gender.Female }

	Column (
		modifier = modifier
			.fillMaxSize()
			.padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
		Text("Dogs: $dogCount")
		Text("Cats: $catCount")
		Text("Sterilized: $sterilizedCount")
		Text("Not Sterilized: $nonSterilizedCount")
		Text("Male: $maleCount")
		Text("Female: $femaleCount")
	}
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
	Dashboard()
}
