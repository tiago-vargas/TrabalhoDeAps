package io.github.tiago_vargas.trabalhodeaps

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			App(context = this)
		}
	}
}

@Composable
fun App(context: Context) {
	val snackbarHostState = remember { SnackbarHostState() }

	TrabalhoDeApsTheme {
		Scaffold(
			modifier = Modifier.fillMaxSize(),
			snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
		) { innerPadding ->
			Content(
				context = context,
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
				snackbarHostState = snackbarHostState,
			)
		}
	}
}

@Composable
fun Content(
	context: Context,
	snackbarHostState: SnackbarHostState,
	modifier: Modifier = Modifier,
) {
	val viewModel = viewModel<LoginViewModel>()
	val isLoggedIn = viewModel.isLoggedIn.collectAsState()
	if (isLoggedIn.value) {
		PrePetListScreen(modifier = modifier)
	} else {
		LoginScreen(context = context, viewModel = viewModel, snackbarHostState = snackbarHostState, modifier = modifier)
	}
}
