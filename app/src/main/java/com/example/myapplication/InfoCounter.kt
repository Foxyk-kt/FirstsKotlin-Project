package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp

class InfoCounter : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        MyProfile()
                        Counter(modifier = Modifier.padding(vertical = 20.dp))
                    }
                }
            }
        }
    }

    @Composable
    fun MyProfile(modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            Text(
                text = "Foxyk",
            )
            Text("14")
            Text("Android developer")
        }
    }
    @Composable
    fun Counter (modifier: Modifier = Modifier) {
        var count by remember { mutableStateOf(0) }
        Row(modifier = modifier) {
            Button(
                onClick = {count++},
            ) {
                Text("[+]")
            }
            Button(
                onClick = { if(count > 0 ) count-- },
            ) {
                Text("[-]")
            }
            Text(
                text = count.toString(),
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)
            )
        }
    }
}