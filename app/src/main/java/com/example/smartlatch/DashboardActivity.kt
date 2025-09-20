package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        FirebaseInitializer.initialize(this)
        
        // Check authentication status
        var isAuthenticated = false
        
        // Try Firebase authentication first
        try {
            auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            if (currentUser != null) {
                isAuthenticated = true
                android.util.Log.d("Dashboard", "Firebase user authenticated: ${currentUser.email}")
            }
        } catch (e: Exception) {
            android.util.Log.e("Dashboard", "Firebase authentication check failed: ${e.message}")
        }
        
        // If Firebase auth failed, check demo authentication
        if (!isAuthenticated) {
            DemoAuthManager.initialize(this)
            if (DemoAuthManager.isLoggedIn()) {
                isAuthenticated = true
                android.util.Log.d("Dashboard", "Demo user authenticated: ${DemoAuthManager.getCurrentUserEmail()}")
            }
        }
        
        // If not authenticated, redirect to login
        if (!isAuthenticated) {
            startActivity(Intent(this, Login::class.java))
            finish()
            return
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Front Door
        binding.frontDoorDevice.setOnClickListener {
            val intent = Intent(this, DeviceDetailsActivity::class.java)
            intent.putExtra("device_name", "Front Door")
            intent.putExtra("device_owner", "Catherine Mamallas")
            intent.putExtra("is_locked", false)
            startActivity(intent)
        }


        // Bottom Navigation - Chip Device
        binding.navChip?.setOnClickListener {
            val intent = Intent(this, DeviceDetailsActivity::class.java)
            intent.putExtra("device_name", "Chip Device")
            intent.putExtra("device_owner", "Smart Latch System")
            intent.putExtra("is_locked", false)
            startActivity(intent)
        }

        // Bottom Navigation - Home
        binding.navHome?.setOnClickListener {
            // Already on Home
        }

        // Bottom Navigation - Profile
        binding.navProfile?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        // Bottom Navigation - Settings
        binding.navSettings?.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }

        // Notification Bell → show dialog (not a new page)
        binding.notificationBell.setOnClickListener {
            NotificationsDialog.show(this)
        }
    }
}
