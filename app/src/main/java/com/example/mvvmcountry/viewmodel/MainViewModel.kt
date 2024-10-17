package com.example.mvvmcountry.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmcountry.model.CountryData
import com.example.mvvmcountry.model.ImpCountryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ImpCountryRepo) : ViewModel() {

    //Common Name
    private val _countryCommonName = MutableLiveData<List<String>>()
    val countryCommonName: LiveData<List<String>> get() = _countryCommonName

    //Official Name
    private val _countryOfficial = MutableLiveData<List<String>>()
    val countryOfficial: LiveData<List<String>> get() = _countryOfficial

    //Country Flag
    private val _countryFlag = MutableLiveData<List<String>>()
    val countryFlag: LiveData<List<String>> get() = _countryFlag

    //Combine All lists
    private val _combinedData = MediatorLiveData<Triple<List<String>, List<String>, List<String>>>()
    val combinedData: LiveData<Triple<List<String>, List<String>, List<String>>> get() = _combinedData

    private val countryDetailsList = mutableListOf<CountryData>()
    private var searchQuery: String? = null

    fun fetchData() {
        viewModelScope.launch {
            val response = repository.getData()
            if (response.isSuccessful) {
                response.body()?.let { dataList ->
                    countryDetailsList.clear()
                    dataList.forEach { dataModel ->
                        countryDetailsList.add(
                            CountryData(
                                dataModel.name.common,
                                dataModel.name.official,
                                dataModel.flags.png)
                        )
                    }
                    _countryCommonName.postValue(countryDetailsList.map { it.names })
                    _countryOfficial.postValue(countryDetailsList.map { it.officials })
                    _countryFlag.postValue(countryDetailsList.map { it.flag })
                } ?: Log.e("TAG", "Response body is null")
            } else {
                Log.e("TAG", "Response is not successful: ${response.errorBody()}")
            }
        }
    }

    fun setSearchQuery(query: String) {
        searchQuery = query
        Log.i("TAG", "Search Query: $searchQuery")
    }
}
