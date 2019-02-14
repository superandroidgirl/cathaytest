package com.cathay.testproject.retrofit.apimodel.categorylocation

data class Result(
    val count: Int = 0,
    val limit: Int = 0,
    val offset: Int = 0,
    val results: List<CategoryListResultX> = listOf(),
    val sort: String = ""
)