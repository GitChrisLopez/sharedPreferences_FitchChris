package com.chris.login

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chris.login.data.DatabaseHelper
import com.chris.login.data.ProductDAO
import com.chris.login.screens.*
import com.chris.login.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // when preguntan por el contexto: aca esta el contexto de la app papu
    val context = androidx.compose.ui.platform.LocalContext.current

    // base de datos instanciada
    val dbHelper = remember { DatabaseHelper(context) }
    val dao = remember { ProductDAO(dbHelper) }
    val viewModel = remember { ProductViewModel(dao, context) }

    // nav
    NavHost(navController = navController, startDestination = "welcome") {

        // pantalla bienvenida
        composable("welcome") {
            WelcomeScreen(
                onNavigateToMenu = { navController.navigate("menu") }
            )
        }

        // menu de categorias
        composable("menu") {
            MenuScreen(
                onCategorySelected = { category ->
                    // pa que sirva lo de las categorias
                    navController.navigate("products/$category")
                },
                onNavigateToAddProduct = {
                    navController.navigate("addProduct")
                }
            )
        }

        // lista de productos
        composable(
            route = "products/{categoryType}",
            arguments = listOf(navArgument("categoryType") { type = NavType.StringType })
        ) { backStackEntry ->

            val categoryType = backStackEntry.arguments?.getString("categoryType") ?: "Hot drinks"

            // cargamos los datos
            LaunchedEffect(categoryType) {
                viewModel.loadProductsByType(categoryType)
            }

            // y ya sale el estado de los productos asi bien y se muestran en pantalla
            ProductsScreen(
                categoryType = categoryType,
                innerPadding = PaddingValues(0.dp),
                products = viewModel.productsListState
            )
        }

        // pantalla de agregar productos
        composable("addProduct") {
            AddProductScreen(
                innerPadding = PaddingValues(0.dp),
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
