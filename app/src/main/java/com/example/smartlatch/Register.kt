package com.example.smartlatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlatch.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Clicks can be wired later
        binding.backToLogin.setOnClickListener { finish() }
    }
}


