package io.github.tiago_vargas.trabalhodeaps.data

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
	private val _chatState = MutableStateFlow(ChatState())
	val chatState = _chatState.asStateFlow()

	fun onEvent(event: ChatUiEvent) {
		when (event) {
			is ChatUiEvent.SendPrompt -> {
				if (event.prompt.isNotEmpty()) {
					addPrompt(event.prompt, event.bitmap)
					// Trigger the loading indicator when sending prompt
					showIndicator()

					if (event.bitmap != null) {
						getResponseWithImage(event.prompt, event.bitmap)
					} else {
						getResponse(event.prompt)
					}
				}
			}

			is ChatUiEvent.UpdatePrompt -> {
				_chatState.update {
					it.copy(prompt = event.newPrompt)
				}
			}

			is ChatUiEvent.ShowIndicator -> {
				showIndicator()
			}
		}
	}

	private fun addPrompt(
		prompt: String,
		bitmap: Bitmap?,
	) {
		// Add prompt to chat list
		_chatState.update {
			it.copy(
				chatMessageList = it.chatMessageList.toMutableList().apply {
					add(index = 0, element = ChatMessage(prompt, bitmap, Sender.USER))
				},
				prompt = "",
				bitmap = null,
			)
		}
	}

	private fun getResponse(prompt: String) {
		viewModelScope.launch {
			val chat = ChatData.getResponse(prompt)
			_chatState.update {
				it.copy(
					chatMessageList = it.chatMessageList.toMutableList().apply {
						add(index = 0, element = chat)
					},
					// Hide loading indicator after response
					showIndicator = false,
				)
			}
		}
	}

	private fun getResponseWithImage(
		prompt: String,
		bitmap: Bitmap,
	) {
		viewModelScope.launch {
			val chat = ChatData.getResponseWithImage(prompt, bitmap)
			_chatState.update {
				it.copy(
					chatMessageList = it.chatMessageList.toMutableList().apply {
						add(index = 0, element = chat)
					},
					// Hide loading indicator after response
					showIndicator = false,
				)
			}
		}
	}

	private fun showIndicator() {
		// Show loading indicator
		_chatState.update {
			it.copy(showIndicator = true)
		}
	}
}
