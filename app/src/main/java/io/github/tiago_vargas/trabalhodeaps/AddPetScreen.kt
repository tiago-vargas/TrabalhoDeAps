package io.github.tiago_vargas.trabalhodeaps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(modifier: Modifier = Modifier) {
	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			TopAppBar(title = { Text(stringResource(R.string.add_pet)) })
		},
		bottomBar = {
			BottomAppBar(
				actions = {
					Button(onClick = { /* TODO! */ }) {
						Text("Cancel")
					}
					Spacer(Modifier.weight(1f))
					Button(onClick = { /* TODO! */ }) {
						Text("Done")
					}
				},
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(innerPadding),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp),
		) {
			Icon(
				imageVector = Icons.Filled.Person,
				contentDescription = null,
				modifier = Modifier.size(200.dp),
			)
			OutlinedTextField("Name", onValueChange = {})
			OutlinedTextField("Species", onValueChange = {})
			OutlinedTextField("Birth Date", onValueChange = {})
			OutlinedTextField("Weight", onValueChange = {})
			OutlinedTextField("Gender", onValueChange = {})
			OutlinedTextField("Was Sterilized", onValueChange = {})
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddPetScreenPreview() {
	TrabalhoDeApsTheme {
		AddPetScreen()
	}
}
