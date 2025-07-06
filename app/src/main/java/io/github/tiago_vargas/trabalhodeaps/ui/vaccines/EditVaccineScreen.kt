package io.github.tiago_vargas.trabalhodeaps.ui.vaccines

import androidx.compose.foundation.layout.Column
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
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditVaccineScreen(
	vaccine: Vaccine,
	onDoneClicked: (Vaccine) -> Unit,
	onDeleteClicked: (Vaccine) -> Unit,
	modifier: Modifier = Modifier,
) {
	val (sandboxVaccine, setSandboxVaccine) = remember { mutableStateOf(vaccine.copy()) }
	val scrollState = rememberScrollState()

	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			TopAppBar(
				title = { Text(stringResource(R.string.edit_vaccine)) },
			)
		},
		bottomBar = {
			BottomBar(onDoneClicked = { onDoneClicked(sandboxVaccine) })
		},
	) { innerPadding ->
		Column(
			modifier = modifier
				.fillMaxWidth()
				.verticalScroll(scrollState)
				.padding(innerPadding),
		) {
			VaccineForm(
				vaccine = sandboxVaccine,
				onVaccineChange = setSandboxVaccine,
				modifier = modifier
					.fillMaxWidth()
					.padding(innerPadding),
			)
			Button(onClick = { onDeleteClicked(sandboxVaccine) }) {
				Text("Delete")  // TODO! Extract
			}
		}
	}
}

@Composable
private fun BottomBar(onDoneClicked: () -> Unit, modifier: Modifier = Modifier) {
	BottomAppBar(
		actions = {
			Button(onClick = { /* TODO! */ }) {
				Text("Cancel")
			}
			Spacer(Modifier.weight(1.0f))
			Button(onClick = onDoneClicked) {
				Text("Done")
			}
		},
		modifier = modifier,
	)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditVaccineScreenPreview() {
	val vaccine = Vaccine(
		name = "Cashew",
		description = "This vaccine is about preventing this and this",
		date = 1_700_000_000_000L,
	)
	TrabalhoDeApsTheme {
		EditVaccineScreen(vaccine, onDoneClicked = { vaccine -> }, onDeleteClicked = { vaccine -> })
	}
}
