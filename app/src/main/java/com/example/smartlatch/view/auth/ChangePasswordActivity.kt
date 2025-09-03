package com.example.smartlatch.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.R
import com.example.smartlatch.databinding.ActivityChangePasswordBinding
import com.example.smartlatch.view.forgetPass.PasswordChangedSuccessActivity

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
        setupPasswordVisibilityToggles()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.changePasswordButton.setOnClickListener {
            changePassword()
        }

        binding.notificationBell.setOnClickListener {
            // TODO: Handle notifications
        }
    }

    private fun setupPasswordVisibilityToggles() {
        // Current password visibility toggle
        binding.currentPasswordEye.setOnClickListener {
            togglePasswordVisibility(
                binding.currentPasswordInput,
                binding.currentPasswordEye
            )
        }

        // New password visibility toggle
        binding.newPasswordEye.setOnClickListener {
            togglePasswordVisibility(
                binding.newPasswordInput,
                binding.newPasswordEye
            )
        }

        // Confirm password visibility toggle
        binding.confirmPasswordEye.setOnClickListener {
            togglePasswordVisibility(
                binding.confirmPasswordInput,
                binding.confirmPasswordEye
            )
        }
    }

    private fun togglePasswordVisibility(editText: android.widget.EditText, eyeIcon: android.widget.ImageView) {
        if (editText.transformationMethod is PasswordTransformationMethod) {
            editText.transformationMethod = null
            eyeIcon.setImageResource(R.drawable.ic_eye_off)
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            eyeIcon.setImageResource(R.drawable.ic_eye)
        }
        editText.setSelection(editText.text.length)
    }

    private fun changePassword() {
        val currentPassword = binding.currentPasswordInput.text.toString()
        val newPassword = binding.newPasswordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()

        when {
            currentPassword.isEmpty() -> {
                Toast.makeText(this, "Please enter current password", Toast.LENGTH_SHORT).show()
            }
            newPassword.isEmpty() -> {
                Toast.makeText(this, "Please enter new password", Toast.LENGTH_SHORT).show()
            }
            confirmPassword.isEmpty() -> {
                Toast.makeText(this, "Please confirm new password", Toast.LENGTH_SHORT).show()
            }
            newPassword != confirmPassword -> {
                Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show()
            }
            newPassword.length < 6 -> {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // TODO: Implement actual password change logic
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, PasswordChangedSuccessActivity::class.java))
                finish()
            }
        }
    }
}
