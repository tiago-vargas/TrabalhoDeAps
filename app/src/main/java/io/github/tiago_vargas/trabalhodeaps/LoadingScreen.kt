package io.github.tiago_vargas.trabalhodeaps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier.fillMaxSize(),
	) {
		CircularProgressIndicator()
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoadingScreen() {
	TrabalhoDeApsTheme {
		Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
			LoadingScreen(
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
			)
		}
	}
	LoadingScreen()
}
