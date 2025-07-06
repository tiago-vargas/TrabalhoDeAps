package io.github.tiago_vargas.trabalhodeaps.ui.vaccines

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import io.github.tiago_vargas.trabalhodeaps.ui.PropertyRow
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun VaccineDetailsScreen(
	vaccine: Vaccine,
	onEditClicked: () -> Unit,
	onNavigateUp: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = { TopBar(onEditClicked, onNavigateUp) },
	) { innerPadding ->
		Column(
			modifier = Modifier.padding(innerPadding),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Banner(vaccine.name)

			Column(modifier = Modifier.padding(16.dp)) {
				PropertyRow(
					stringResource(R.string.form_field_name),
					vaccine.name,
					modifier = Modifier.fillMaxWidth(),
				)
				PropertyRow(
					stringResource(R.string.form_field_description),
					vaccine.description,
					modifier = Modifier.fillMaxWidth(),
				)
				PropertyRow(
					stringResource(R.string.form_field_shot_date),
					vaccine.date.toString(),
					modifier = Modifier.fillMaxWidth()
				)
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
	onEditClicked: () -> Unit,
	onNavigateUp: () -> Unit,
	modifier: Modifier = Modifier,
) {
	TopAppBar(
		title = { Text(stringResource(R.string.vaccine_details)) },
		modifier = modifier,
		navigationIcon = {
			IconButton(onClick = onNavigateUp) {
				Icon(
					imageVector = Icons.AutoMirrored.Filled.ArrowBack,
					contentDescription = null,
				)
			}
		},
		actions = {
			IconButton(onClick = onEditClicked) {
				Icon(
					imageVector = Icons.Filled.Edit,
					contentDescription = stringResource(R.string.edit_vaccine),
				)
			}
		},
	)
}

@Composable
fun Banner(vaccineName: String, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Text(
			vaccineName,
			fontWeight = FontWeight.ExtraBold,
			style = MaterialTheme.typography.headlineLarge,
		)
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VaccineDetailsScreenPreview() {
	val vaccine = Vaccine(name = "Antirrábica", description = "Vacina contra raiva")

	TrabalhoDeApsTheme {
		VaccineDetailsScreen(vaccine = vaccine, onEditClicked = {}, onNavigateUp = {})
	}
}
