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
import com.example.smartlatch.model.SettingsModel
import com.example.smartlatch.presenter.SettingsPresenter
import com.example.smartlatch.view.LoginActivity
import com.example.smartlatch.view.SettingsView

class SettingsActivity : AppCompatActivity(), SettingsView {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SettingsPresenter(this, SettingsModel())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
        setupSwitchListeners()
    }

    private fun setupClickListeners() {
        binding.profileSection.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.changePasswordSection.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        binding.notificationBell.setOnClickListener {
            showMessage("Notifications")
        }
        binding.logoutButton.setOnClickListener { showLogoutConfirmation() }
    }

    private fun setupSwitchListeners() {
        binding.pushNotificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.togglePushNotifications(isChecked)
        }
        binding.successfulEntriesSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.toggleSuccessfulEntries(isChecked)
        }
        binding.failedAttemptsSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.toggleFailedAttempts(isChecked)
        }
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.toggleDarkMode(isChecked)
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Log out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { _, _ -> presenter.logout() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    // --- SettingsView implementation ---
    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
