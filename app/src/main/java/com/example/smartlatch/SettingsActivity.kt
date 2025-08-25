package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
        setupSwitchListeners()
    }

    private fun setupClickListeners() {
        // Profile section
        binding.profileSection.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Change password section
        binding.changePasswordSection.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        // Notification bell
        binding.notificationBell.setOnClickListener {
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
        }

        // Logout button
        binding.logoutButton.setOnClickListener {
            logout()
        }

        // Bottom navigation click listeners
        binding.bottomNavigation.getChildAt(0).setOnClickListener {
            // TODO: Navigate to Chip screen
        }

        binding.bottomNavigation.getChildAt(1).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            finish()
        }

        binding.bottomNavigation.getChildAt(2).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        binding.bottomNavigation.getChildAt(3).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        binding.bottomNavigation.getChildAt(4).setOnClickListener {
            // Already on Settings screen
        }
    }

    private fun setupSwitchListeners() {
        binding.pushNotificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            // TODO: Implement push notifications toggle
            Toast.makeText(this, "Push notifications: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        binding.successfulEntriesSwitch.setOnCheckedChangeListener { _, isChecked ->
            // TODO: Implement successful entries notifications toggle
            Toast.makeText(this, "Successful entries notifications: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        binding.failedAttemptsSwitch.setOnCheckedChangeListener { _, isChecked ->
            // TODO: Implement failed attempts notifications toggle
            Toast.makeText(this, "Failed attempts notifications: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // TODO: Implement dark mode toggle
            Toast.makeText(this, "Dark mode: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logout() {
        // TODO: Implement actual logout logic (clear user session, etc.)
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()
        
        // Navigate back to login screen
        val intent = Intent(this, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
