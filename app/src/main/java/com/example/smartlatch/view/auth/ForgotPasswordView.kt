package com.example.smartlatch.view.auth

interface ForgotPasswordView {
    fun showEmailError(message: String)
    fun showResetSuccess(email: String)
    fun navigateToOtpVerification(email: String)
    fun navigateToLogin()
}
