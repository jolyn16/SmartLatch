package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.dashboard.DashboardModel
import com.example.smartlatch.dashboard.DashboardPresenter
import com.example.smartlatch.dashboard.DashboardView
import com.example.smartlatch.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity(), DashboardView {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var presenter: DashboardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DashboardPresenter(this, DashboardModel())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.frontDoorDevice.setOnClickListener {
            presenter.onDeviceClicked("Front Door", "Catherine Mamallas", false)
        }

        binding.backDoorDevice.setOnClickListener {
            presenter.onDeviceClicked("Back Door", "Jelian Minoza", true)
        }

        binding.connectionButton.setOnClickListener {
            presenter.onConnectionClicked()
        }

        binding.voiceSearchButton.setOnClickListener {
            presenter.onVoiceSearchClicked()
        }

        binding.navChip?.setOnClickListener {
            presenter.onDeviceClicked("Chip Device", "Smart Latch System", false)
        }

        binding.navBook?.setOnClickListener {
            presenter.onHistoryClicked()
        }

        binding.navProfile?.setOnClickListener {
            presenter.onProfileClicked()
        }

        binding.navSettings?.setOnClickListener {
            presenter.onSettingsClicked()
        }

        binding.notificationBell.setOnClickListener {
            presenter.onNotificationClicked()
        }
    }

    // ================= DashboardView Implementation =================
    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToDeviceDetails(deviceName: String, deviceOwner: String, isLocked: Boolean) {
        val intent = Intent(this, DeviceDetailsActivity::class.java)
        intent.putExtra("device_name", deviceName)
        intent.putExtra("device_owner", deviceOwner)
        intent.putExtra("is_locked", isLocked)
        startActivity(intent)
    }

    override fun navigateToHistory() {
        startActivity(Intent(this, HistoryActivity::class.java))
        finish()
    }

    override fun navigateToProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
    }

    override fun navigateToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
        finish()
    }

    override fun navigateToNotification() {
        startActivity(Intent(this, Notification::class.java))
    }
}
