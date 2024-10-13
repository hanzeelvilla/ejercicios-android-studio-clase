package com.hanzeel.reto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hanzeel.reto.ui.theme.RetoTheme

data class Fruit(val name: String, val description: String, val imageUrl: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetoTheme {
                FruitApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitApp() {
    var fruits by remember { mutableStateOf(listOf<Fruit>()) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Fruit Collection") })
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                // Input fields for adding a new fruit
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Button to add a new fruit
                Button(
                    onClick = {
                        if (name.isNotEmpty() && description.isNotEmpty() && imageUrl.isNotEmpty()) {
                            fruits = fruits + Fruit(name, description, imageUrl)
                            name = ""
                            description = ""
                            imageUrl = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add Fruit")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display the list of fruits
                FruitList(fruits)
            }
        }
    )
}

@Composable
fun FruitList(fruits: List<Fruit>) {
    LazyColumn {
        items(fruits) { fruit ->
            FruitItem(fruit)
        }
    }
}

@Composable
fun FruitItem(fruit: Fruit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = fruit.imageUrl,
            contentDescription = fruit.name,
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Crop
        )
        Column {
            Text(text = fruit.name, style = MaterialTheme.typography.titleMedium)
            Text(text = fruit.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetoTheme {
        FruitApp()
    }
}
