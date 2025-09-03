package com.example.smartlatch.model

data class User(val email: String, val password: String)

class LoginModel {
    fun authenticate(user: User): Boolean {
        // Allow login for ANY user with non-empty credentials
        return user.email.isNotEmpty() && user.password.isNotEmpty()
    }
}
