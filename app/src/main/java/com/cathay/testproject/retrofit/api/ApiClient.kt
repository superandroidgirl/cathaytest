package com.cathay.testproject.retrofit.api

import com.cathay.testproject.retrofit.apimodel.categorylocation.CategoryListRoot
import com.cathay.testproject.retrofit.apimodel.planetlist.PlanetRoot
import io.reactivex.Observable

class ApiClient {

    companion object {

        /**
         * 取得館區列表
         */
        fun getCategoryList(): Observable<CategoryListRoot> {
            return ServiceFactory.pushDomainService()
                .getLocationList("resourceAquire", "5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
                .compose(ObservableUtils.schedulersHandling())
        }

        /**
         * 取得植物列表
         */
        fun getPlanetList(categoryName : String): Observable<PlanetRoot> {
            return ServiceFactory.pushDomainService()
                .getPlanetLocation("resourceAquire", "f18de02f-b6c9-47c0-8cda-50efad621c14", categoryName)
                .compose(ObservableUtils.schedulersHandling())
        }
    }
}