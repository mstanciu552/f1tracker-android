package com.example.f1tracker.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class AuthDao(context: Context) {
    private val db: SQLiteDatabase = AuthDatabase(context).writableDatabase

    // Vulnerable: No input sanitization, SQLi possible
    fun login(username: String, password: String): Boolean {
        val query = "SELECT * FROM users WHERE username = '$username' AND password = '$password'"
        val cursor = db.rawQuery(query, null)
        val success = cursor.count > 0
        cursor.close()
        return success
    }

    fun register(email: String, username: String, password: String): Boolean {
        val query = "INSERT INTO users (email, username, password) VALUES ('$email', '$username', '$password')"
        return try {
            db.execSQL(query)
            true
        } catch (e: Exception) {
            false
        }
    }
}