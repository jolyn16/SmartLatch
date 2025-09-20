package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlatch.databinding.ActivityDeviceDetailsBinding

class DeviceDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailsBinding
    private var isLocked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val deviceName = intent.getStringExtra("device_name") ?: "Unknown Device"
        val deviceOwner = intent.getStringExtra("device_owner") ?: "Unknown Owner"
        isLocked = intent.getBooleanExtra("is_locked", false)

        // Set initial UI values
        binding.deviceNameText.text = deviceName
        updateLockUI()

        // Back button
        binding.backButton.setOnClickListener {
            finish()
        }

        // Notifications → dialog
        binding.notificationBell.setOnClickListener {
            NotificationsDialog.show(this)
        }

        // Lock/Unlock toggle
        binding.lockToggleLayout.setOnClickListener {
            isLocked = !isLocked
            updateLockUI()
            Toast.makeText(
                this,
                if (isLocked) "Device locked 🔒" else "Device unlocked 🔓",
                Toast.LENGTH_SHORT
            ).show()
        }

        // -------------------
        // Bottom navigation
        // -------------------

        // 0: Devices (chip) → Dashboard
        binding.bottomNavigation.getChildAt(0).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        // 2: Home → Dashboard
        binding.bottomNavigation.getChildAt(2).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        // 3: Profile
        binding.bottomNavigation.getChildAt(3).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        // 4: Settings
        binding.bottomNavigation.getChildAt(4).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }
    }

    private fun updateLockUI() {
        if (isLocked) {
            // Show locked icon
            binding.lockedIcon.setImageResource(R.drawable.ic_lockk)
            binding.unlockedIcon.setImageResource(R.drawable.ic_lock_open)
        } else {
            // Show unlocked icon
            binding.lockedIcon.setImageResource(R.drawable.ic_lock_open)
            binding.unlockedIcon.setImageResource(R.drawable.ic_lockk)
        }
    }
}
