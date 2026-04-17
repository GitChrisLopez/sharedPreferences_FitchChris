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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chris.login.Components.Comidas
import com.chris.login.Utilities.Carrito
import com.chris.login.Utilities.Producto
import com.chris.login.data.DatabaseHelper
import com.chris.login.screens.DetailScreen
import com.chris.login.screens.HomeScreen
import com.chris.login.screens.LoginScreen
import com.chris.login.screens.CartScreen
import com.chris.login.data.ProductoDAO

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PreferenceManager(this)

        // Conectamos la base de datos y el dao al main
        val dbHelper = DatabaseHelper(this)
        val productoDAO = ProductoDAO(dbHelper)

        var productosGuardadosEnDB = productoDAO.getAllProducts()
        if (productosGuardadosEnDB.isEmpty()) {
            Comidas.listaComidas.forEach { producto ->
                productoDAO.insertProduct(producto)
            }
            // consultamos la bd
            productosGuardadosEnDB = productoDAO.getAllProducts()
        }

        // cargamos los productos de los carritos
        if (Carrito.productos.isEmpty()) {
            val savedItems = prefs.getCart(productosGuardadosEnDB)
            Carrito.productos.addAll(savedItems)
        }

        setContent {
            // Aca esta la logica de navegacion y persistencia
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
                            productosDeDB = productosGuardadosEnDB,
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

