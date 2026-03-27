package com.chris.login.Components

import com.chris.login.Utilities.Producto

class Comidas {
    companion object {
        val listaComidas = listOf(
            Producto(
                1,
                "Pollo Asado",
                200.45,
                "PolloAsado.png",
                "Pollo Asado entero con arroz."
            ),
            Producto(
                2,
                "Pizza Pepperoni",
                200.0,
                "pizza.png",
                "Masa artesanal con salsa de tomate y pepperoni."
            ),
            Producto(
                3,
                "Tacos al Pastor",
                85.0,
                "tacosPastor.png",
                "5 tacos con piña, cebolla y cilantro."
            ),
            Producto(
                4,
                "Tacos de carne asada",
                120.0,
                "tacosAsada.png",
                "3 Tacos de carne asada con todo y una coca."
            ),
            Producto(
                5,
                "Sushi Roll",
                180.0,
                "sushi.png",
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



