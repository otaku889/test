package com.example.mvvmcountry.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.mvvmcountry.model.CountryData
import com.example.mvvmcountry.model.ImpCountryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ImpCountryRepo) : ViewModel() {

    private val _countryCommonName = MutableLiveData<List<String>>()
    val countryCommonName: LiveData<List<String>> get() = _countryCommonName

    private val _countryOfficial = MutableLiveData<List<String>>()
    val countryOfficial: LiveData<List<String>> get() = _countryOfficial

    private val _countryFlag = MutableLiveData<List<String>>()
    val countryFlag: LiveData<List<String>> get() = _countryFlag

    private val _combinedData = MediatorLiveData<Triple<List<String>, List<String>, List<String>>>()
    val combinedData: LiveData<Triple<List<String>, List<String>, List<String>>> get() = _combinedData

    private val countryDetailsList = mutableListOf<CountryData>()

    init {
        _combinedData.addSource(_countryCommonName) { updateCombinedData() }
        _combinedData.addSource(_countryOfficial) { updateCombinedData() }
        _combinedData.addSource(_countryFlag) { updateCombinedData() }
    }

    private fun updateCombinedData() {
        val commonNames = _countryCommonName.value ?: emptyList()
        val officialNames = _countryOfficial.value ?: emptyList()
        val flagURLs = _countryFlag.value ?: emptyList()
        _combinedData.value = Triple(commonNames, officialNames, flagURLs)
    }

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
                                dataModel.flags.png
                            )
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
        Log.i("TAG", "Search Query: $query")
    }
}
