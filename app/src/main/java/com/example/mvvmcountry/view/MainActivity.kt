package com.example.mvvmcountry.view

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmcountry.databinding.ActivityMainBinding
import com.example.mvvmcountry.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        viewModel.fetchData()
    }

    private fun setupRecyclerView() {
        binding.Recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.combinedData.observe(this) { (commonNames, officialNames, flagURLs) ->
            val adapter = CountryAdapter(commonNames, officialNames, flagURLs)
            binding.Recycler.adapter = adapter
        }
    }
}
