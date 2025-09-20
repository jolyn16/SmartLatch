package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivityResetPasswordBinding

class ResetPassword : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var email: String
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get email from OTP screen
        email = intent.getStringExtra("email") ?: ""

        setupClickListeners()
        setupTogglePassword()
    }

    private fun setupClickListeners() {
        binding.btnConfirm.setOnClickListener {
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Send password reset request to backend using 'email' and 'password'
                Toast.makeText(this, "Password reset successful for $email", Toast.LENGTH_SHORT).show()

                // Navigate to PasswordChangedActivity
                val intent = Intent(this, PasswordChangedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.backToLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }

    private fun setupTogglePassword() {
        binding.ivTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            binding.etPassword.transformationMethod =
                if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }

        binding.ivToggleConfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            binding.etConfirmPassword.transformationMethod =
                if (isConfirmPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
            binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.length)
        }
    }
}
