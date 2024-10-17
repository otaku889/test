package com.example.mvvmcountry.view

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.ui.text.toLowerCase

import androidx.lifecycle.Observer
import com.example.mvvmcountry.databinding.ActivityMainBinding
import com.example.mvvmcountry.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.countryCommonName.observe(this, Observer { commonNames ->
            // Update UI with data

            binding.apply {
                Searcher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(p0: String): Boolean {

                        return true
                    }

                    override fun onQueryTextChange(p0: String): Boolean {
                        viewModel.setSearchQuery(p0)
                        //Get Common Name for Searching
                        commonNames.forEach { commonName ->
                            Log.i("TAG", "Last: $commonName")

                            if (p0.lowercase() == commonName.lowercase()) {
                                textView.text = commonName

                                //Get Official Name
                                viewModel.countryOfficial.observe(
                                    this@MainActivity,
                                    Observer { officialName ->
                                        officialName.forEach {
                                            textView2.text =
                                                officialName[commonNames.indexOf(commonName)]
                                        }
                                    })


                            }


                        }

                        return true
                    }
                })

            }
        })
        //viewModel.data.observe(this, Observer { data ->
        //    // Update UI with data
        //})

        viewModel.fetchData()
    }
}



