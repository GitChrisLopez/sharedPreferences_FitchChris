package com.chris.login.Screens

import androidx.compose.runtime.mutableStateListOf
import com.chris.login.Utilities.Producto

object Carrito {
    val productos = mutableStateListOf<Producto>()

    fun agregar(producto: Producto) {
        productos.add(producto)
    }

    fun eliminar(producto: Producto) {
        productos.remove(producto)
    }

    fun total(): Double {
        return productos.sumOf { it.precio }
    }

    fun cantidad(): Int {
        return productos.size
    }

    fun limpiar() {
        productos.clear()
    }
}