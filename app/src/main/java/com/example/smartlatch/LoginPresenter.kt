package com.example.smartlatch.presenter

import android.util.Patterns
import com.example.smartlatch.view.LoginView

class LoginPresenter(private val view: LoginView) {

    fun onLoginClicked(email: String, password: String) {
        var isValid = true

        // Email validation
        if (email.isEmpty()) {
            view.showEmailError("Email is required")
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Enter a valid email")
            isValid = false
        } else {
            view.showEmailError("") // clear error
        }

        // Password validation
        if (password.isEmpty()) {
            view.showPasswordError("Password is required")
            isValid = false
        } else {
            view.showPasswordError("") // clear error
        }

        // If valid → proceed
        if (isValid) {
            view.showLoginSuccess()
            view.navigateToDashboard()
        }
    }

    fun onSignUpClicked() {
        view.navigateToRegister()
    }

    fun onForgotPasswordClicked() {
        view.navigateToForgotPassword()
    }
}
