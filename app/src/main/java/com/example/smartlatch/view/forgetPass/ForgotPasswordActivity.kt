package com.example.smartlatch.view.forgetPass

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivityForgotPasswordBinding
import com.example.smartlatch.model.ForgotPasswordModel
import com.example.smartlatch.presenter.ForgotPasswordPresenter
import com.example.smartlatch.view.auth.ForgotPasswordView
import com.example.smartlatch.view.auth.LoginActivity

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordView {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var presenter: ForgotPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ForgotPasswordPresenter(this, ForgotPasswordModel())

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnConfirm.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            presenter.onConfirmClicked(email)
        }

        binding.backToLogin.setOnClickListener {
            presenter.onBackToLoginClicked()
        }
    }

    // --- ForgotPasswordView methods ---
    override fun showEmailError(message: String) {
        binding.etEmail.error = if (message.isNotEmpty()) message else null
    }

    override fun showResetSuccess(email: String) {
        Toast.makeText(this, "Password reset link sent to $email", Toast.LENGTH_SHORT).show()
    }

    override fun navigateToOtpVerification(email: String) {
        val intent = Intent(this, OtpVerificationActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
