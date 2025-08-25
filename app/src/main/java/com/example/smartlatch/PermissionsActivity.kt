package com.example.smartlatch

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartlatch.databinding.ActivityPermissionsBinding

class PermissionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermissionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        binding.permissionsRecyclerView.layoutManager = LinearLayoutManager(this)
        
        // Sample data - in a real app, this would come from a database or API
        val permissions = listOf(
            PermissionItem("Theus Mendez", "Whitelisted"),
            PermissionItem("Jelian Minoza", "Whitelisted"),
            PermissionItem("John Maligay", "Whitelisted"),
            PermissionItem("Jessa Mamalias", "Whitelisted"),
            PermissionItem("Mark Linganay", "Whitelisted"),
            PermissionItem("Anthony Peralta", "Whitelisted"),
            PermissionItem("Mae Rosios", "Whitelisted"),
            PermissionItem("Jacksepticeye", "Whitelisted"),
            PermissionItem("Mae Roios", "Blacklisted"),
            PermissionItem("Marc Perez", "Blacklisted"),
            PermissionItem("Nikka Sanchz", "Blacklisted"),
            PermissionItem("Hoshimachi Suisei", "Blacklisted"),
            PermissionItem("Shane Abarke", "Blacklisted")
        )
        
        val adapter = PermissionsAdapter(permissions) { permission, action ->
            when (action) {
                "edit" -> Toast.makeText(this, "Edit ${permission.name}", Toast.LENGTH_SHORT).show()
                "delete" -> Toast.makeText(this, "Delete ${permission.name}", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.permissionsRecyclerView.adapter = adapter
    }
    
    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
        
        binding.notificationBell.setOnClickListener {
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
        }
        
        binding.addPermissionFab.setOnClickListener {
            Toast.makeText(this, "Add new permission", Toast.LENGTH_SHORT).show()
        }
    }
}

data class PermissionItem(
    val name: String,
    val status: String
)

class PermissionsAdapter(
    private val permissions: List<PermissionItem>,
    private val onActionClick: (PermissionItem, String) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<PermissionsAdapter.PermissionViewHolder>() {

    class PermissionViewHolder(val binding: android.view.View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding)

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): PermissionViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return PermissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PermissionViewHolder, position: Int) {
        val permission = permissions[position]
        val textView = holder.binding.findViewById<android.widget.TextView>(android.R.id.text1)
        textView.text = "${permission.name} - ${permission.status}"
    }

    override fun getItemCount() = permissions.size
}
