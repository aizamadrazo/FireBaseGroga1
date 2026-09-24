package com.example.firebasegroga1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firebasegroga1.ui.pantailak.BigarrenPantaila
import com.example.firebasegroga1.ui.theme.FireBaseGroga1Theme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FireBaseGroga1Theme {
                    Login()
                }
            }
        }
    }


@Composable
fun Login() {
    var pass by remember { mutableStateOf(value = "") }
    var email by remember { mutableStateOf(value = "") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top= 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            singleLine = true,
            label= {Text("Email")}
        )
        OutlinedTextField(
            value = pass,
            onValueChange = {pass = it},
            singleLine = true,
            label= {Text("Pass")}
        )
        val auth = FirebaseAuth.getInstance()
        val context = LocalContext.current
        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){

                            Toast.makeText(context, "Holiiiiii", Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, BigarrenPantaila::class.java)
//                            //coger el parametro de mail
                            intent.putExtra("user", email)
                            context.startActivity(intent)
                        }else{
                            makeText(
                                context,
                                "Error: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Saioa hasi")
        }
    }

}