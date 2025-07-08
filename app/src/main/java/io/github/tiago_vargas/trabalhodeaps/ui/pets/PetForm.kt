package io.github.tiago_vargas.trabalhodeaps.ui.pets

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.pet.Gender
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetPhoto
import io.github.tiago_vargas.trabalhodeaps.data.pet.Species
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PetForm(
	pet: Pet,
	photos: List<PetPhoto>,
	onPetChange: (Pet) -> Unit,
	onAddPhoto: (String) -> Unit,
	onRemovePhoto: (PetPhoto) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.Companion.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
	) {
		Avatar(
			profilePictureUri = pet.profilePictureUri,
			onProfilePictureChange = { uri -> onPetChange(pet.copy(profilePictureUri = uri)) }
		)
		NameEntryRow(
			petName = pet.name,
			onPetNameChange = { name -> onPetChange(pet.copy(name = name)) },
		)
		SpeciesComboRow(
			petSpecies = pet.species,
			onPetSpeciesChange = { species -> onPetChange(pet.copy(species = species)) },
		)
		BirthDateActionRow(
			petBirthDate = pet.birthDate,
			onPetBirthDateChange = { birthDate -> onPetChange(pet.copy(birthDate = birthDate)) },
		)
		GenderComboRow(
			petGender = pet.gender,
			onPetGenderChange = { gender -> onPetChange(pet.copy(gender = gender)) },
		)
		WeightEntryRow(
			petWeight = pet.weight,
			onPetWeightChange = { weight -> onPetChange(pet.copy(weight = weight)) },
		)
		IsSterilizedComboRow(
			petIsSterilized = pet.wasSterilized,
			onPetIsSterilizedChange = { wasSterilized -> onPetChange(pet.copy(wasSterilized = wasSterilized)) },
		)

		// Gallery Management
		PhotoGalleryManagement(
			photos = photos,
			onAddPhoto = onAddPhoto,
			onRemovePhoto = onRemovePhoto,
			modifier = Modifier.fillMaxWidth()
		)
	}
}

@Composable
fun Avatar(
	profilePictureUri: String?,
	onProfilePictureChange: (String?) -> Unit,
	modifier: Modifier = Modifier,
) {
	val context = LocalContext.current
	val launcher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.GetContent(),
		onResult = { uri: Uri? ->
			onProfilePictureChange(uri?.toString())
		},
	)

	Box(
		modifier = modifier
			.size(200.dp)
			.clip(CircleShape)
			.clickable { launcher.launch("image/*") },
		contentAlignment = Alignment.Center,
	) {
		if (profilePictureUri != null) {
			AsyncImage(
				model = ImageRequest.Builder(context)
					.data(profilePictureUri)
					.crossfade(true)
					.build(),
				contentDescription = stringResource(R.string.pet_profile_picture),
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.size(200.dp)
					.clip(CircleShape)
			)
		} else {
			Icon(
				imageVector = Icons.Filled.Person,
				contentDescription = stringResource(R.string.add_profile_picture),
				modifier = Modifier.size(200.dp),
				tint = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
	}
}

@Composable
fun NameEntryRow(
	petName: String,
	onPetNameChange: (String) -> Unit,
	modifier: Modifier = Modifier,
) {
	OutlinedTextField(
		petName,
		onValueChange = onPetNameChange,
		modifier = modifier,
		label = { Text(stringResource(R.string.form_field_name)) },
	)
}

@Composable
fun SpeciesComboRow(
	petSpecies: Species,
	onPetSpeciesChange: (Species) -> Unit,
	modifier: Modifier = Modifier,
) {
	ComboRow(
		value = petSpecies,
		onEntryChosen = onPetSpeciesChange,
		label = stringResource(R.string.form_field_species),
		contentDescription = stringResource(R.string.pick_species),
		entries = Species.entries,
		modifier = modifier,
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateActionRow(
	petBirthDate: Long,
	onPetBirthDateChange: (Long) -> Unit,
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
							onPetBirthDateChange(millis)
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
		value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(petBirthDate)),
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

@Composable
fun GenderComboRow(
	petGender: Gender,
	onPetGenderChange: (Gender) -> Unit,
	modifier: Modifier = Modifier,
) {
	ComboRow(
		value = petGender,
		onEntryChosen = onPetGenderChange,
		label = stringResource(R.string.form_field_gender),
		contentDescription = stringResource(R.string.pick_gender),
		entries = Gender.entries,
		modifier = modifier,
	)
}

@Composable
fun WeightEntryRow(
	petWeight: Double,
	onPetWeightChange: (Double) -> Unit,
	modifier: Modifier = Modifier,
) {
	OutlinedTextField(
		petWeight.toString(),
		onValueChange = { s -> onPetWeightChange(s.toDouble()) },
		modifier = modifier,
		label = { Text(stringResource(R.string.form_field_weight)) },
		suffix = { Text("kg") },
	)
}

@Composable
fun IsSterilizedComboRow(
	petIsSterilized: Boolean,
	onPetIsSterilizedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
) {
	ComboRow(
		value = petIsSterilized,
		onEntryChosen = onPetIsSterilizedChange,
		label = stringResource(R.string.form_field_is_sterilized),
		contentDescription = stringResource(R.string.pick_sterilization_status),
		entries = listOf(true, false),
		modifier = modifier,
		entryToString = { e -> if (e) stringResource(R.string.yes) else stringResource(R.string.no) },
	)
}


@Composable
private fun <T> ComboRow(
	value: T,
	onEntryChosen: (T) -> Unit,
	label: String,
	contentDescription: String,
	entries: Iterable<T>,
	modifier: Modifier = Modifier,
	entryToString: @Composable (T) -> String = { e -> e.toString() },
) {
	val dropdownMenuIsExpanded = remember { mutableStateOf(false) }
	Box(modifier = modifier, contentAlignment = Alignment.Companion.TopEnd) {
		OutlinedTextField(
			value = entryToString(value),
			onValueChange = { s -> },
			readOnly = true,
			label = { Text(label) },
			trailingIcon = {
				DropDownIconButton(
					onClick = { dropdownMenuIsExpanded.value = true },
					contentDescription = contentDescription,
				)
			},
		)
		DropdownMenu(
			expanded = dropdownMenuIsExpanded.value,
			onDismissRequest = { dropdownMenuIsExpanded.value = false },
		) {
			for (option in entries) {
				DropdownMenuItem(
					text = { Text(entryToString(option)) },
					onClick = {
						onEntryChosen(option)
						dropdownMenuIsExpanded.value = false
					},
				)
			}
		}
	}
}

@Composable
private fun DropDownIconButton(
	onClick: () -> Unit,
	contentDescription: String,
	modifier: Modifier = Modifier,
) {
	IconButton(onClick = onClick, modifier = modifier) {
		Icon(
			imageVector = Icons.Filled.ArrowDropDown,
			contentDescription = contentDescription,
		)
	}
}

@Composable
fun PhotoGalleryManagement(
	photos: List<PetPhoto>,
	onAddPhoto: (String) -> Unit,
	onRemovePhoto: (PetPhoto) -> Unit,
	modifier: Modifier = Modifier
) {
	val context = LocalContext.current
	val launcher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.GetMultipleContents(),
		onResult = { uris ->
			uris.forEach { uri ->
				onAddPhoto(uri.toString())
			}
		}
	)

	Column(modifier = modifier) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 8.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = stringResource(R.string.photo_gallery),
				style = MaterialTheme.typography.titleMedium
			)

			IconButton(
				onClick = { launcher.launch("image/*") }
			) {
				Icon(
					imageVector = Icons.Filled.Add,
					contentDescription = stringResource(R.string.add_photos)
				)
			}
		}

		if (photos.isNotEmpty()) {
			LazyRow(
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				items(photos) { photo ->
					Box(
						modifier = Modifier
							.width(120.dp)
							.height(120.dp)
					) {
						AsyncImage(
							model = ImageRequest.Builder(context)
								.data(photo.uri)
								.crossfade(true)
								.build(),
							contentDescription = stringResource(R.string.pet_photo),
							contentScale = ContentScale.Crop,
							modifier = Modifier
								.size(120.dp)
								.clip(RoundedCornerShape(8.dp))
								.background(MaterialTheme.colorScheme.surfaceVariant)
						)

						// Remove button
						IconButton(
							onClick = { onRemovePhoto(photo) },
							modifier = Modifier
								.align(Alignment.TopEnd)
								.size(24.dp)
								.background(
									MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
									CircleShape
								)
						) {
							Icon(
								imageVector = Icons.Filled.Close,
								contentDescription = stringResource(R.string.remove_photo),
								tint = MaterialTheme.colorScheme.error,
								modifier = Modifier.size(16.dp)
							)
						}
					}
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun PetFormPreview() {
	TrabalhoDeApsTheme {
		PetForm(
			pet = Pet(name = "", species = Species.Cat),
			photos = emptyList(),
			onPetChange = { pet -> },
			onAddPhoto = { uri -> },
			onRemovePhoto = { photo -> }
		)
	}
}
