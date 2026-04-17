package com.chris.login.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.chris.login.data.StoreContract.ProductsEntry

class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME="store.db"
        private const val DATABASE_VERSION = 1
    }

    // Base de datos
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys = ON") // Permitimos las llaves foraneas en la BD

        // Creamos la tabla
        db.execSQL(
            """CREATE TABLE ${ProductsEntry.TABLE_NAME} (
            ${ProductsEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${ProductsEntry.COLUMN_NAME} TEXT NOT NULL,
            ${ProductsEntry.COLUMN_IMAGE} INTEGER,
            ${ProductsEntry.COLUMN_PRICE} REAL NOT NULL,
            ${ProductsEntry.COLUMN_DESCRIPTION} TEXT
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${ProductsEntry.TABLE_NAME}")
    }

}