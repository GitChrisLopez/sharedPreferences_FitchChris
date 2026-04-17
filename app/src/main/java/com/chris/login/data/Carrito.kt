package com.chris.login.data

import androidx.compose.runtime.mutableStateListOf

object Carrito {
    val productos = mutableStateListOf<Producto>()

    fun agregar(producto: Producto) {
        productos.add(producto)
    }

    fun eliminar(producto: Producto) {
        productos.remove(producto)
    }

    fun total(): Float {
        return productos.sumOf { it.price.toDouble() }.toFloat()
    }

    fun cantidad(): Int {
        return productos.size
    }

    fun limpiar() {
        productos.clear()
    }
}