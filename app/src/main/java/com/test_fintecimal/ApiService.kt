package com.test_fintecimal

import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("https://fintecimal-test.herokuapp.com/api/interview/")
    open fun getLocations(): Call<List<ApiServiceData>>
}