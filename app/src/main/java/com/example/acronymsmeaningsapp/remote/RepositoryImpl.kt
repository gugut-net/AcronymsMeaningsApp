package com.example.acronymsmeaningsapp.remote

import com.example.acronymsmeaningsapp.model.AcronymResponse
import com.example.acronymsmeaningsapp.model.AcronymResponseItem
import com.example.acronymsmeaningsapp.service.ServiceApi
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val serviceApi: ServiceApi
): Repository {
    override suspend fun getAbbreviationList(typedText: String): Response<List<AcronymResponseItem?>> {
        return serviceApi.getAbbreviationList(typedText)
    }
}