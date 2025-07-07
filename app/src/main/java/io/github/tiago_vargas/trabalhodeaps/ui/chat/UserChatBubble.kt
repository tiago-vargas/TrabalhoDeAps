package io.github.tiago_vargas.trabalhodeaps.ui.chat

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import io.github.tiago_vargas.trabalhodeaps.R

val chatBubbleRadius = 16.dp

@Composable
fun UserChatBubble(
	bitmap: Bitmap?,
	prompt: String,
	modifier: Modifier = Modifier,
) {

	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.spacedBy(4.dp),
		horizontalAlignment = Alignment.End,
	) {
		bitmap?.let {
			Image(
				bitmap = it.asImageBitmap(),
				contentDescription = "image",
				modifier = Modifier
					.fillMaxWidth()
					.clip(RoundedCornerShape(chatBubbleRadius)),
				contentScale = ContentScale.Crop,
			)
		}

		Text(
			text = prompt,
			modifier = Modifier
				.clip(RoundedCornerShape(chatBubbleRadius))
				.background(MaterialTheme.colorScheme.primary)
				.padding(16.dp),
			color = MaterialTheme.colorScheme.onPrimary,
		)
	}
}

// Extension function to convert drawable resource to bitmap
fun Context.drawableToBitmap(drawableId: Int, width: Int = 200, height: Int = 200): Bitmap? {
	val drawable = ContextCompat.getDrawable(this, drawableId) ?: return null

	// If it's already a bitmap drawable, just return the bitmap
	if (drawable is BitmapDrawable && drawable.bitmap != null) {
		return drawable.bitmap
	}

	// Create a bitmap from the drawable
	val bitmap = createBitmap(width, height)
	val canvas = Canvas(bitmap)
	drawable.setBounds(0, 0, canvas.width, canvas.height)
	drawable.draw(canvas)

	return bitmap
}

@Preview
@Composable
fun UserChatBubblePreview() {
	UserChatBubble(
		bitmap = null,
		prompt = "Hello!",
	)
}

@Preview
@Composable
fun UserChatBubbleWithImagePreview() {
	val context = LocalContext.current
	val bitmap = context.drawableToBitmap(R.drawable.beluga, 300, 200)

	UserChatBubble(
		bitmap = bitmap,
		prompt = "Hi! This is my cat! Is he broken??",
	)
}
