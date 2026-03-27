package com.chris.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chris.login.Components.Comidas
import com.chris.login.Utilities.Carrito
import com.chris.login.Utilities.Producto

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PreferenceManager(this)
        val comidasProvider = Comidas()

        if (Carrito.productos.isEmpty()) {
            val savedItems = prefs.getCart(Comidas.listaComidas)
            Carrito.productos.addAll(savedItems)
        }

        setContent {
            var screenState by remember { mutableStateOf("HOME") }
            var selectedProduct by remember { mutableStateOf<Producto?>(null) }

            MaterialTheme {
                Surface {
                    when (screenState) {
                        "HOME" -> HomeScreen(
                            onProductClick = {
                                selectedProduct = it
                                screenState = "DETAIL"
                            },
                            onCartClick = { screenState = "CART" },
                            onAdd = { prefs.saveCart(Carrito.productos) }
                        )
                        "DETAIL" -> selectedProduct?.let { prod ->
                            DetailScreen(
                                producto = prod,
                                onBack = { screenState = "HOME" },
                                onAdd = {
                                    Carrito.agregar(prod)
                                    prefs.saveCart(Carrito.productos)
                                }
                            )
                        }
                        "CART" -> CartScreen(
                            onBack = { screenState = "HOME" },
                            onDelete = {
                                Carrito.eliminar(it)
                                prefs.saveCart(Carrito.productos)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onProductClick: (Producto) -> Unit, onCartClick: () -> Unit, onAdd: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredList = Comidas().filtrarNombre(searchQuery)

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar...") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onCartClick) {
                BadgedBox(badge = { if(Carrito.cantidad() > 0) Badge { Text("${Carrito.cantidad()}") } }) {
                    Icon(Icons.Default.ShoppingCart, "Carrito")
                }
            }
        }

    }
}

@Composable
fun DetailScreen(producto: Producto, onBack: () -> Unit, onAdd: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        //aaaaaaaaaaaaaaaaaaaaaaaa detalles
    }
}

@Composable
fun CartScreen(onBack: () -> Unit, onDelete: (Producto) -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tu Carrito", style = MaterialTheme.typography.headlineMedium)
        //LazyColumn(Modifier.weight(1f)) {
            
    }
}