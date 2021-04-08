package com.example.restro.networkClasses.retrofit

import com.example.restro.dashboard.dataModel.RestroModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitServiceAnnotator {

    @GET
    fun doGetRequest(@Url url : String) : Call<RestroModel>

}