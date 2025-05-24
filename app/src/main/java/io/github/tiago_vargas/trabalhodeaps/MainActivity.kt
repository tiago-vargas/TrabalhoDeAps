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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			App(this)
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
fun Content(context: Context, modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
	val isLoggedIn = remember { mutableStateOf(false) }
	val coroutineScope = rememberCoroutineScope()

	val account = Auth0.getInstance(
		clientId = context.getString(R.string.com_auth0_client_id),
		domain = context.getString(R.string.com_auth0_domain),
	)
	val attemptLogin = {
		val callback = object : Callback<Credentials, AuthenticationException> {
			override fun onFailure(error: AuthenticationException) {
				coroutineScope.launch {
					snackbarHostState.showSnackbar(
						message = context.getString(R.string.an_error_occurred),
					)
				}
			}

			override fun onSuccess(result: Credentials) {
				isLoggedIn.value = true
			}
		}
		WebAuthProvider
			.login(account)
			.withScheme(context.getString(R.string.com_auth0_scheme))
			.start(context, callback)
	}
	val attemptLogout = {
		val callback = object : Callback<Void?, AuthenticationException> {
			override fun onFailure(error: AuthenticationException) {
				coroutineScope.launch {
					snackbarHostState.showSnackbar(
						message = context.getString(R.string.an_error_occurred),
					)
				}
			}

			override fun onSuccess(result: Void?) {
				isLoggedIn.value = false
			}
		}
		WebAuthProvider
			.logout(account)
			.withScheme(context.getString(R.string.com_auth0_scheme))
			.start(context, callback)
	}

	if (isLoggedIn.value) {
		MainScreen(onLogoutClicked = attemptLogout, modifier = modifier)
	} else {
		LoginScreen(onLoginClicked = attemptLogin, modifier = modifier)
	}
}
