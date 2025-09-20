package com.example.smartlatch.presenter

import android.util.Patterns
import com.example.smartlatch.model.ForgotPasswordModel
import com.example.smartlatch.view.ForgotPasswordView
class ForgotPasswordPresenter(
    private val view: ForgotPasswordView,
    private val model: ForgotPasswordModel
) {

    fun onConfirmClicked(email: String) {
        if (email.isEmpty()) {
            view.showEmailError("Email is required")
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Enter a valid email")
            return
        } else {
            view.showEmailError("") // clear
        }

        // Simulate sending reset link
        model.sendResetLink(email)
        view.showResetSuccess(email)
        view.navigateToOtpVerification(email)
    }

    fun onBackToLoginClicked() {
        view.navigateToLogin()
    }
}
