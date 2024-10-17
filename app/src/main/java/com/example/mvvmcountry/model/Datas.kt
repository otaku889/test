package com.example.mvvmcountry.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataModel(
   @SerializedName("name")
   val name: Name,
   @SerializedName("flags")
   val flags: Flags,
    @SerializedName("region")
    val region :String,
    @SerializedName("subregion")
    val subregion :String,
    @SerializedName("capital")
    val capital: List<String>?,
   @SerializedName("currencies")
   val currencies: Map<String, Currency>,
    @SerializedName("cca2")
    val countryCode : String,
    @SerializedName("timezones")
    val countryTimeZone :List<String>,
    @SerializedName("languages")
    val language : Map<String, String>?,
    @SerializedName("population")
    val population: String
)

//CHILD ABUSE
data class Name(
    @SerializedName("common")
    val common: String,

    @SerializedName("official")
    val official: String

)

data class Flags(
    @SerializedName("png")
    val png: String,
    @SerializedName("svg")
    val svg: String
)

data class Currency(
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)

//Getting data for Recycler View
data class CountryData(
    val names: String,
    val officials: String
    //val flag: String,
    //val cca2: String,
    //val timeZone: String,
    //val population: String,
    //val capital: List<String>?,
    //val region: String,
    //val subregions: String?,
    //val language: String,
    //val currencies: String



)