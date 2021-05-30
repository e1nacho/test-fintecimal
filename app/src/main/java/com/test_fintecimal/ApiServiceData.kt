package com.test_fintecimal

import com.google.gson.annotations.SerializedName

data class ApiServiceData(val streetName: String, val suburb: String, val visited: Boolean, val location: String)
//data class ApiServiceData(@SerializedName("streetName") var streetName: String,
//                          @SerializedName("suburb") var suburb: String,
//                          @SerializedName("visited") var visited: String,
//                          @SerializedName("location") var location: List<String>)