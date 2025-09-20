package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlatch.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import android.util.Log

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseInitializer.initialize(this)

        auth = FirebaseAuth.getInstance()
        Log.d("Login", "Firebase Auth initialized successfully")
       try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)
            Log.d("Login", "Google Sign-In configured successfully")
        } catch (e: Exception) {
            Log.e("Login", "Google Sign-In configuration failed: ${e.message}")
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            var isValid = true

            if (email.isEmpty()) {
                binding.emailEditText.error = "Email is required"
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEditText.error = "Enter a valid email"
                isValid = false
            } else {
                binding.emailEditText.error = null
            }

            if (password.isEmpty()) {
                binding.passwordEditText.error = "Password is required"
                isValid = false
            } else {
                binding.passwordEditText.error = null
            }
            if (isValid) {
                signInWithEmailAndPassword(email, password)
            }
        }

        binding.signUpText.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }

        binding.googleSignInButton.setOnClickListener {
            try {
                signInWithGoogle()
            } catch (e: Exception) {
                Toast.makeText(this, "Google Sign-In not available. Please use email/password.", Toast.LENGTH_SHORT).show()
                Log.e("Login", "Google Sign-In failed: ${e.message}")
            }
        }

        testFirebaseConnection()
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        binding.loginButton.isEnabled = false
        binding.loginButton.text = "Signing in..."

        Log.d("Login", "Attempting to sign in with email: $email")

        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.loginButton.isEnabled = true
                    binding.loginButton.text = "Log In"

                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.d("Login", "Firebase login successful for user: ${user?.email}")
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("Login", "Firebase login failed: ${task.exception?.message}")
                        tryDemoAuthentication(email, password)
                    }
                }
        } catch (e: Exception) {
            Log.e("Login", "Firebase not available, using demo auth: ${e.message}")
            tryDemoAuthentication(email, password)
        }
    }

    private fun tryDemoAuthentication(email: String, password: String) {
        DemoAuthManager.initialize(this)
        DemoAuthManager.loginUser(email, password) { success, message ->
            binding.loginButton.isEnabled = true
            binding.loginButton.text = "Log In"
            
            if (success) {
                Log.d("Login", "Demo login successful for user: $email")
                Toast.makeText(this, "Login successful! (Demo Mode)", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.e("Login", "Demo login failed: $message")
                Toast.makeText(this, "Authentication failed: $message", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signInWithGoogle() {
        try {
            if (::googleSignInClient.isInitialized) {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            } else {
                Toast.makeText(this, "Google Sign-In not configured. Please use email/password.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Google Sign-In error: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("Login", "Google Sign-In error: ${e.message}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(this, "Google sign in successful!", Toast.LENGTH_SHORT).show()
                    
                    // Navigate to DashboardActivity
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", 
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun testFirebaseConnection() {
        try {
            // Test Firebase Auth
            val currentUser = auth.currentUser
            Log.d("Login", "Firebase Auth test - Current user: ${currentUser?.email ?: "No user logged in"}")
            
            // Test Firebase initialization
            if (FirebaseInitializer.isFirebaseInitialized()) {
                Log.d("Login", "Firebase initialization test: SUCCESS")
                Toast.makeText(this, "Firebase connected successfully", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("Login", "Firebase initialization test: FAILED")
                Toast.makeText(this, "Firebase connection failed", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("Login", "Firebase connection test failed: ${e.message}")
            Toast.makeText(this, "Firebase test error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
