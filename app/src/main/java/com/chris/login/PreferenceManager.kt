package com.chris.login

import android.content.Context
import android.content.SharedPreferences
import com.chris.login.Utilities.Producto

class PreferenceManager(context: Context) {
    private val prefs = context.getSharedPreferences("TiendaPrefs", Context.MODE_PRIVATE)
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
    fun saveCart(productos: List<com.chris.login.Utilities.Producto>) {
        val ids = productos.joinToString(",") { it.id.toString() }
        prefs.edit().putString("cart_ids", ids).apply()
    }

    fun getCart(allProducts: List<com.chris.login.Utilities.Producto>): List<com.chris.login.Utilities.Producto> {
        val idsString = prefs.getString("cart_ids", "") ?: ""
        if (idsString.isEmpty()) return emptyList()

        val ids = idsString.split(",").mapNotNull { it.toIntOrNull() }
        return ids.mapNotNull { id -> allProducts.find { it.id == id } }
    }
}