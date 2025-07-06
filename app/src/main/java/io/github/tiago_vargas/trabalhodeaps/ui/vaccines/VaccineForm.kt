package io.github.tiago_vargas.trabalhodeaps.ui.vaccines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.vaccine.Vaccine
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun VaccineForm(
	vaccine: Vaccine,
	onVaccineChange: (Vaccine) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.Companion.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {
		NameEntryRow(
			vaccineName = vaccine.name,
			onVaccineNameChange = { name -> onVaccineChange(vaccine.copy(name = name)) },
		)
		DescriptionEntryRow(
			vaccineDescription = vaccine.description,
			onVaccineDescriptionChange = { description -> onVaccineChange(vaccine.copy(description = description)) },
		)
		DateActionRow(
			vaccineShotDate = vaccine.date,
			onVaccineShotDateChange = { date -> onVaccineChange(vaccine.copy(date = date)) },
		)
	}
}

@Composable
fun NameEntryRow(
	vaccineName: String,
	onVaccineNameChange: (String) -> Unit,
	modifier: Modifier = Modifier,
) {
	OutlinedTextField(
		vaccineName,
		onValueChange = onVaccineNameChange,
		modifier = modifier,
		label = { Text(stringResource(R.string.form_field_name)) },
	)
}

@Composable
fun DescriptionEntryRow(
	vaccineDescription: String,
	onVaccineDescriptionChange: (String) -> Unit,
	modifier: Modifier = Modifier,
) {
	OutlinedTextField(
		vaccineDescription,
		onValueChange = onVaccineDescriptionChange,
		modifier = modifier,
		label = { Text(stringResource(R.string.form_field_description)) },
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateActionRow(
	vaccineShotDate: Long,
	onVaccineShotDateChange: (Long) -> Unit,
	modifier: Modifier = Modifier,
) {
	val showDatePickerDialog = remember { mutableStateOf(false) }
	val datePickerState = rememberDatePickerState()
	val selectedDate = remember { mutableStateOf("") }
	if (showDatePickerDialog.value) {
		DatePickerDialog(
			onDismissRequest = { showDatePickerDialog.value = false },
			confirmButton = {
				Button(
					onClick = {
						datePickerState.selectedDateMillis?.let { millis ->
							selectedDate.value = millis.toString()
							onVaccineShotDateChange(millis)
						}
						showDatePickerDialog.value = false
					},
				) {
					Text(text = stringResource(R.string.okay))
				}
			},
			dismissButton = {
				Button(onClick = { showDatePickerDialog.value = false }) {
					Text(stringResource(R.string.cancel))
				}
			},
		) {
			DatePicker(state = datePickerState)
		}
	}
	OutlinedTextField(
		value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(vaccineShotDate)),
		onValueChange = { s -> },
		modifier = modifier,
		readOnly = true,
		label = { Text(stringResource(R.string.form_field_birth_date)) },
		trailingIcon = {
			IconButton(onClick = { showDatePickerDialog.value = true }) {
				Icon(
					imageVector = Icons.Filled.DateRange,
					contentDescription = stringResource(R.string.pick_date),
				)
			}
		},
	)
}

@Preview(showBackground = true)
@Composable
fun VaccineFormPreview() {
	TrabalhoDeApsTheme {
		VaccineForm(
			vaccine = Vaccine(name = "", description = ""),
			onVaccineChange = { vaccine -> },
		)
	}
}
