package com.example.smartlatch

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast

object DemoAuthManager {
    private const val PREFS_NAME = "demo_auth_prefs"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_PASSWORD = "user_password"
    private const val KEY_FIRST_NAME = "user_first_name"
    private const val KEY_LAST_NAME = "user_last_name"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        Log.d("DemoAuthManager", "Demo authentication initialized")
    }

    fun registerUser(email: String, password: String, firstName: String, lastName: String, callback: (Boolean, String?) -> Unit) {
        try {
            // Check if user already exists
            val existingEmail = prefs.getString(KEY_EMAIL, null)
            if (existingEmail == email) {
                callback(false, "User already exists with this email")
                return
            }

            // Save user data
            prefs.edit().apply {
                putString(KEY_EMAIL, email)
                putString(KEY_PASSWORD, password)
                putString(KEY_FIRST_NAME, firstName)
                putString(KEY_LAST_NAME, lastName)
                putBoolean(KEY_IS_LOGGED_IN, false)
                apply()
            }

            Log.d("DemoAuthManager", "User registered successfully: $email")
            callback(true, "Registration successful!")
        } catch (e: Exception) {
            Log.e("DemoAuthManager", "Registration failed: ${e.message}")
            callback(false, "Registration failed: ${e.message}")
        }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        try {
            val savedEmail = prefs.getString(KEY_EMAIL, null)
            val savedPassword = prefs.getString(KEY_PASSWORD, null)

            if (savedEmail == email && savedPassword == password) {
                // Login successful
                prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
                Log.d("DemoAuthManager", "Login successful: $email")
                callback(true, "Login successful!")
            } else {
                Log.e("DemoAuthManager", "Login failed: Invalid credentials")
                callback(false, "Invalid email or password")
            }
        } catch (e: Exception) {
            Log.e("DemoAuthManager", "Login failed: ${e.message}")
            callback(false, "Login failed: ${e.message}")
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getCurrentUserEmail(): String? {
        return if (isLoggedIn()) prefs.getString(KEY_EMAIL, null) else null
    }

    fun getCurrentUserFirstName(): String? {
        return if (isLoggedIn()) prefs.getString(KEY_FIRST_NAME, null) else null
    }

    fun getCurrentUserLastName(): String? {
        return if (isLoggedIn()) prefs.getString(KEY_LAST_NAME, null) else null
    }

    fun logout() {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply()
        Log.d("DemoAuthManager", "User logged out")
    }

    fun clearAllData() {
        prefs.edit().clear().apply()
        Log.d("DemoAuthManager", "All user data cleared")
    }
}
