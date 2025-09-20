package com.example.smartlatch

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

object NotificationsDialog {
    fun show(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_notifications, null, false)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)
        val dialog = builder.create()

        // Actions
        dialogView.findViewById<TextView>(R.id.markAsRead)?.setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<ImageView>(R.id.settingsIcon)?.setOnClickListener {
            context.startActivity(android.content.Intent(context, SettingsActivity::class.java))
            dialog.dismiss()
        }

        dialog.show()
    }
}
