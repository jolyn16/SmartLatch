package com.example.smartlatch.model

class ResetPasswordModel {
    fun resetPassword(password: String, confirmPassword: String, callback: (Boolean, String) -> Unit) {
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            callback(false, "Please fill in both fields")
        } else if (password != confirmPassword) {
            callback(false, "Passwords do not match")
        } else {
            // Fake success (replace with backend logic later)
            callback(true, "Password reset successful")
        }
    }
}
