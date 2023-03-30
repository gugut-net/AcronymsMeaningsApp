package com.example.acronymsmeaningsapp.model

import com.google.gson.annotations.SerializedName

data class AcronymResponse(

    @field:SerializedName("AcronymResponse")
    val acronymResponse: List<AcronymResponseItem?>? = null
)

data class VarsItem(

    @field:SerializedName("freq")
    val freq: Int? = null,

    @field:SerializedName("lf")
    val lf: String? = null,

    @field:SerializedName("since")
    val since: Int? = null
)

data class AcronymResponseItem(

    @field:SerializedName("sf")
    val sf: String? = null,

    @field:SerializedName("lfs")
    val lfs: List<LfsItem>? = null
)

data class LfsItem(

    @field:SerializedName("freq")
    val freq: Int? = null,

    @field:SerializedName("lf")
    val lf: String? = null,

    @field:SerializedName("vars")
    val vars: List<VarsItem?>? = null,

    @field:SerializedName("since")
    val since: Int? = null
)
