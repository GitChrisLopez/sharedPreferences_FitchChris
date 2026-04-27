package com.chris.login.data

import android.content.ContentValues
import com.chris.login.data.StoreContract.ProductsEntry
import com.chris.login.domain.Producto
import kotlin.text.insert


// Clase DAO en base a nuestro databasehelper
class ProductDAO(private val dbHelper: DatabaseHelper) {
    fun insertProduct(product: Producto): Long {
        val db = dbHelper.writableDatabase

        // Insertamos los elementos/contenido en la tabla
        val values = ContentValues().apply {
            put(ProductsEntry.COLUMN_NAME, product.name)
            put(ProductsEntry.COLUMN_PRICE, product.price)
            put(ProductsEntry.COLUMN_DESCRIPTION, product.description)
            put(ProductsEntry.COLUMN_IMAGE, product.image)
            put(ProductsEntry.COLUMN_TYPE, product.type)
        }

        // Los valores a mandar
        return db.insert(ProductsEntry.TABLE_NAME, null, values)
    }

    // Filtrar por tipo
    fun getProductsByType(type: String): List<Producto> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(
                ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_IMAGE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_TYPE
            ),
            "${ProductsEntry.COLUMN_TYPE} = ?",
            arrayOf(type),
            null, null, null
        )

        val products = mutableListOf<Producto>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = getFloat(getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val image = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                val description = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val prodType = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_TYPE))
                products.add(Producto(id, name, price, image, description, prodType))
            }
        }
        cursor.close()
        return products
    }

    fun getAllProducts(): List<Producto>{
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(
                ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE,
                ProductsEntry.COLUMN_TYPE
            ),
            null, null, null, null, null
        )

        // Convertir cursor en lista
        val products = mutableListOf<Producto>()

        with (cursor){
            while(moveToNext()){
                val id = getInt(getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = getFloat(getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val image = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                val description = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val prodType = getString(getColumnIndexOrThrow(ProductsEntry.COLUMN_TYPE))
                products.add(Producto(id, name, price, image, description, prodType))
            }
        }
        // Cada query que usamos devuelve un cursor
        cursor.close()
        return products
    }

    fun getProductById(productId: Int): Producto?{
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            ProductsEntry.TABLE_NAME,
            arrayOf(
                ProductsEntry.COLUMN_ID,
                ProductsEntry.COLUMN_NAME,
                ProductsEntry.COLUMN_PRICE,
                ProductsEntry.COLUMN_DESCRIPTION,
                ProductsEntry.COLUMN_IMAGE,
                ProductsEntry.COLUMN_TYPE
            ),
            "${ProductsEntry.COLUMN_ID} = ?",
            arrayOf(productId.toString()),
            null,
            null,
            null
        )

        // Iteramos el cursor para obtener su informacion
        val product: Producto? = cursor.use {
            if (it.moveToFirst()){
                val id = it.getInt(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_ID))
                val name = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_NAME))
                val price = it.getFloat(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_PRICE))
                val image = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_IMAGE))
                val description = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_DESCRIPTION))
                val prodType = it.getString(it.getColumnIndexOrThrow(ProductsEntry.COLUMN_TYPE))
                Producto(id, name, price, image, description, prodType)
            }else{
                null
            }
        }
        return product
    }
}

