package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseInitializer.initialize(this)

        var isAuthenticated = false

        try {
            auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            if (currentUser != null) {
                isAuthenticated = true
                android.util.Log.d("Profile", "Firebase user authenticated: ${currentUser.email}")
            }
        } catch (e: Exception) {
            android.util.Log.e("Profile", "Firebase authentication check failed: ${e.message}")
        }

        if (!isAuthenticated) {
            DemoAuthManager.initialize(this)
            if (DemoAuthManager.isLoggedIn()) {
                isAuthenticated = true
                android.util.Log.d("Profile", "Demo user authenticated: ${DemoAuthManager.getCurrentUserEmail()}")
            }
        }

        if (!isAuthenticated) {
            startActivity(Intent(this, Login::class.java))
            finish()
            return
        }

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

        // Logout button
        binding.logoutButton.setOnClickListener {
            logout()
        }

        // Open notifications overlay dialog
        binding.notificationBell.setOnClickListener {
            NotificationsDialog.show(this)
        }

        // -------------------
        // Bottom navigation
        // -------------------

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

        // 3: Profile (current) – no-op
        binding.bottomNavigation.getChildAt(3).setOnClickListener { /* no-op */ }

        // 4: Settings
        binding.bottomNavigation.getChildAt(4).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }
    }

    private fun logout() {
        // Try Firebase logout first
        try {
            auth.signOut()
            android.util.Log.d("Profile", "Firebase logout successful")
        } catch (e: Exception) {
            android.util.Log.e("Profile", "Firebase logout failed: ${e.message}")
        }

        // Also logout from demo authentication
        DemoAuthManager.initialize(this)
        DemoAuthManager.logout()

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

        // Navigate to login screen
        val intent = Intent(this, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
