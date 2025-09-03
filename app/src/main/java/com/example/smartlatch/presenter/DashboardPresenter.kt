package com.example.smartlatch.presenter

import com.example.smartlatch.model.DashboardModel
import com.example.smartlatch.view.dashboard.DashboardView

class DashboardPresenter(private val view: DashboardView, private val model: DashboardModel) {

    fun onDeviceClicked(deviceName: String, deviceOwner: String, isLocked: Boolean) {
        val device = model.getDeviceDetails(deviceName, deviceOwner, isLocked)
        view.navigateToDeviceDetails(
            device["device_name"] as String,
            device["device_owner"] as String,
            device["is_locked"] as Boolean
        )
    }

    fun onConnectionClicked() {
        view.showToast("Connecting to devices...")
    }

    fun onVoiceSearchClicked() {
        view.showToast("Voice search activated")
    }

    fun onHistoryClicked() {
        view.navigateToHistory()
    }

    fun onProfileClicked() {
        view.navigateToProfile()
    }

    fun onSettingsClicked() {
        view.navigateToSettings()
    }

    fun onNotificationClicked() {
        view.navigateToNotification()
    }
}
