package com.example.smartlatch

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.analytics.FirebaseAnalytics

object FirebaseInitializer {
    
    fun initialize(context: Context) {
        try {
            // Initialize Firebase if not already initialized
            if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
                Log.d("FirebaseInitializer", "Firebase initialized successfully")
            }
            
            // Initialize Firebase Auth
            FirebaseAuth.getInstance()
            Log.d("FirebaseInitializer", "Firebase Auth initialized")
            
            // Initialize Firestore
            FirebaseFirestore.getInstance()
            Log.d("FirebaseInitializer", "Firestore initialized")
            
            // Initialize Analytics
            FirebaseAnalytics.getInstance(context)
            Log.d("FirebaseInitializer", "Firebase Analytics initialized")
            
        } catch (e: Exception) {
            Log.e("FirebaseInitializer", "Firebase initialization failed", e)
            e.printStackTrace()
        }
    }
    
    fun isFirebaseInitialized(): Boolean {
        return try {
            FirebaseApp.getInstance() != null
        } catch (e: Exception) {
            false
        }
    }
}
