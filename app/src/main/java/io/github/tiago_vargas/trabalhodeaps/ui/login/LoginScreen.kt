package io.github.tiago_vargas.trabalhodeaps.ui.login

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import io.github.tiago_vargas.trabalhodeaps.R
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
	context: Context,
	snackbarHostState: SnackbarHostState,
	onLoginSuccess: () -> Unit,
	modifier: Modifier = Modifier,
) {
	val coroutineScope = rememberCoroutineScope()
	val account = getAuth0Instance(context)
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
				onLoginSuccess()
			}
		}
		WebAuthProvider
			.login(account)
			.withScheme(context.getString(R.string.com_auth0_scheme))
			.start(context, callback)
	}

	LoginScreenContent(onLoginClicked = attemptLogin, modifier = modifier)
}

private fun getAuth0Instance(context: Context): Auth0 = Auth0.getInstance(
	clientId = context.getString(R.string.com_auth0_client_id),
	domain = context.getString(R.string.com_auth0_domain),
)

@Composable
fun LoginScreenContent(onLoginClicked: () -> Unit, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Button(onClick = onLoginClicked) {
			Text("Login")
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenContentPreview() {
	TrabalhoDeApsTheme {
		Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
			LoginScreenContent(
				onLoginClicked = {},
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
			)
		}
	}
}
