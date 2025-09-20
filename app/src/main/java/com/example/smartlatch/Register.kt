package com.example.smartlatch

import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlatch.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseInitializer.initialize(this)
        try {
            auth = FirebaseAuth.getInstance()
            firestore = FirebaseFirestore.getInstance()
            android.util.Log.d("Register", "Firebase initialized successfully")
        } catch (e: Exception) {
            android.util.Log.e("Register", "Firebase initialization failed: ${e.message}")
            Toast.makeText(this, "Firebase initialization failed. Please try again.", Toast.LENGTH_SHORT).show()
        }
        binding.backToLogin.setOnClickListener { finish() }

        // Register button click
        binding.btnRegister.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        // Validation
        if (firstName.isEmpty()) { binding.etFirstName.error = "Required"; return }
        if (lastName.isEmpty()) { binding.etLastName.error = "Required"; return }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) { binding.etEmail.error = "Valid email required"; return }
        if (password.isEmpty() || password.length < 6) { binding.etPassword.error = "Min 6 chars"; return }
        if (confirmPassword.isEmpty()) { binding.etConfirmPassword.error = "Confirm password"; return }
        if (password != confirmPassword) { binding.etConfirmPassword.error = "Passwords do not match"; return }

        // Disable button and show loading
        binding.btnRegister.isEnabled = false
        binding.btnRegister.text = "Creating Account..."

        // Try Firebase registration first, fallback to demo
        try {
            android.util.Log.d("Register", "Attempting to create user with email: $email")
            auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.btnRegister.isEnabled = true
                binding.btnRegister.text = "Sign Up"

                if (task.isSuccessful) {
                    // User created successfully, now save additional info to Firestore
                    val user = auth.currentUser
                    android.util.Log.d("Register", "Firebase user created successfully: ${user?.email}")
                    user?.let {
                        val userData = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "email" to email,
                            "createdAt" to System.currentTimeMillis()
                        )

                        firestore.collection("users")
                            .document(it.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                                finish() // back to login
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Registration successful, but failed to save profile: ${e.message}", 
                                    Toast.LENGTH_LONG).show()
                                finish() // back to login
                            }
                    }
                } else {
                    // Firebase failed, try demo registration
                    android.util.Log.e("Register", "Firebase registration failed: ${task.exception?.message}")
                    tryDemoRegistration(email, password, firstName, lastName)
                }
            }
        } catch (e: Exception) {
            // Firebase not available, use demo registration
            android.util.Log.e("Register", "Firebase not available, using demo registration: ${e.message}")
            tryDemoRegistration(email, password, firstName, lastName)
        }
    }

    private fun tryDemoRegistration(email: String, password: String, firstName: String, lastName: String) {
        DemoAuthManager.initialize(this)
        DemoAuthManager.registerUser(email, password, firstName, lastName) { success, message ->
            binding.btnRegister.isEnabled = true
            binding.btnRegister.text = "Sign Up"
            
            if (success) {
                android.util.Log.d("Register", "Demo registration successful: $email")
                Toast.makeText(this, "Registration successful! (Demo Mode)", Toast.LENGTH_SHORT).show()
                finish() // back to login
            } else {
                android.util.Log.e("Register", "Demo registration failed: $message")
                Toast.makeText(this, "Registration failed: $message", Toast.LENGTH_LONG).show()
            }
        }
    }
}
