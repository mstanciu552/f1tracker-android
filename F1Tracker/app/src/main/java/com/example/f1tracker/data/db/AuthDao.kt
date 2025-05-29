package com.example.f1tracker.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class AuthDao(context: Context) {
    private val db: SQLiteDatabase = AuthDatabase(context).writableDatabase

    // Function to sanitize inputs
    fun containsSqlInjection(input: String): Boolean {
        val lower = input.lowercase()
        return listOf("'", "\"", "--", ";", " or ", " and ", "1=1", "=")
            .any { pattern -> lower.contains(pattern) }
    }


    fun login(username: String, password: String): Boolean {
        if (containsSqlInjection(username) || containsSqlInjection(password)) {
            Log.w("Security", "Possible SQL injection attempt: username=$username password=$password")
            return false
        }
        val query = "SELECT * FROM users WHERE username = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val success = cursor.count > 0
        cursor.close()
        return success
    }

    fun register(email: String, username: String, password: String): Boolean {
        if (containsSqlInjection(username) || containsSqlInjection(password) || containsSqlInjection(email)) {
            Log.w("Security", "Possible SQL injection attempt: email=$email username=$username password=$password")
            return false
        }
        val query = db.compileStatement("INSERT INTO users (email, username, password) VALUES (?, ?, ?)")
        return try {
            query.bindString(1, email)
            query.bindString(2, username)
            query.bindString(3, password)
            query.executeInsert()
            true
        } catch (e: Exception) {
            false
        }
    }
}