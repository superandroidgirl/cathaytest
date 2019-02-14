package com.cathay.testproject.retrofit.apimodel.planetlist

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("offset")
    val offset: Int = 0,
    @SerializedName("results")
    val results: List<ResultX> = listOf(),
    @SerializedName("sort")
    val sort: String = ""
)