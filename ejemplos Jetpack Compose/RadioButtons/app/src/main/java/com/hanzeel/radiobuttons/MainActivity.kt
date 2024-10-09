package com.hanzeel.radiobuttons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanzeel.radiobuttons.ui.theme.RadioButtonsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RadioButtonsTheme {
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

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Número 2") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        val operations = listOf("Suma", "Resta", "Multiplicación")
        var selectedOperation by remember { mutableStateOf(operations[0]) }

        Column {
            operations.forEach { operation ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (operation == selectedOperation),
                            onClick = { selectedOperation = operation }
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (operation == selectedOperation),
                        onClick = { selectedOperation = operation }
                    )
                    Text(
                        text = operation,
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
                    result = when (selectedOperation) {
                        "Suma" -> (n1 + n2).toString()
                        "Resta" -> (n1 - n2).toString()
                        "Multiplicación" -> (n1 * n2).toString()
                        else -> result // Este caso no debería ocurrir
                    }
                } else {
                    result = "Por favor, ingrese números válidos."
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Calcular")
        }

        if (result.isNotEmpty()) {
            Text(text = "Resultado: $result")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    RadioButtonsTheme {
        Calculator()
    }
}