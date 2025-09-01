package com.example.smartlatch.presenter

import com.example.smartlatch.model.SettingsModel
import com.example.smartlatch.view.SettingsView

class SettingsPresenter(
    private val view: SettingsView,
    private val model: SettingsModel
) {
    fun togglePushNotifications(enabled: Boolean) {
        model.pushNotificationsEnabled = enabled
        view.showMessage("Push notifications: ${if (enabled) "ON" else "OFF"}")
    }

    fun toggleSuccessfulEntries(enabled: Boolean) {
        model.successfulEntriesEnabled = enabled
        view.showMessage("Successful entries: ${if (enabled) "ON" else "OFF"}")
    }

    fun toggleFailedAttempts(enabled: Boolean) {
        model.failedAttemptsEnabled = enabled
        view.showMessage("Failed attempts: ${if (enabled) "ON" else "OFF"}")
    }

    fun toggleDarkMode(enabled: Boolean) {
        model.darkModeEnabled = enabled
        view.showMessage("Dark mode: ${if (enabled) "ON" else "OFF"}")
    }

    fun logout() {
        view.showMessage("Logging out...")
        view.navigateToLogin()
    }
}
