package com.momo.mobile.shoppingv2.android.retrofit.api

import com.cathay.testproject.retrofit.apimodel.categorylocation.CategoryListRoot
import com.cathay.testproject.retrofit.apimodel.planetlist.PlanetRoot
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by iristai on 2019/02/06.
 */
interface DataApiService {

    @GET("apiAccess")
    fun getLocationList(@Query("scope") scope: String,
                        @Query("rid") rid: String): Observable<CategoryListRoot>

    @GET("apiAccess")
    fun getPlanetLocation(@Query("scope") scope: String,
                          @Query("rid") rid: String,
                          @Query("q") queryString: String): Observable<PlanetRoot>


}