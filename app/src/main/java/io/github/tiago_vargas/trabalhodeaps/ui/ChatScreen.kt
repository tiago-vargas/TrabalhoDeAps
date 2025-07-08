package io.github.tiago_vargas.trabalhodeaps.ui

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.asDrawable
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.data.ChatState
import io.github.tiago_vargas.trabalhodeaps.data.ChatUiEvent
import io.github.tiago_vargas.trabalhodeaps.data.ChatViewModel
import io.github.tiago_vargas.trabalhodeaps.data.Sender
import io.github.tiago_vargas.trabalhodeaps.ui.chat.ModelChatBubble
import io.github.tiago_vargas.trabalhodeaps.ui.chat.UserChatBubble
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
	val chatViewModel = viewModel<ChatViewModel>()

	val imageUri = remember { MutableStateFlow("") }
	val imagePicker = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.PickVisualMedia(),
	) { uri ->
		uri?.let {
			imageUri.value = it.toString()
		}
	}

	val chatState = chatViewModel.chatState.collectAsState().value

	Scaffold(
		topBar = { TopBar() },
	) { innerPadding ->
		Column(
			modifier = modifier
				.fillMaxSize()
				.padding(innerPadding),
			verticalArrangement = Arrangement.spacedBy(8.dp),
		) {
			LazyColumn(
				modifier = Modifier
					.weight(1.0f)
					.padding(horizontal = 12.dp),
				verticalArrangement = Arrangement.spacedBy(12.dp),
				reverseLayout = true,
			) {
				items(chatState.chatMessageList) { chat ->
					val padding = 80.dp

					when (chat.sender) {
						Sender.USER -> UserChatBubble(
							bitmap = chat.bitmap,
							prompt = chat.text,
							modifier = Modifier
								.fillMaxWidth()
								.padding(start = padding),
						)
						Sender.MODEL -> ModelChatBubble(
							response = chat.text,
							modifier = Modifier.padding(end = padding),
						)
					}
				}
			}

			ChatInputRow(
				chatViewModel = chatViewModel,
				chatState = chatState,
				imagePicker = imagePicker,
				imageUri = imageUri,
				modifier = Modifier
					.fillMaxWidth(),
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(modifier: Modifier = Modifier) {
	TopAppBar(
		title = { Text(stringResource(R.string.chat)) },
		modifier = modifier,
	)
}

@Composable
fun ChatInputRow(
	chatState: ChatState,
	imagePicker: ActivityResultLauncher<PickVisualMediaRequest>,
	imageUri: MutableStateFlow<String>,
	modifier: Modifier = Modifier,
	chatViewModel: ChatViewModel = viewModel(),
) {
	val bitmap = getBitmap(imageUri)
	val focusManager = LocalFocusManager.current
	val sendPromptAndClearEntryField = {
		chatViewModel.onEvent(ChatUiEvent.SendPrompt(chatState.prompt, bitmap))
		imageUri.update { "" }
		focusManager.clearFocus()
	}
	val isLoading = chatState.showIndicator

	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Column {
			bitmap?.let {
				Image(
					bitmap = it.asImageBitmap(),
					contentDescription = "picked image",
					modifier = Modifier
						.size(40.dp)
						.padding(bottom = 2.dp)
						.clip(RoundedCornerShape(6.dp)),
					contentScale = ContentScale.Crop,
				)
			}

			AddImageButton(
				onClick = {
					imagePicker.launch(
						PickVisualMediaRequest
							.Builder()
							.setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
							.build(),
					)
				},
			)
		}

		ChatMessageEntryRow(
			value = chatState.prompt,
			onValueChange = { s ->
				chatViewModel.onEvent(ChatUiEvent.UpdatePrompt(s))
			},
			shouldShowLoadingButton = isLoading,
			modifier = Modifier
				.weight(1.0f),
		)

		SendButton(onClick = sendPromptAndClearEntryField)
	}
}

@Composable
fun ChatMessageEntryRow(
	value: String,
	onValueChange: (String) -> Unit,
	shouldShowLoadingButton: Boolean,
	modifier: Modifier = Modifier,
) {
	OutlinedTextField(
		value = value,
		onValueChange = onValueChange,
		modifier = modifier,
		trailingIcon = {
			if (shouldShowLoadingButton) {
				CircularProgressIndicator(modifier = Modifier.size(24.dp))
			}
		},
		placeholder = { Text(stringResource(R.string.ask_something)) },
	)
}

@Composable
fun AddImageButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
	Icon(
		painter = painterResource(id = R.drawable.add_photo_alternate_24px),
		contentDescription = stringResource(R.string.add_photo),
		modifier = modifier
			.size(40.dp)
			.clickable(onClick = onClick),
	)
}

@Composable
fun SendButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
	Icon(
		modifier = modifier
			.size(40.dp)
			.clickable(onClick = onClick),
		imageVector = Icons.AutoMirrored.Rounded.Send,
		contentDescription = stringResource(R.string.send_prompt),
	)
}

@Composable
private fun getBitmap(uri: MutableStateFlow<String>): Bitmap? {
	val painter = rememberAsyncImagePainter(
		model = ImageRequest.Builder(LocalContext.current)
			.data(uri.collectAsState().value)
			.size(Size.ORIGINAL)
			.build(),
	)

	val imageState = painter.state.collectAsState()
	if (imageState.value is AsyncImagePainter.State.Success) {
		return (imageState.value as AsyncImagePainter.State.Success).result.image.asDrawable(LocalContext.current.resources).toBitmap()
	}

	return null
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatScreenPreview() {
	TrabalhoDeApsTheme {
		Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
			ChatScreen(
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
			)
		}
	}
}

@Preview
@Composable
fun ChatInputRowPreview() {
	ChatInputRow(
		chatState = ChatState(),
		imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {},
		imageUri = remember { MutableStateFlow("") },
	)
}
