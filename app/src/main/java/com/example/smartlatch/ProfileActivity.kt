package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivityProfileBinding
import com.example.smartlatch.presenter.ProfilePresenter
import com.example.smartlatch.view.ProfileView

class ProfileActivity : AppCompatActivity(), ProfileView {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ProfilePresenter(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.editProfileButton.setOnClickListener {
            presenter.onEditProfileClicked()
        }

        binding.notificationBell.setOnClickListener {
            presenter.onNotificationClicked()
        }

        binding.bottomNavigation.getChildAt(0).setOnClickListener {
            presenter.onChipClicked()
        }

        binding.bottomNavigation.getChildAt(1).setOnClickListener {
            presenter.onBookClicked()
        }

        binding.bottomNavigation.getChildAt(2).setOnClickListener {
            presenter.onDashboardClicked()
        }

        binding.bottomNavigation.getChildAt(3).setOnClickListener {
            // already in Profile
            showMessage("Already on Profile screen")
        }

        binding.bottomNavigation.getChildAt(4).setOnClickListener {
            presenter.onSettingsClicked()
        }
    }

    // ===== ProfileView implementation =====
    override fun navigateToEditProfile() {
        startActivity(Intent(this, EditProfileActivity::class.java))
    }

    override fun navigateToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun navigateToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
        finish()
    }

    override fun showNotifications() {
        Toast.makeText(this, "Notifications coming soon!", Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
