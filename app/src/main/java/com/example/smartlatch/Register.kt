package com.example.smartlatch.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlatch.databinding.ActivityRegisterBinding
import com.example.smartlatch.model.RegisterModel
import com.example.smartlatch.presenter.RegisterPresenter

class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = RegisterPresenter(this, RegisterModel())

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backToLogin.setOnClickListener {
            presenter.onBackToLoginClicked()
        }

        binding.btnRegister.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            presenter.onRegisterClicked(firstName, lastName, email, password, confirmPassword)
        }
    }

    // --- RegisterView methods ---
    override fun showFirstNameError(message: String) {
        binding.etFirstName.error = if (message.isNotEmpty()) message else null
    }

    override fun showLastNameError(message: String) {
        binding.etLastName.error = if (message.isNotEmpty()) message else null
    }

    override fun showEmailError(message: String) {
        binding.etEmail.error = if (message.isNotEmpty()) message else null
    }

    override fun showPasswordError(message: String) {
        binding.etPassword.error = if (message.isNotEmpty()) message else null
    }

    override fun showConfirmPasswordError(message: String) {
        binding.etConfirmPassword.error = if (message.isNotEmpty()) message else null
    }

    override fun showRegistrationSuccess() {
        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
    }

    override fun navigateBackToLogin() {
        finish()
    }
}
