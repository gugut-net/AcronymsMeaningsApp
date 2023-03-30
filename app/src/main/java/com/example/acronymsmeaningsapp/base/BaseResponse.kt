package com.example.acronymsmeaningsapp.base

import com.example.acronymsmeaningsapp.model.AcronymResponseItem

data class BaseResponse(
    val successResponse: List<AcronymResponseItem?>? = null,
    val errorResponse: String? = null
)