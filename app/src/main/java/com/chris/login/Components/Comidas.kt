package com.chris.login.Components

import com.chris.login.R
import com.chris.login.Utilities.Producto

class Comidas {
    companion object {
        val listaComidas = listOf(
            Producto(
                1,
                "Pollo Asado",
                200.45,
                R.drawable.polloasado,
                "Pollo Asado entero con arroz."
            ),
            Producto(
                2,
                "Pizza Pepperoni",
                200.0,
                R.drawable.pizza,
                "Masa artesanal con salsa de tomate y pepperoni."
            ),
            Producto(
                3,
                "Tacos al Pastor",
                85.0,
                R.drawable.tacospastor,
                "5 tacos con piña, cebolla y cilantro."
            ),
            Producto(
                4,
                "Tacos de carne asada",
                120.0,
                R.drawable.tacosasada,
                "3 Tacos de carne asada con todo y una coca."
            ),
            Producto(
                5,
                "Sushi Roll",
                180.0,
                R.drawable.sushi,
                "Roll de salmón y aguacate con queso crema."
            )
        )

    }

    fun filtrarNombre(texto: String): List<Producto> {
        return if (texto.isBlank()) {
            listaComidas
        } else {
            listaComidas.filter {
                it.nombre.contains(texto, ignoreCase = true)
            }
        }
    }

    fun obtenerID(id: Int): Producto? {
        return listaComidas.find { it.id == id }
    }
}



