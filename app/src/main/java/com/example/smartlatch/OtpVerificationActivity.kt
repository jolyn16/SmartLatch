package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartlatch.databinding.ActivityOtpVerificationBinding
import com.example.smartlatch.view.LoginActivity

class OtpVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpVerificationBinding
    private lateinit var email: String  // get email from ForgotPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOtpVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the email from the previous activity
        email = intent.getStringExtra("email") ?: ""

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupOtpInputs()
        setupClickListeners()
    }

    private fun setupOtpInputs() {
        val otpInputs = listOf(binding.otp1, binding.otp2, binding.otp3, binding.otp4)
        for (i in otpInputs.indices) {
            otpInputs[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && i < otpInputs.size - 1) {
                        otpInputs[i + 1].requestFocus()
                    }
                }
            })
        }
    }

    private fun setupClickListeners() {
        binding.submitButton.setOnClickListener {
            val otp = getOtpCode()
            if (otp.length == 4) {
                Toast.makeText(this, "OTP Verified", Toast.LENGTH_SHORT).show()

                // Navigate to your ResetPassword activity with the email
                val intent = Intent(this, ResetPassword::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
                finish() // prevent going back to OTP screen
            } else {
                Toast.makeText(this, "Please enter complete OTP", Toast.LENGTH_SHORT).show()
            }
        }

        binding.resendLink.setOnClickListener {
            Toast.makeText(this, "OTP resent", Toast.LENGTH_SHORT).show()
            // implement resend OTP logic if needed
        }

        binding.backToLoginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun getOtpCode(): String {
        return binding.otp1.text.toString() +
                binding.otp2.text.toString() +
                binding.otp3.text.toString() +
                binding.otp4.text.toString()
    }
}
