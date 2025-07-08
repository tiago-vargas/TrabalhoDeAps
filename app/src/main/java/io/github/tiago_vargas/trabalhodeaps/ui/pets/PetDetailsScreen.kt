package io.github.tiago_vargas.trabalhodeaps.ui.pets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.pet.Pet
import io.github.tiago_vargas.trabalhodeaps.data.pet.PetPhoto
import io.github.tiago_vargas.trabalhodeaps.data.pet.Species
import io.github.tiago_vargas.trabalhodeaps.ui.PropertyRow
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun PetDetailsScreen(
	pet: Pet,
	photos: List<PetPhoto>,
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
			Banner(pet.name, pet.profilePictureUri)

			Column(modifier = Modifier.padding(16.dp)) {
				PropertyRow(
					"Species",
					pet.species.toString(),
					modifier = Modifier.fillMaxWidth(),
				)
				PropertyRow(
					"Birth Date",
					pet.birthDate.toString(),
					modifier = Modifier.fillMaxWidth()
				)
				PropertyRow(
					"Weight",
					pet.weight.toString(),
					modifier = Modifier.fillMaxWidth()
				)
				PropertyRow(
					"Gender",
					pet.gender.toString(),
					modifier = Modifier.fillMaxWidth(),
				)
				PropertyRow(
					"Was Sterilized",
					pet.wasSterilized.toString(),
					modifier = Modifier.fillMaxWidth()
				)

				// Gallery Section
				if (photos.isNotEmpty()) {
					Text(
						text = stringResource(R.string.photo_gallery),
						style = MaterialTheme.typography.titleMedium,
						modifier = Modifier.padding(vertical = 8.dp)
					)
					PhotoGallery(
						photos = photos,
						modifier = Modifier.fillMaxWidth()
					)
				}
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
		title = { Text(stringResource(R.string.pet_details)) },
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
					contentDescription = stringResource(R.string.edit_pet),
				)
			}
		},
	)
}

@Composable
fun Banner(petName: String, profilePictureUri: String?, modifier: Modifier = Modifier) {
	val context = LocalContext.current
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
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
					.background(MaterialTheme.colorScheme.surfaceVariant),
			)
		} else {
			Icon(
				imageVector = Icons.Filled.Person,
				contentDescription = null,
				modifier = Modifier.size(200.dp),
			)
		}
		Text(
			petName,
			fontWeight = FontWeight.ExtraBold,
			style = MaterialTheme.typography.headlineLarge,
		)
	}
}

@Composable
fun PhotoGallery(
	photos: List<PetPhoto>,
	modifier: Modifier = Modifier
) {
	val context = LocalContext.current

	LazyRow(
		modifier = modifier,
		horizontalArrangement = Arrangement.spacedBy(8.dp)
	) {
		items(photos) { photo ->
			AsyncImage(
				model = ImageRequest.Builder(context)
					.data(photo.uri)
					.crossfade(true)
					.build(),
				contentDescription = stringResource(R.string.pet_photo),
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.width(120.dp)
					.height(120.dp)
					.clip(RoundedCornerShape(8.dp))
					.background(MaterialTheme.colorScheme.surfaceVariant)
			)
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PetDetailsScreenPreview() {
	val pet = Pet(name = "Cashew", species = Species.Cat)
	val photos = emptyList<PetPhoto>()

	TrabalhoDeApsTheme {
		PetDetailsScreen(pet = pet, photos = photos, onEditClicked = {}, onNavigateUp = {})
	}
}
