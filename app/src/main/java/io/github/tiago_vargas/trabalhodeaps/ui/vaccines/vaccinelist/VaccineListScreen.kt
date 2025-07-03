package io.github.tiago_vargas.trabalhodeaps.ui.vaccines.vaccinelist

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun VaccineListScreen(
	onVaccineClicked: (Vaccine) -> Unit,
	onAddClicked: () -> Unit,
	modifier: Modifier = Modifier,
	viewModel: VaccineListViewModel = viewModel(factory = VaccineListViewModel.Factory),
) {
	remember { mutableStateOf(false) }
	Scaffold(
		modifier = modifier,
		topBar = {
			TopBar(onAddClicked = onAddClicked)
		},
	) { innerPadding ->
		val vaccines = viewModel.cachedVaccines.collectAsState(initial = emptyList()).value
		LazyColumn(modifier = Modifier.padding(innerPadding)) {
			items(vaccines) { vaccine ->
				VaccineRow(
					vaccine = vaccine,
					modifier = Modifier
						.clickable(onClick = { onVaccineClicked(vaccine) })
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
	onAddClicked: () -> Unit,
	modifier: Modifier = Modifier,
) {
	TopAppBar(
		title = { Text("Vaccines") },
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
fun VaccineRow(vaccine: Vaccine, modifier: Modifier = Modifier) {
	Text(vaccine.name, modifier = modifier)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VaccineListScreenPreview() {
	TrabalhoDeApsTheme {
		Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
			VaccineListScreen(
				onVaccineClicked = {},
				onAddClicked = {},
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
			)
		}
	}
}
