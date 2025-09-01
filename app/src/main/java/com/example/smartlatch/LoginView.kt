package com.example.smartlatch.view

interface LoginView {
    fun showEmailError(message: String)
    fun showPasswordError(message: String)
    fun showLoginSuccess()
    fun navigateToDashboard()
    fun navigateToRegister()
    fun navigateToForgotPassword()
}
