package com.calculator.Calculator

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
import com.calculator.Calculator.ui.theme.MyApplicationTheme
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import com.calculator.Calculator.ui.theme.MyTextSpecialDark


//--------------------------------Procents--------------------------------
class Calculator : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ){
                        Colculator()
                    }

                }
            }
        }
    }
//||--------------------------------Display--------------------------------||
    @Composable
    fun Colculator(modifier: Modifier = Modifier) {
        var operators by remember { mutableStateOf(listOf<String>()) }
        var numbers by remember { mutableStateOf(listOf<Double>()) }
        var curNumber by remember { mutableStateOf("0") }
        var textOut by remember { mutableStateOf("") }
        var Debug by remember { mutableStateOf(false) }
        val debugList = remember { mutableStateListOf("") }

//--------------------------------Logic--------------------------------
        fun handleClick(value: String) {
            try {
                when {
                    value in listOf("+", "-", "*", "/") -> {
                        numbers = numbers + curNumber.toDouble()
                        operators = operators + value
                        if (curNumber == "0") textOut += value else textOut += curNumber + value
                        curNumber = ""
                    }
//--------------------------------" ＝ "--------------------------------
                    value == "=" -> {
                        numbers = numbers + curNumber.toDouble()
                        var result = numbers[0]
                        operators.forEachIndexed { num, operator ->
//--------------------------------Operators--------------------------------
                            result = when(operator) {
                                "+" -> result + numbers[num + 1]
                                "-" -> result - numbers[num + 1]
                                "*" -> result * numbers[num + 1]
                                "/" -> result / numbers[num + 1]
                                else -> result
                            }
                        }
//-------------------------------------------------------------------------
                        curNumber = if (result % 1.0 == 0.0) result.toLong().toString() else result.toString()
                        numbers = listOf()
                        operators = listOf()
                        textOut = ""
                    }
//--------------------------------" AC "--------------------------------
                    value == "AC" -> {
                        curNumber = "0"
                        numbers = listOf()
                        operators = listOf()
                        textOut = ""
                    }
//--------------------------------" < "--------------------------------
                    value == "<" -> {
                        if (curNumber.length >1 ) curNumber = curNumber.dropLast(1) else curNumber = "0"
                    }
//--------------------------------" +/- "--------------------------------
                    value == "+/-" -> {
                        curNumber = if (curNumber.startsWith("-")) curNumber.drop(1) else "-$curNumber"
                    }
//--------------------------------" % "--------------------------------
                    value == "%" -> {
                        if (numbers.isEmpty()) {
                            curNumber = (curNumber.toDouble() / 100).toString()
                        }else {
                            val preNum = numbers.last()
                            curNumber = (preNum * curNumber.toDouble() / 100).toString()
                        }
                    }
                    else -> if (curNumber == "0") {
                        curNumber = value
                    } else {
                        curNumber += value
                    }
                }
            }catch (e: Exception) {
                debugList.add(e.message.toString())
            }
        }
//--------------------------------Design--------------------------------
        Column(
            horizontalAlignment = Alignment.End,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {Debug = !Debug},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {}

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                if(Debug) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(8.dp)
                    ) {
//--------------------------------DebugPrintln--------------------------------
                        Text(text = "textOut: $textOut", color = Color.White)
                        Text(text = "curNumber: $curNumber", color = Color.White)
                        Text(text = "numbers: $numbers", color = Color.Green)
                        Text(text = "operators: $operators", color = Color.Yellow)
                        Text(text = "DebugActivated", color = Color.Red)
                        debugList.forEach { item ->
                            Text(text = "erors: $item", color = Color.Red)
                        }
                    }
                }
            }
//--------------------------------TextOutput--------------------------------
            Text(
                text = textOut + curNumber,
                fontWeight = FontWeight.Bold,
                fontSize = 42.sp,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 156.dp, end = 20.dp)


            )
        }
//--------------------------------Buttons--------------------------------
        val buttons = listOf(
            listOf("AC", "<", "%", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("+/-", "0", ".", "=")

        )
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            buttons.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, end = 6.dp)
                ) {
                    row.forEach { text ->
                        if(text in listOf("AC", "<", "%", "/", "*", "-", "+", "=", "+/-")) {
                            BtnO(text) {handleClick(it)}
                        }else
                            Btn(text) { handleClick(it) }
                    }
                }
            }
        }

    }
    //--------------------------------ButtonDef--------------------------------
    @Composable
    fun Btn(text: String, onClick: (String) -> Unit) {
        Button(
            onClick = {onClick(text)},
            modifier = Modifier
                .size(80.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    //--------------------------------ButtonOrange--------------------------------
    @Composable
    fun BtnO(text: String, onClick: (String) -> Unit) {
        Button(
            onClick = {onClick(text)},
            modifier = Modifier
                .size(80.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = MyTextSpecialDark
            )
        }
    }
}