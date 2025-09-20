package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
        setupBottomNavigation()
    }

    private fun setupClickListeners() {
        binding.profileSection.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
        binding.changePasswordSection.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        binding.notificationBell.setOnClickListener {
            NotificationsDialog.show(this)
        }
        binding.logoutButton.setOnClickListener { showLogoutConfirmation() }
    }

    private fun setupSwitchListeners() {
        binding.pushNotificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Push notifications: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }
        binding.successfulEntriesSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Successful entries notifications: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }
        binding.failedAttemptsSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Failed attempts notifications: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Dark mode: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
        // 0: Devices/Chip → Dashboard
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

        // 4: Settings (current) – no action
        binding.bottomNavigation.getChildAt(4).setOnClickListener { /* no-op */ }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Log out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ -> logout() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun logout() {
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
