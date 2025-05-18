
package f1tracker.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.f1tracker.data.db.AuthDao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(navController: NavController) {
    var isLogin by remember { mutableStateOf(true) }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authDao = remember { AuthDao(context) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (isLogin) "Login" else "Register") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (!isLogin) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val success = if (isLogin) {
                        authDao.login(username, password)
                    } else {
                        authDao.register(email, username, password)
                    }

                    if (success && isLogin) {
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    } else if (!isLogin) {
                        isLogin = true;
                    } else {
                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isLogin) "Login" else "Register")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { isLogin = !isLogin }) {
                Text(if (isLogin) "Don't have an account? Register" else "Already have an account? Login")
            }
        }
    }
}
