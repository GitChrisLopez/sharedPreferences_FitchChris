package com.chris.login.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.chris.login.data.StoreContract.ProductsEntry
import com.chris.login.R

class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME="cheezery.db"
        private const val DATABASE_VERSION = 4 // esto se debe cambiar cada q hago algo aca
    }

    // Base de datos
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON") // Permitimos las llaves foraneas en la BD

        // Creamos la tabla
        db.execSQL(
            """CREATE TABLE ${ProductsEntry.TABLE_NAME} (
                    ${ProductsEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${ProductsEntry.COLUMN_NAME} TEXT NOT NULL,
            ${ProductsEntry.COLUMN_IMAGE} TEXT,
            ${ProductsEntry.COLUMN_PRICE} REAL NOT NULL,
            ${ProductsEntry.COLUMN_DESCRIPTION} TEXT,
            ${ProductsEntry.COLUMN_TYPE} TEXT NOT NULL
            )
            """.trimIndent()
        )

        // insertamo productos porque me da tock no tener nada para confirmar la funcionalidad de esto
        insertProducts(db)
    }

    private fun insertProducts(db: SQLiteDatabase) {
        val insertSQL = """
        INSERT INTO ${ProductsEntry.TABLE_NAME} (
            ${ProductsEntry.COLUMN_NAME},
            ${ProductsEntry.COLUMN_IMAGE},
            ${ProductsEntry.COLUMN_PRICE},
            ${ProductsEntry.COLUMN_DESCRIPTION},
            ${ProductsEntry.COLUMN_TYPE}
        )
        VALUES 
        ('Latte Vainilla', '${R.drawable.latte}', 65.0, 'Latte caliente con un toque de vainilla.', 'Hot drinks'),
        ('Café Americano', '${R.drawable.americano}', 45.0, 'Clásico café americano recién hecho.', 'Hot drinks'),
        
        ('Frappé de Moka', '${R.drawable.caramel_frap}', 85.0, 'Bebida fría mezclada con chocolate y café.', 'Cold drinks'),
        ('Té Helado', '${R.drawable.matcha}', 40.0, 'Té negro con hielo y limón.', 'Cold drinks'),
        
        ('Sándwich de Pavo', '${R.drawable.clubsandwich}', 120.0, 'Pan artesanal con pechuga de pavo y queso.', 'Salties'),
        ('Croissant Jamón/Queso', '${R.drawable.hampanini}', 75.0, 'Cuernito horneado con jamón y queso derretido.', 'Salties'),
        
        ('Cheesecake de Fresa', '${R.drawable.strawberrycheesecake}', 95.0, 'Rebanada clásica de nuestro mejor cheesecake.', 'Sweets'),
        ('Brownie con Helado', '${R.drawable.coldbrew}', 80.0, 'Brownie de chocolate oscuro calientito.', 'Sweets')
    """.trimIndent()

        // Las imagenes no son exactas la vdd

        db.execSQL(insertSQL)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${ProductsEntry.TABLE_NAME}")
        onCreate(db)
    }

}