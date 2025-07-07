package io.github.tiago_vargas.trabalhodeaps.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ModelChatBubble(response: String, modifier: Modifier = Modifier) {
	Column(modifier = modifier) {
		Text(
			text = response,
			modifier = Modifier
				.fillMaxWidth()
				.clip(RoundedCornerShape(chatBubbleRadius))
				.background(MaterialTheme.colorScheme.secondary)
				.padding(16.dp),
			color = MaterialTheme.colorScheme.onSecondary,
		)
	}
}

@Preview
@Composable
fun ModelChatBubblePreview() {
	ModelChatBubble(response = "Your cat is definitely not broken! That's a very famous cat meme, often called the \"Polite Cat\" or \"He Looks Very Polite\" meme. Your cat has a naturally symmetrical and slightly upward-curving mouth that makes him look like he's smiling politely, which is why the image became so popular. It's a perfectly normal and adorable expression for a cat!")
}
