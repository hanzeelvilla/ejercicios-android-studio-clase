package com.hanzeel.sumade2numeros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanzeel.sumade2numeros.ui.theme.SumaDe2NumerosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SumaDe2NumerosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Suma(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Suma(modifier: Modifier = Modifier) {
    // Crear estados para almacenar los valores ingresados
    val num1 = remember { mutableStateOf("") }
    val num2 = remember { mutableStateOf("") }
    val resultado = remember { mutableStateOf("0") }

    // Crear una función para realizar la suma
    fun sumar() {
        val num1Int = num1.value.toIntOrNull() ?: 0
        val num2Int = num2.value.toIntOrNull() ?: 0
        val c = num1Int + num2Int
        resultado.value = "$num1Int + $num2Int = $c"
    }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = num1.value,
            onValueChange = { num1.value = it },
            label = { Text("Número 1") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Cambiado a Number
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = num2.value,
            onValueChange = { num2.value = it },
            label = { Text("Número 2") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Cambiado a Number
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { sumar() }) {
            Text("Sumar")
        }
        Text("Resultado: ${resultado.value}")
    }
}

@Preview(showBackground = true)
@Composable
fun SumaPreview() {
    SumaDe2NumerosTheme {
        Suma()
    }
}