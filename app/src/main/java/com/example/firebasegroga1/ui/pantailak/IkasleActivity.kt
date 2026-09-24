package com.example.firebasegroga1.ui.pantailak

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.firebasegroga1.data.User
import com.example.firebasegroga1.ui.theme.FireBaseGroga1Theme
import com.google.firebase.firestore.FirebaseFirestore

class IkasleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val izena = intent.getStringExtra("user") ?: ""
            Crud(
                bueltan = { finish() }
            )
        }
    }
}


@Composable
fun Crud(bueltan: () -> Unit) {
    var id by remember { mutableStateOf("")}
    var izena by remember { mutableStateOf("")}
    var abizena by remember { mutableStateOf("")}

    var datos by remember { mutableStateOf("")}
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        item {
            Text(
                text = "IKASLEAK"
            )

            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text(text = "id") },
                singleLine = true
            )

            OutlinedTextField(
                value = izena,
                onValueChange = { izena = it },
                label = { Text(text = "izena") },
                singleLine = true
            )

            OutlinedTextField(
                value = abizena,
                onValueChange = { abizena = it },
                label = { Text(text = "abizena") },
                singleLine = true
            )

            Text(
                text = datos
            )

            Button(
                onClick = {
                    Erakutsi{ resultado ->
                    datos = resultado}
                }
            ) { Text("Erakutsi") }
            Button(
                onClick = {
                    Gorde(id,izena,abizena){ resultado ->
                        datos = resultado}
                }
            ) { Text("Gorde") }
            Button(
                onClick = {
                    bueltan()
                }
            ) { Text("Itzi") }

        }
    }
}

fun Gorde(
    id: String,
    izena: String,
    abizena: String,
    resultado: (String) -> Unit) {

    val db = FirebaseFirestore.getInstance()
    val ikaslea = User(
        id = id,
        izena = izena,
        abizena = abizena
    )
    db.collection("ikasleak")
        .add(ikaslea)
        .addOnSuccessListener { documentReference ->
                resultado("Docuemnto añadadios ${documentReference.id}\n")
              }
        .addOnFailureListener { e ->
            resultado( "Error: ${e.message}")
        }
}
fun Erakutsi(resultado: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
db.collection("ikasleak").get()
    .addOnSuccessListener { ikasleak ->
        var datuak = ""
        for (ikaslea in ikasleak){
            datuak += "${ikaslea.id} : ${ikaslea.data}\n"

        }
        if(datuak.isEmpty()){ resultado("Ez dago docuemnturik")}
        else{ resultado(datuak)}

        }
        .addOnFailureListener { e ->
            resultado ( "Error: ${e.message}")
        }
        }

