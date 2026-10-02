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
    var nana by remember { mutableStateOf("")}
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
                value = nana,
                onValueChange = { nana = it },
                label = { Text(text = "nana") },
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
                    Gorde(nana,izena,abizena,context)
                }
            ) { Text("Gorde") }
            Button(
                onClick = {
                    Aldatu(nana, izena, abizena, context)
                }
            ) { Text("Aldatu") }

            Button(
                onClick = {
                    Ezabatu(nana,context)
                }
            ) { Text("Ezabatu") }

            Button(
                onClick = {
                    bueltan()
                }
            ) { Text("Itzi") }

        }
    }
}

fun Gorde(
    nana: String,
    izena: String,
    abizena: String,
    context: android.content.Context) {

    val db = FirebaseFirestore.getInstance()
    val ikaslea = User(
        nana = nana,
        izena = izena,
        abizena = abizena
    )
    db.collection("ikasleak")
        .add(ikaslea)
        .addOnSuccessListener { Toast.makeText(
            context,
            "Ikaslea gordeta",
            Toast.LENGTH_SHORT
        ).show()}
        .addOnFailureListener { e ->
            Toast.makeText(
                context,
                "Errorea: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()        }
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
fun Ezabatu(nana: String, context: android.content.Context) {
    val db = FirebaseFirestore.getInstance()

    db.collection("ikasleak")
        .whereEqualTo("nana", nana)
        .get()
        .addOnSuccessListener { ikasleak ->

            if (ikasleak.isEmpty) {
                Toast.makeText(
                    context,
                    "Ez da ikaslea aurkitu",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                for (ikaslea in ikasleak) {
                    db.collection("ikasleak")
                        .document(ikaslea.id)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "Ikaslea ezabatuta",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                context,
                                "Errorea: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
        .addOnFailureListener { e ->
            Toast.makeText(
                context,
                "Errorea bilatzean: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}
fun Aldatu(
    nana: String,
    izena: String,
    abizena: String,
    context: android.content.Context
) {
    val db = FirebaseFirestore.getInstance()

    db.collection("ikasleak")
        .whereEqualTo("nana", nana)
        .get()
        .addOnSuccessListener { ikasleak ->

            if (ikasleak.isEmpty) {
                Toast.makeText(
                    context,
                    "Ez da ikaslea aurkitu",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                for (ikaslea in ikasleak) {

                    db.collection("ikasleak")
                        .document(ikaslea.id)
                        .update(
                            "izena", izena,
                            "abizena", abizena
                        )
                        .addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "Ikaslea eguneratuta",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                context,
                                "Errorea: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
        .addOnFailureListener { e ->
            Toast.makeText(
                context,
                "Errorea bilatzean: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}