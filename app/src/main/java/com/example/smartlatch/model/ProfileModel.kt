package com.example.smartlatch.model

data class Profile(
    val name: String,
    val email: String,
    val profileImageUrl: String? = null
)

class ProfileModel {
    // Simulated data source (later can be replaced with DB or API)
    private var profile = Profile("John Doe", "john@example.com", null)

    fun getProfile(): Profile {
        return profile
    }

    fun updateProfile(newProfile: Profile) {
        profile = newProfile
    }
}
