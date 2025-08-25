package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        // Device click listeners
        binding.frontDoorDevice.setOnClickListener {
            val intent = Intent(this, DeviceDetailsActivity::class.java)
            intent.putExtra("device_name", "Front Door")
            intent.putExtra("device_owner", "Catherine Mamallas")
            intent.putExtra("is_locked", false)
            startActivity(intent)
        }
        
        binding.backDoorDevice.setOnClickListener {
            val intent = Intent(this, DeviceDetailsActivity::class.java)
            intent.putExtra("device_name", "Back Door")
            intent.putExtra("device_owner", "Jelian Minoza")
            intent.putExtra("is_locked", true)
            startActivity(intent)
        }
        
        // Connection button
        binding.connectionButton.setOnClickListener {
            Toast.makeText(this, "Connecting to devices...", Toast.LENGTH_SHORT).show()
        }
        
        // Search functionality
        binding.voiceSearchButton.setOnClickListener {
            Toast.makeText(this, "Voice search activated", Toast.LENGTH_SHORT).show()
        }
        
        // Bottom navigation
        binding.navChip.setOnClickListener {
            Toast.makeText(this, "Chip section", Toast.LENGTH_SHORT).show()
        }
        
        binding.navBook.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            finish()
        }
        
        binding.navHome.setOnClickListener {
            // Already on home
        }
        
        binding.navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
        
        binding.navSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }
        
        // Notification bell
        binding.notificationBell.setOnClickListener {
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
        }
    }
}
