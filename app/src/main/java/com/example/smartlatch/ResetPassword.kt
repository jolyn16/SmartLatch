package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlatch.databinding.ActivityResetPasswordBinding
import com.example.smartlatch.model.ResetPasswordModel
import com.example.smartlatch.presenter.ResetPasswordPresenter
import com.example.smartlatch.view.LoginActivity
import com.example.smartlatch.view.ResetPasswordView

class ResetPassword : AppCompatActivity(), ResetPasswordView {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var presenter: ResetPasswordPresenter
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ResetPasswordPresenter(this, ResetPasswordModel())

        setupClickListeners()
        setupTogglePassword()
    }

    private fun setupClickListeners() {
        binding.btnConfirm.setOnClickListener {
            presenter.onResetPassword(
                binding.etPassword.text.toString(),
                binding.etConfirmPassword.text.toString()
            )
        }

        binding.backToLogin.setOnClickListener {
            presenter.onBackToLogin()
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

    // ===== ResetPasswordView implementation =====
    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToPasswordChanged() {
        startActivity(Intent(this, PasswordChangedActivity::class.java))
        finish()
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
