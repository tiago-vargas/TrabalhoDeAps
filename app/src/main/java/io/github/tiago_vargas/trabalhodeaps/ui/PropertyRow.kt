package io.github.tiago_vargas.trabalhodeaps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PropertyRow(key: String, value: String, modifier: Modifier = Modifier) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.SpaceBetween,
	) {
		Text(key)
		Text(value)
	}
}

@Preview
@Composable
fun PropertyRowPreview() {
	PropertyRow(key = "Name", value = "Value")
}
