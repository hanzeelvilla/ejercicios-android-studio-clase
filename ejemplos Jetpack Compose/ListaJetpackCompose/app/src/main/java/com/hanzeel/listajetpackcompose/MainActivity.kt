package com.hanzeel.listajetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hanzeel.listajetpackcompose.ui.theme.ListaJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListaJetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskList(modifier = Modifier.padding(innerPadding)) // Cambiado a TaskList
                }
            }
        }
    }
}

@Composable
fun TaskList(modifier: Modifier = Modifier) {
    var task by remember { mutableStateOf("") }
    val tasks = remember { mutableStateListOf<String>() }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = task,
            onValueChange = { task = it },
            label = { Text("Tarea") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (task.isNotEmpty()) {
                    tasks.add(task)
                    task = ""
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Agregar")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaciador para separación

        LazyColumn {
            items(tasks) { task ->
                Card(modifier = Modifier.padding(5.dp)) {
                    Column(modifier = Modifier.padding(5.dp)) {
                        Row(modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                        ) {
                            Text(text = task)
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TaskListPreview() {
    ListaJetpackComposeTheme {
        TaskList() // Llama a TaskList para la vista previa
    }
}
