package com.example.smartlatch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartlatch.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private val originalHistoryList = mutableListOf<HistoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupSearchFunctionality()
        setupClickListeners()
        loadHistoryData()
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter()
        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyAdapter
        }
    }

    private fun setupSearchFunctionality() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterHistory(s.toString())
            }
        })
    }

    private fun setupClickListeners() {
        // Bottom navigation click listeners
        binding.bottomNavigation.getChildAt(0).setOnClickListener {
            // TODO: Navigate to Chip screen
        }

        binding.bottomNavigation.getChildAt(1).setOnClickListener {
            // Already on History screen
        }

        binding.bottomNavigation.getChildAt(2).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        binding.bottomNavigation.getChildAt(3).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        binding.bottomNavigation.getChildAt(4).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }
    }

    private fun loadHistoryData() {
        originalHistoryList.clear()
        originalHistoryList.addAll(listOf(
            HistoryItem("Failed Entry Attempt • Front Door", "Yesterday"),
            HistoryItem("Successful Entry • Front Door", "2 days ago"),
            HistoryItem("Entry Request • Front Door", "2 days ago"),
            HistoryItem("Forced Entry • Front Door", "6 days ago"),
            HistoryItem("Successful Entry • Front Door", "1 week ago"),
            HistoryItem("Successful Entry • Front Door", "1 week ago"),
            HistoryItem("Approved Entry • Front Door", "1 week ago"),
            HistoryItem("Successful Entry • Front Door", "1 week ago"),
            HistoryItem("Failed Entry Attempt • Front Door", "1 week ago")
        ))
        historyAdapter.updateHistory(originalHistoryList)
    }

    private fun filterHistory(query: String) {
        if (query.isEmpty()) {
            historyAdapter.updateHistory(originalHistoryList)
        } else {
            val filteredList = originalHistoryList.filter { item ->
                item.title.contains(query, ignoreCase = true) ||
                item.timestamp.contains(query, ignoreCase = true)
            }
            historyAdapter.updateHistory(filteredList)
        }
    }
}
