package io.github.tiago_vargas.trabalhodeaps.data

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import io.github.tiago_vargas.trabalhodeaps.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class ChatMessage(
	val text: String,
	val bitmap: Bitmap?,
	val sender: Sender,
)

enum class Sender {
	USER,
	MODEL,
}

object ChatData {
	const val API_KEY = BuildConfig.API_KEY

	suspend fun getResponse(prompt: String): ChatMessage {
		val generativeModel = GenerativeModel(
			modelName = "gemini-1.5-pro-latest",
			apiKey = API_KEY,
			systemInstruction = content {
				text("You are a pet expert assistant. Only answer questions about cats, dogs, and other pets. If asked about anything else, politely redirect the conversation back to pets.")
			},
		)

		try {
			val response = withContext(Dispatchers.IO) {
				generativeModel.generateContent(prompt)
			}

			return ChatMessage(
				text = response.text ?: "error",
				bitmap = null,
				sender = Sender.MODEL,
			)
		} catch (e: Exception) {
			return ChatMessage(
				text = e.message ?: "error",
				bitmap = null,
				sender = Sender.MODEL,
			)
		}
	}

	suspend fun getResponseWithImage(
		prompt: String,
		bitmap: Bitmap,
	): ChatMessage {
		val generativeModel = GenerativeModel(
			modelName = "gemini-1.5-flash",
			apiKey = API_KEY,
			systemInstruction = content {
				text(
					"""
					You are a pet image analyzer. Only analyze and respond to images that contain:
					- Cats, dogs, or other pets
					- Pet toys, food, or accessories
					- Pet-related environments (kennels, pet beds, etc.)
					If the image doesn't contain pet-related content, respond: "I can only analyze pet-related images. Please share a photo of pets, pet food, toys, or accessories."
					"""
				)
			},
		)

		try {
			val inputContent = content {
				image(bitmap)
				text(prompt)
			}

			val response = withContext(Dispatchers.IO) {
				generativeModel.generateContent(inputContent)
			}

			return ChatMessage(
				text = response.text ?: "error",
				bitmap = null,
				sender = Sender.MODEL,
			)
		} catch (e: Exception) {
			return ChatMessage(
				text = e.message ?: "error",
				bitmap = null,
				sender = Sender.MODEL,
			)
		}
	}
}

sealed class ChatUiEvent {
	data class UpdatePrompt(val newPrompt: String) : ChatUiEvent()

	data class SendPrompt(
		val prompt: String,
		val bitmap: Bitmap?,
	) : ChatUiEvent()

	data object ShowIndicator : ChatUiEvent()
}

data class ChatState(
	val chatMessageList: MutableList<ChatMessage> = mutableListOf(),
	var prompt: String = "",
	val bitmap: Bitmap? = null,
	val showIndicator: Boolean = false,
)
