package com.example.eva1
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USERNAME TEXT,"
                + "$COLUMN_PASSWORD TEXT)")
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }
    fun addUser(username: String, password: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, password)


        val result = db.insert(TABLE_USERS, null, values)


        if (result == -1L) {
            Log.e("SQLiteHelper", "Error al insertar el usuario")
        } else {
            Log.i("SQLiteHelper", "Usuario insertado correctamente")}
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?",
            arrayOf(username, password)
        )

        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun updateUser(username: String, newPassword: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PASSWORD, newPassword)
        }


        return db.update(TABLE_USERS, values, "$COLUMN_USERNAME=?", arrayOf(username))
    }

    fun deleteUser(username: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_USERS, "$COLUMN_USERNAME=?", arrayOf(username))
    }

    fun getAllUsers(): List<String> {
        val userList = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_USERS", null)

        if (cursor.moveToFirst()) {
            do {
                val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
                userList.add(username)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }


    fun eliminarUsuariosDuplicados() {
        val db = this.writableDatabase
        db.execSQL("""
        DELETE FROM $TABLE_USERS 
        WHERE $COLUMN_ID NOT IN (
            SELECT MIN($COLUMN_ID) 
            FROM $TABLE_USERS 
            GROUP BY $COLUMN_USERNAME
        )
    """)

    }
}
