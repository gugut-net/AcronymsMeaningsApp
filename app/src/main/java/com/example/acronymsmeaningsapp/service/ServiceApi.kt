package com.example.acronymsmeaningsapp.service

import com.example.acronymsmeaningsapp.model.AcronymResponse
import com.example.acronymsmeaningsapp.model.AcronymResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("dictionary.py")
    suspend fun getAbbreviationList(@Query("sf") typedText: String): Response<List<AcronymResponseItem?>>
}