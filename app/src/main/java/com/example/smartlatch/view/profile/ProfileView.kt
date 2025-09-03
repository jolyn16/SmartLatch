package com.example.smartlatch.view.profile

interface ProfileView {
    fun navigateToEditProfile()
    fun navigateToDashboard()
    fun navigateToSettings()
    fun showNotifications()   // placeholder
    fun showMessage(message: String)
}
