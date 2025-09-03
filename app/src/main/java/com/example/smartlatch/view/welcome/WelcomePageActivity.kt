package com.example.smartlatch.view.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivityWelcomePageBinding
import com.example.smartlatch.view.auth.LoginActivity

class WelcomePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWelcomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adjust window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate to Login screen
        binding.getStarted.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }


    }
}
