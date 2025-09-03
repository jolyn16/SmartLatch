package com.example.smartlatch.view.auth

interface RegisterView {
    fun showFirstNameError(message: String)
    fun showLastNameError(message: String)
    fun showEmailError(message: String)
    fun showPasswordError(message: String)
    fun showConfirmPasswordError(message: String)
    fun showRegistrationSuccess()
    fun navigateBackToLogin()
}
