package io.github.tiago_vargas.trabalhodeaps

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
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetListScreen(modifier: Modifier = Modifier) {
	Scaffold(
		modifier = modifier,
		topBar = {
			TopAppBar(
				title = { Text("Pets") },
				actions = {
					IconButton(onClick = { /* TODO! doSomething() */ }) {
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
			Pet("Cajú", Species.Cat),
			Pet("Branquinho", Species.Cat),
			Pet("Salomão", Species.Cat),
			Pet("Pretinho", Species.Cat),
			Pet("Jeremias", Species.Cat),
			Pet("Pingo", Species.Cat),
			Pet("Jiló", Species.Cat),
			Pet("Pitoquinho", Species.Cat),
			Pet("Caramelo", Species.Cat),
		)
//		TODO! Make this a Lazy Column to remember rows after scrolling them out of view
		Column(modifier = Modifier.padding(innerPadding)) {
			for (pet in pets) {
				PetRow(
					pet,
					modifier = Modifier
						.padding(8.dp)
						.fillMaxWidth(),
				)
			}
		}
	}
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
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
			)
		}
	}
}
