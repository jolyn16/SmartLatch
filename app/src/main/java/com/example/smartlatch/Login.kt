package com.example.smartlatch.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlatch.DashboardActivity
import com.example.smartlatch.view.ForgotPasswordActivity
import com.example.smartlatch.RequestsActivity
import com.example.smartlatch.databinding.ActivityLoginBinding
import com.example.smartlatch.presenter.LoginPresenter

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Login button
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            presenter.onLoginClicked(email, password)
        }

        // Sign Up text
        binding.signUpText.setOnClickListener {
            presenter.onSignUpClicked()
        }

        // Forgot Password text
        binding.forgotPassword.setOnClickListener {
            presenter.onForgotPasswordClicked()
        }
    }

    // --- LoginView interface methods ---
    override fun showEmailError(message: String) {
        binding.emailEditText.error = if (message.isNotEmpty()) message else null
    }

    override fun showPasswordError(message: String) {
        binding.passwordEditText.error = if (message.isNotEmpty()) message else null
    }

    override fun showLoginSuccess() {
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
    }

    override fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun navigateToForgotPassword() {
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }
}
