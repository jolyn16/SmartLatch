package com.example.smartlatch.presenter

import android.util.Patterns
import com.example.smartlatch.model.User
import com.example.smartlatch.model.RegisterModel
import com.example.smartlatch.view.auth.RegisterView

class RegisterPresenter(
    private val view: RegisterView,
    private val model: RegisterModel
) {

    fun onRegisterClicked(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        // Validation
        if (firstName.isEmpty()) {
            view.showFirstNameError("Required")
            return
        } else view.showFirstNameError("")

        if (lastName.isEmpty()) {
            view.showLastNameError("Required")
            return
        } else view.showLastNameError("")

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Valid email required")
            return
        } else view.showEmailError("")

        if (password.isEmpty() || password.length < 6) {
            view.showPasswordError("Min 6 chars")
            return
        } else view.showPasswordError("")

        if (confirmPassword.isEmpty()) {
            view.showConfirmPasswordError("Confirm password")
            return
        }

        if (password != confirmPassword) {
            view.showConfirmPasswordError("Passwords do not match")
            return
        } else view.showConfirmPasswordError("")

        // Simulate saving user (for now always success)
        model.registerUser(User(email, password))
        view.showRegistrationSuccess()
        view.navigateBackToLogin()
    }

    fun onBackToLoginClicked() {
        view.navigateBackToLogin()
    }
}
