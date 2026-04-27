package com.chris.login.Components

import com.chris.login.R
import com.chris.login.domain.Producto

class Comidas {
    companion object {
        val listaComidas = listOf(
            Producto(
                1,
                "Pollo Asado",
                200f,
                R.drawable.polloasado.toString(),
                "Pollo Asado entero con arroz.",
                type = "Salties"
            ),
            Producto(
                2,
                "Pizza Pepperoni",
                200f,
                R.drawable.pizza.toString(),
                "Masa artesanal con salsa de tomate y pepperoni.",
                type = "Tasty"
            ),
            Producto(
                3,
                "Tacos al Pastor",
                85f,
                R.drawable.tacospastor.toString(),
                "5 tacos con piña, cebolla y cilantro.",
                type = "Spicy"
            ),
            Producto(
                4,
                "Tacos de carne asada",
                120f,
                R.drawable.tacosasada.toString(),
                "3 Tacos de carne asada con todo y una coca.",
                type = "Meat"
            ),
            Producto(
                5,
                "Sushi Roll",
                180f,
                R.drawable.sushi.toString(),
                "Roll de salmón y aguacate con queso crema.",
                type = "Oriental"
            )
        )

    }

    fun filtrarNombre(texto: String): List<Producto> {
        return if (texto.isBlank()) {
            listaComidas
        } else {
            listaComidas.filter {
                it.name.contains(texto, ignoreCase = true)
            }
        }
    }

    fun obtenerID(id: Int): Producto? {
        return listaComidas.find { it.id == id }
    }
}



