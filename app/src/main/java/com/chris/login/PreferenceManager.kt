package com.chris.login

import android.content.Context
import android.content.SharedPreferences
import com.chris.login.Utilities.Producto

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }

    // Metodos para el carrito (Cart), la parte 2 de la actividad

    fun saveCart(productos: List<Producto>) {
        val texto = productos.joinToString("|") {
            "${it.id},${it.nombre},${it.precio},${it.imagen},${it.descripcion}"
        }
        sharedPreferences.edit().putString("cart", texto).apply()
    }

    fun getCart(): List<Producto> {
        val texto = sharedPreferences.getString("cart", null) ?: return emptyList()
        if (texto.isBlank()) return emptyList()
        val lista = mutableListOf<Producto>()
        val productosString = texto.split("|")

        for (productoStr in productosString) {
            val partes = productoStr.split(",")

            if (partes.size == 5) {
                try {
                    val producto = Producto(
                        id = partes[0].toInt(),
                        nombre = partes[1],
                        precio = partes[2].toDouble(),
                        imagen = partes[3],
                        descripcion = partes[4]
                    )
                    lista.add(producto)
                } catch (e: Exception) {
                }
            }
        }

        return lista
    }
}