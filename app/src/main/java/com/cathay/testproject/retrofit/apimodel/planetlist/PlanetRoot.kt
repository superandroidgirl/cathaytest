package com.cathay.testproject.retrofit.apimodel.planetlist

import com.google.gson.annotations.SerializedName

data class PlanetRoot(
    @SerializedName("result")
    val result: Result = Result()
)