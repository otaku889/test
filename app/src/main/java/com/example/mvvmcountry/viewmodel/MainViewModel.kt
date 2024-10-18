package com.example.mvvmcountry.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.mvvmcountry.DI.ImpCountryRepo
import com.example.mvvmcountry.model.CombinedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ImpCountryRepo) : ViewModel() {

    private val _combinedData = MediatorLiveData<CombinedData>()

    //viewModel.combinedData to call
    val combinedData: LiveData<CombinedData> get() = _combinedData
    //Common Name
    private val _countryCommonName = MutableLiveData<List<String>>()
    private val commonNames = ArrayList<String>()

    //Official Name
    private val _officialName = MutableLiveData<List<String>>()
    private val officialNames = ArrayList<String>()

    //Flag Url
    private val _countryFlag = MutableLiveData<List<String>>()
    private val flagURLs = ArrayList<String>()

    //Capital
    private val _capitalName = MutableLiveData<List<String>>()
    private val capital = ArrayList<String>()


    //FOR MY MEDIATORLIST
    init {
        _combinedData.addSource(_countryCommonName) { updateCombinedData() }
        _combinedData.addSource(_officialName) { updateCombinedData() }
        _combinedData.addSource(_countryFlag) { updateCombinedData() }
        _combinedData.addSource(_capitalName) { updateCombinedData() }
    }

    private fun updateCombinedData() {
        val commonNames = _countryCommonName.value ?: emptyList()
        val officialNames = _officialName.value ?: emptyList()
        val flagURLs = _countryFlag.value ?: emptyList()
        val capitalName = _capitalName.value ?: emptyList()
        _combinedData.value = CombinedData(commonNames, officialNames, flagURLs, capitalName)
    }

    fun fetchData() {
        viewModelScope.launch {
            val response = repository.getData()
            if (response.isSuccessful) {
                response.body()?.let { dataList ->
                    dataList.forEach { dataModel ->
                        commonNames.add(dataModel.name.common)
                        _countryCommonName.value = commonNames

                        officialNames.add(dataModel.name.official)
                        _officialName.value = officialNames

                        flagURLs.add(dataModel.flags.png)
                        _countryFlag.value = flagURLs

                        capital.add(dataModel.capital.toString())
                        _capitalName.value = capital

                    }
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
