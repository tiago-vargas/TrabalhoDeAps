package io.github.tiago_vargas.trabalhodeaps

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import io.github.tiago_vargas.trabalhodeaps.ui.theme.TrabalhoDeApsTheme

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
	TrabalhoDeApsTheme {
		Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
			Content(
				context = context,
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
			)
		}
	}
}

@Composable
fun Content(context: Context, modifier: Modifier = Modifier) {
	val isLoggedIn = remember { mutableStateOf(false) }

	val attemptLogin = {
		val account = Auth0.getInstance(
			clientId = context.getString(R.string.com_auth0_client_id),
			domain = context.getString(R.string.com_auth0_domain),
		)
		val callback = object : Callback<Credentials, AuthenticationException> {
			override fun onFailure(error: AuthenticationException) {
				isLoggedIn.value = false
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

	if (isLoggedIn.value) {
		MainScreen(modifier = modifier)
	} else {
		LoginScreen(onLoginClicked = attemptLogin, modifier = modifier)
	}
}
