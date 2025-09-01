package com.example.smartlatch

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartlatch.databinding.ActivityRequestsBinding

class RequestsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRequestsBinding.inflate(layoutInflater)
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
        binding.requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        
        // Sample data - in a real app, this would come from a database or API
        val requests = listOf(
            RequestItem("Theus Mendez", "18 hours ago"),
            RequestItem("Jelian Minoza", "2 days ago"),
            RequestItem("John Maligay", "1 week ago"),
            RequestItem("Jessa Mamalias", "2 weeks ago"),
            RequestItem("Mark Linganay", "3 weeks ago"),
            RequestItem("Anthony Peralta", "1 month ago"),
            RequestItem("Mae Rosios", "2 months ago"),
            RequestItem("Marc Perez", "2 months ago"),
            RequestItem("Nikka Sanchz", "2 months ago"),
            RequestItem("Sakura Miko", "2 months ago"),
            RequestItem("Jacksepticeye", "2 months ago")
        )
        
        val adapter = RequestsAdapter(requests) { request, action ->
            when (action) {
                "approve" -> Toast.makeText(this, "Approved ${request.name}", Toast.LENGTH_SHORT).show()
                "deny" -> Toast.makeText(this, "Denied ${request.name}", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.requestsRecyclerView.adapter = adapter
    }
    
    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
        
        binding.notificationBell.setOnClickListener {
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
        }
    }
}

data class RequestItem(
    val name: String,
    val time: String
)

class RequestsAdapter(
    private val requests: List<RequestItem>,
    private val onActionClick: (RequestItem, String) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {

    class RequestViewHolder(val binding: android.view.View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding)

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): RequestViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        val textView = holder.binding.findViewById<android.widget.TextView>(android.R.id.text1)
        textView.text = "${request.name} - ${request.time}"
    }

    override fun getItemCount() = requests.size
}
