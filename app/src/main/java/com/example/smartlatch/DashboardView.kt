package com.example.smartlatch.dashboard

interface DashboardView {
    fun showToast(message: String)
    fun navigateToDeviceDetails(deviceName: String, deviceOwner: String, isLocked: Boolean)
    fun navigateToHistory()
    fun navigateToProfile()
    fun navigateToSettings()
    fun navigateToNotification()
}
