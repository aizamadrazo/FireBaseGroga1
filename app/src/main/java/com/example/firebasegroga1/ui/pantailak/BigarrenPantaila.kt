package com.example.firebasegroga1.ui.pantailak

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.firebasegroga1.ui.theme.FireBaseGroga1Theme

class BigarrenPantaila : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val izena = intent.getStringExtra("user")?:""
            Holi(
                izena,
                bueltan={finish()}
            )
        }
    }
    }

@Composable
fun Holi(izena: String, bueltan:()-> Unit){
val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top= 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(text= "ongietorri $izena" )
        Button(
            onClick = {
                val intent = Intent(context, IkasleActivity::class.java)
                context.startActivity(intent)

            }
        ) {Text("ikasleak") }
        Button(
            onClick = {
                bueltan()
            }
        ) {Text("Itzi") }
    }
}