package io.github.tiago_vargas.trabalhodeaps

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun AddPetScreen(onDoneClicked: (Pet) -> Unit, modifier: Modifier = Modifier) {
	val (pet, setPet) = remember { mutableStateOf(Pet(name = "", species = Species.Cat)) }
	val scrollState = rememberScrollState()

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = { TopBar() },
		bottomBar = {
			BottomBar(onDoneClicked = { onDoneClicked(pet) })
		},
	) { innerPadding ->
		PetForm(
			pet = pet,
			onPetChange = setPet,
			modifier = modifier
				.fillMaxWidth()
				.verticalScroll(scrollState)
				.padding(innerPadding),
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
	TopAppBar(
		title = { Text(stringResource(R.string.add_pet)) },
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
fun AddPetScreenPreview() {
	TrabalhoDeApsTheme {
		AddPetScreen(onDoneClicked = { pet -> })
	}
}
