package com.example.smartlatch.model

class RegisterModel {
    private val registeredUsers = mutableListOf<User>()

    fun registerUser(user: User) {
        // Save to list (later replace with DB or Firebase)
        registeredUsers.add(user)
    }

    fun getAllUsers(): List<User> = registeredUsers
}
