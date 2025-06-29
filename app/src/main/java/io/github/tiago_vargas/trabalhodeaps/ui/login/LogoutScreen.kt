package io.github.tiago_vargas.trabalhodeaps.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

@Composable
fun LogoutScreen(onLogoutClicked: () -> Unit, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Text("Logged In!")
		Button(onClick = onLogoutClicked) {
			Text("Logout")
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
	TrabalhoDeApsTheme {
		Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
			LogoutScreen(
				onLogoutClicked = {},
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
			)
		}
	}
}
