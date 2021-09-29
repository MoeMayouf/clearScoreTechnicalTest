package com.mayouf.clearscoretest.data.source

import com.mayouf.clearscoretest.domain.model.CreditReport
import io.reactivex.Single
import retrofit2.http.GET

interface RetrofitServiceApi {

    @GET("endpoint.json")
    fun getCreditReport() : Single<CreditReport>
}