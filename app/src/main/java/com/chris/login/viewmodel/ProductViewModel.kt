package com.chris.login.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chris.login.data.ProductDAO
import kotlinx.coroutines.launch
import com.chris.login.domain.Producto

class ProductViewModel (private val dao: ProductDAO, private val context: Context ): ViewModel()
{
    var productsListState by mutableStateOf( value = listOf<Producto>())

    init {
        viewModelScope.launch {
            getAllProducts()
        }
    }

    fun saveProduct(product: Producto){
        val newProduct = dao.insertProduct(product)
        if (newProduct != -1L){
            Toast.makeText(
                context,
                "Producto guardado",
                Toast.LENGTH_SHORT).show()
            getAllProducts()
        }else{
            Toast.makeText(
                context,
                "Hubo un error al guardar",
                Toast.LENGTH_SHORT).show()
        }
    }

    fun loadProductsByType(type: String){
        productsListState = dao.getProductsByType(type)
    }

    fun getAllProducts(){
        productsListState = dao.getAllProducts()
    }

}