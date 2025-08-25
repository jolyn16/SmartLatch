package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivityDeviceDetailsBinding

class DeviceDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailsBinding
    private var isLocked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDeviceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        setupDeviceInfo()
        setupClickListeners()
    }
    
    private fun setupDeviceInfo() {
        val deviceName = intent.getStringExtra("device_name") ?: "Front Door"
        val deviceOwner = intent.getStringExtra("device_owner") ?: "Catherine Mamallas"
        isLocked = intent.getBooleanExtra("is_locked", false)
        
        binding.deviceNameText.text = deviceName
        updateLockStatus()
    }
    
    private fun updateLockStatus() {
        if (isLocked) {
            binding.unlockedSection.setBackgroundColor(resources.getColor(android.R.color.transparent, null))
            binding.lockedSection.setBackgroundColor(resources.getColor(android.R.color.darker_gray, null))
            binding.unlockedIcon.setColorFilter(resources.getColor(android.R.color.darker_gray, null))
            binding.lockedIcon.setColorFilter(resources.getColor(android.R.color.black, null))
        } else {
            binding.unlockedSection.setBackgroundColor(resources.getColor(android.R.color.darker_gray, null))
            binding.lockedSection.setBackgroundColor(resources.getColor(android.R.color.transparent, null))
            binding.unlockedIcon.setColorFilter(resources.getColor(android.R.color.black, null))
            binding.lockedIcon.setColorFilter(resources.getColor(android.R.color.darker_gray, null))
        }
    }
    
    private fun setupClickListeners() {
        // Back button
        binding.backButton.setOnClickListener {
            finish()
        }
        
        // Lock/Unlock toggle
        binding.lockToggleLayout.setOnClickListener {
            isLocked = !isLocked
            updateLockStatus()
            val status = if (isLocked) "locked" else "unlocked"
            Toast.makeText(this, "Device $status", Toast.LENGTH_SHORT).show()
        }
        
        // Visitor Permissions section
        binding.visitorPermissionsSection.setOnClickListener {
            val intent = Intent(this, PermissionsActivity::class.java)
            startActivity(intent)
        }
        
        // Visitor Requests section
        binding.visitorRequestsSection.setOnClickListener {
            val intent = Intent(this, RequestsActivity::class.java)
            startActivity(intent)
        }
        
        // Bottom navigation
        binding.navChip.setOnClickListener {
            // Already on chip section
        }
        
        binding.navBook.setOnClickListener {
            Toast.makeText(this, "Book section", Toast.LENGTH_SHORT).show()
        }
        
        binding.navHome.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        
        binding.navProfile.setOnClickListener {
            Toast.makeText(this, "Profile section", Toast.LENGTH_SHORT).show()
        }
        
        binding.navSettings.setOnClickListener {
            Toast.makeText(this, "Settings section", Toast.LENGTH_SHORT).show()
        }
        
        // Notification bell
        binding.notificationBell.setOnClickListener {
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
        }
    }
}
