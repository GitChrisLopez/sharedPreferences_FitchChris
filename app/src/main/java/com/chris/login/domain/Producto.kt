package com.chris.login.domain

data class Producto(
    val id: Int= 0,
    val name: String,
    val price: Float,
    val image: String? = null,
    val description: String? = null,
    val type: String
)