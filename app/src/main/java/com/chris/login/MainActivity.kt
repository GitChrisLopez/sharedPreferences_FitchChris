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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.chris.login.Components.Comidas
import com.chris.login.Utilities.Carrito
import com.chris.login.Utilities.Producto

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PreferenceManager(this)

        // Cargar carrito guardado al iniciar
        if (Carrito.productos.isEmpty()) {
            val savedItems = prefs.getCart(Comidas.listaComidas)
            Carrito.productos.addAll(savedItems)
        }

        setContent {
            // Lógica de navegación y persistencia
            var screenState by remember {
                mutableStateOf(if (prefs.isLoggedIn()) "HOME" else "LOGIN")
            }
            var selectedProduct by remember { mutableStateOf<Producto?>(null) }

            MaterialTheme {
                Surface {
                    when (screenState) {
                        "LOGIN" -> LoginScreen(onLoginClick = {
                            prefs.saveLoginStatus(true)
                            screenState = "HOME"
                        })
                        "HOME" -> HomeScreen(
                            onProductClick = {
                                selectedProduct = it
                                screenState = "DETAIL"
                            },
                            onCartClick = { screenState = "CART" },
                            onAddProduct = { producto ->
                                Carrito.agregar(producto)
                                prefs.saveCart(Carrito.productos)
                                screenState = "HOME"
                            },
                            onLogout = {
                                prefs.logout()
                                screenState = "LOGIN"
                            }
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
fun LoginScreen(onLoginClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Iniciar Sesión",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; errorMessage = "" },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMessage = "" },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if(errorMessage.isNotEmpty()) {
            Text(
                errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(onClick = {
            if (email == "admin@email.com" && password == "1234") onLoginClick()
            else errorMessage = "Credenciales incorrectas"
        }, modifier = Modifier.fillMaxWidth()) { Text("Entrar") }
    }
}

@Composable
fun HomeScreen(
    onProductClick: (Producto) -> Unit,
    onCartClick: () -> Unit,
    onAddProduct: (Producto) -> Unit,
    onLogout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredList = Comidas().filtrarNombre(searchQuery)

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
                            id = producto.imagen),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp))

                        Column(Modifier
                            .weight(1f)
                            .padding(start = 12.dp))
                        {
                            Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                            Text("$${producto.precio}")
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

@Composable
fun DetailScreen(producto: Producto, onBack: () -> Unit, onAdd: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Button(onClick = onBack) {
            Text("< Volver")
        }

        Spacer(Modifier.height(16.dp))

        Image(painter = painterResource(
            id = producto.imagen),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally))

        Text(producto.nombre, style = MaterialTheme.typography.headlineLarge)
        Text("$${producto.precio}", color = MaterialTheme.colorScheme.primary)
        Text(producto.descripcion, Modifier.padding(vertical = 16.dp))

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onAdd,
            modifier = Modifier.fillMaxWidth())
        {
            Text("Agregar al Carrito")
        }
    }
}

@Composable
fun CartScreen(onBack: () -> Unit, onDelete: (Producto) -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack)
        {
            Text("< Volver")
        }

        Text(
            "Tu Carrito",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp))

        LazyColumn(Modifier.weight(1f)) {
            items(Carrito.productos) { producto ->

                Row(Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text("${producto.nombre} - $${producto.precio}")
                    Text(
                        "Borrar",
                        modifier = Modifier.clickable { onDelete(producto) },
                        color = MaterialTheme.colorScheme.error)
                }
            }
        }

        HorizontalDivider()

        Text("Productos: ${Carrito.cantidad()}")
        Text("Total: $${Carrito.total()}", style = MaterialTheme.typography.headlineSmall)

        Button(
            onClick = onBack,
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp))
        {
            Text("Seguir comprando")
        }
    }
}