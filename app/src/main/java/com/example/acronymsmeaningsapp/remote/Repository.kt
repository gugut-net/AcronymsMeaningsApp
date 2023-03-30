package com.example.acronymsmeaningsapp.remote

import com.example.acronymsmeaningsapp.model.AcronymResponse
import com.example.acronymsmeaningsapp.model.AcronymResponseItem
import retrofit2.Response

interface Repository {

    suspend fun getAbbreviationList(typedText: String): Response<List<AcronymResponseItem?>>

}