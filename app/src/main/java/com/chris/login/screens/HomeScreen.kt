package com.chris.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chris.login.R
import com.chris.login.data.Carrito
import com.chris.login.data.Producto

@Composable
fun HomeScreen(
    productosDeDB: List<Producto>,
    onProductClick: (Producto) -> Unit,
    onCartClick: () -> Unit,
    onAddProduct: (Producto) -> Unit,
    onLogout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // ahora ya no usamos mock y si usamos la base de datos chida
    val filteredList = productosDeDB.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar comida") },
                modifier = Modifier.weight(1f)
            )

            BadgedBox(
                badge = {
                    if (Carrito.cantidad() > 0) {
                        Badge { Text(Carrito.cantidad().toString()) }
                    }
                },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                IconButton(onClick = onCartClick) {
                    Icon(painter = painterResource(
                        id = R.drawable.cart),
                        contentDescription = "Carrito",
                        modifier = Modifier.size(30.dp))
                }
            }
        }

        Text("Cerrar Sesión",
            modifier = Modifier.clickable { onLogout() }.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.error
        )

        LazyColumn(Modifier.weight(1f)) {

            items(filteredList) { producto ->
                Card(Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onProductClick(producto) }) {

                    Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(
                            id = producto.image),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp))

                        Column(Modifier
                            .weight(1f)
                            .padding(start = 12.dp))
                        {
                            Text(producto.name, style = MaterialTheme.typography.titleMedium)
                            Text("$${producto.price}")
                        }

                        IconButton(onClick = { onAddProduct(producto) }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        productosDeDB = emptyList(),
        onProductClick = {},
        onCartClick = {},
        onAddProduct = {},
        onLogout = {}
    )
}