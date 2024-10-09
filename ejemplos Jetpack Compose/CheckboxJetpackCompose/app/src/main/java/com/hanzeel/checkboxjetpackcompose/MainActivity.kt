package com.hanzeel.checkboxjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanzeel.checkboxjetpackcompose.ui.theme.CheckboxJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheckboxJetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Número 1") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Número 2") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        val items = listOf("Suma", "Resta", "Multiplicación")
        val checkboxStates = remember { mutableStateMapOf<String, Boolean>().withDefault { false } }

        Column {
            items.forEach { item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkboxStates.getValue(item),
                        onCheckedChange = { checkboxStates[item] = it }
                    )
                    Text(
                        text = item,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Button(
            onClick = {
                val n1: Float? = num1.toFloatOrNull()
                val n2: Float? = num2.toFloatOrNull()
                if (n1 != null && n2 != null) {
                    val res = StringBuilder()
                    if (checkboxStates["Suma"] == true) {
                        res.append("$n1 + $n2 = ${n1 + n2}\n")
                    }
                    if (checkboxStates["Resta"] == true) {
                        res.append("$n1 - $n2 = ${n1 - n2}\n")
                    }
                    if (checkboxStates["Multiplicación"] == true) {
                        res.append("$n1 * $n2 = ${n1 * n2}\n")
                    }
                    result = if (res.isNotEmpty()) "Resultado:\n$res" else "Por favor, seleccione al menos una operación."
                } else {
                    result = "Por favor, ingrese números válidos."
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Calcular")
        }

        if (result.isNotEmpty()) {
            Text(text = result, modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CheckboxJetpackComposeTheme {
        Calculator()
    }
}
