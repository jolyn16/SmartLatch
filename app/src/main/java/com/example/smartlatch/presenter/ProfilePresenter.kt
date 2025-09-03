package com.example.smartlatch.presenter

import com.example.smartlatch.view.profile.ProfileView

class ProfilePresenter(private val view: ProfileView) {

    fun onEditProfileClicked() {
        view.navigateToEditProfile()
    }

    fun onDashboardClicked() {
        view.navigateToDashboard()
    }

    fun onSettingsClicked() {
        view.navigateToSettings()
    }

    fun onNotificationClicked() {
        view.showNotifications()
    }

    fun onBookClicked() {
        view.showMessage("Navigate to Book screen (TODO)")
    }

    fun onChipClicked() {
        view.showMessage("Navigate to Chip screen (TODO)")
    }
}
