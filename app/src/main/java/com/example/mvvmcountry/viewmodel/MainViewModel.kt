package com.example.mvvmcountry.viewmodel

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmcountry.model.CountryData
import com.example.mvvmcountry.model.DataModel
import com.example.mvvmcountry.model.ImpCountryRepo
import com.example.mvvmcountry.model.Name
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel

class MainViewModel @Inject constructor(private val repository: ImpCountryRepo) : ViewModel() {
    //Common Name
    private val _data = MutableLiveData<List<String>>()
    val countryCommonName: LiveData<List<String>> get() = _data
    //Official Name
    private val _official = MutableLiveData<List<String>>()
    val countryOfficial: LiveData<List<String>> get() = _official

    val countryDetailsList = mutableListOf<CountryData>()
    private var searchQuery: String? = null

    fun fetchData() {

        viewModelScope.launch {
            val response = repository.getData()
            Log.i("TAG", response.toString())

            if (response.isSuccessful) {
                response.body()?.let { dataList ->
                    for (i in dataList.indices) {
                        val dataModel = dataList[i]
                        val countryDetails = CountryData(
                            dataModel.name.common,
                            dataModel.name.official
                        )
                        countryDetailsList.add(countryDetails)
                    }
                    // Map the list of CountryData to a list of common names and post it
                    _data.postValue(countryDetailsList.map { it.names})
                    _official.postValue(countryDetailsList.map { it.officials})
                } ?: run {
                    Log.e("TAG", "Response body is null")
                }
            } else {
                Log.e("TAG", "Response is not successful: ${response.errorBody()}")
            }
        }
    }
    // Function to set search query
    fun setSearchQuery(data: String) {
        searchQuery = data
        Log.i("TAG", "Search Query: $searchQuery")
    }
}

/*class MainViewModel @Inject constructor(private val repository: ImpCountryRepo) : ViewModel() {

    private val _data = MutableLiveData<List<String>>()
    val countryCommonName: LiveData<List<String>> get() = _data
    val namesList = mutableListOf<String>()
    private var searchQuery: String? = null

    fun fetchData() {

        viewModelScope.launch {
            val response = repository.getData()
            Log.i("TAG", response.toString())

            if (response.isSuccessful) {

                //CommonName
                response.body()?.let { dataList ->
                    for (i in dataList.indices) {
                        val dataModel = dataList[i]
                        namesList.add(dataModel.name.common)
                        namesList.add(dataModel.name.official)

                    }
                    _data.postValue(namesList)
                } ?: run {
                    Log.e("TAG", "Response body is null")
                }


            } else {
                Log.e("TAG", "Response is not successful: ${response.errorBody()}")
            }

        }
    }

    // Function to set search query
    fun setSearchQuery(data: String) {
        searchQuery = data
        Log.i("TAG", "Search Query: $searchQuery")
    }
    *//*fun fetchData() {

        viewModelScope.launch {
            val response = repository.getData()
            Log.i("TAG", response.toString())
            if (response.isSuccessful) {
                // Map the list of DataModel to a list of names and post it

             response.body()?.forEach { dataModel ->

                }
                _data.postValue(response.body()?.map { it.name.common })
            } else {
                Log.e("TAG", "Response is not successful: ${response.errorBody()}")
            }
        }
    }*//*

}*/


