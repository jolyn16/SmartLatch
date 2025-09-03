package com.example.smartlatch.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.R
import com.example.smartlatch.databinding.ActivityProfileBinding
import com.example.smartlatch.view.dashboard.DashboardActivity
import com.example.smartlatch.view.settings.SettingsActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.editProfileButton.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        binding.notificationBell.setOnClickListener {
            // TODO: Handle notifications
        }

        // Bottom navigation click listeners
        binding.bottomNavigation.getChildAt(0).setOnClickListener {
            // TODO: Navigate to Chip screen
        }

        binding.bottomNavigation.getChildAt(1).setOnClickListener {
            // TODO: Navigate to Book screen
        }

        binding.bottomNavigation.getChildAt(2).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        binding.bottomNavigation.getChildAt(3).setOnClickListener {
            // Already on Profile screen
        }

        binding.bottomNavigation.getChildAt(4).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }
    }
}
