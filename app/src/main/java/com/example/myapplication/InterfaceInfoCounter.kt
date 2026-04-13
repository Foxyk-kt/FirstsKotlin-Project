package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment


class InterfaceInfoCounter : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                        .padding(innerPadding
                    )) {
                        MyProfile()
                        Counter()
                        ColorBox()
                    }
                }
            }
        }
    }

    @Composable
    fun MyProfile() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(10, 160, 90))
                    .padding(16.dp)
        ) {
            Text("Foxyk", color = Color.White)
            Text("14", color = Color.White)
            Text("Android developer", color = Color.White)
        }
    }
    @Composable
    fun Counter (modifier: Modifier = Modifier) {
        var count by remember { mutableStateOf(0) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxWidth()

        ) {
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
    @Composable
    fun ColorBox(modifier: Modifier = Modifier) {
        var colr by remember { mutableStateOf(true) }
        Box(
            modifier = modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))    // ← сначала clip
                .background(if(colr) Color.Red else Color.Green)
                .border(2.dp, Color.Black)
                .clickable { colr = !colr }
        ){}
    }
}