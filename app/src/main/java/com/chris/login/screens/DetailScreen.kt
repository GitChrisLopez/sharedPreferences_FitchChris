package com.chris.login.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chris.login.domain.Producto
import com.chris.login.R


@Composable
fun DetailScreen(producto: Producto, onBack: () -> Unit, onAdd: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Button(onClick = onBack) {
            Text("< Volver")
        }

        Spacer(Modifier.height(16.dp))

        val imageRes = producto.image?.toIntOrNull() ?: R.drawable.muffin

        Image(painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally))

        Text(producto.name, style = MaterialTheme.typography.headlineLarge)
        Text("$${producto.price}", color = MaterialTheme.colorScheme.primary)
        producto.description?.let { Text(it, Modifier.padding(vertical = 16.dp)) }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onAdd,
            modifier = Modifier.fillMaxWidth())
        {
            Text("Agregar al Carrito")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val productoMuestra = Producto(
        id = 1,
        name = "Pizza",
        price = 120f,
        description = "una pizza",
        image = R.drawable.pizza.toString(),
        type = "Delicious"
    )

    DetailScreen(
        producto = productoMuestra,
        onBack = {},
        onAdd = {}
    )
}