package com.chris.login.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chris.login.data.Carrito
import com.chris.login.domain.Producto


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
                    Text("${producto.name} - $${producto.price}")
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

@Preview(showBackground = true)
@Composable
fun CartScreenPreview(){
    CartScreen(
        onBack = {},
        onDelete = {}
    )
}