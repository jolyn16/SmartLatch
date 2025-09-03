package com.example.smartlatch.presenter

import com.example.smartlatch.model.ResetPasswordModel
import com.example.smartlatch.view.forgetPass.ResetPasswordView

class ResetPasswordPresenter(
    private val view: ResetPasswordView,
    private val model: ResetPasswordModel
) {
    fun onResetPassword(password: String, confirmPassword: String) {
        model.resetPassword(password, confirmPassword) { success, message ->
            view.showMessage(message)
            if (success) {
                view.navigateToPasswordChanged()
            }
        }
    }

    fun onBackToLogin() {
        view.navigateToLogin()
    }
}
