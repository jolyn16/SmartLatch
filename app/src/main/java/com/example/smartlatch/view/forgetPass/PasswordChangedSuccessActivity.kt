package com.example.smartlatch.view.forgetPass

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.R
import com.example.smartlatch.databinding.ActivityPasswordChangedSuccessBinding
import com.example.smartlatch.view.profile.ProfileActivity

class PasswordChangedSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordChangedSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPasswordChangedSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish() // Go back to the previous screen
        }

        binding.doneButton.setOnClickListener {
            // Navigate back to ProfileActivity
            val intent = Intent(this, ProfileActivity::class.java)
            // Clear intermediate activities so user goes directly to profile
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        binding.notificationBell.setOnClickListener {
            // TODO: Handle notifications
        }
    }
}
