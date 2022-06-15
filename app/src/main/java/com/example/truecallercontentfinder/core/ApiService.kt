package com.example.truecallercontentfinder.core

import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("2018/01/22/life-as-an-android-engineer/")
    suspend fun fetchContent(): Response<String>

}