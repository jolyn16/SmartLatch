package com.example.smartlatch.model

class DashboardModel {
    fun getDeviceDetails(deviceName: String, deviceOwner: String, isLocked: Boolean): Map<String, Any> {
        return mapOf(
            "device_name" to deviceName,
            "device_owner" to deviceOwner,
            "is_locked" to isLocked
        )
    }
}
