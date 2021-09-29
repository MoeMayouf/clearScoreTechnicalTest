package com.mayouf.clearscoretest.data.repository

import com.mayouf.clearscoretest.data.source.RetrofitServiceApi
import com.mayouf.clearscoretest.domain.model.CreditReport
import com.mayouf.clearscoretest.domain.repository.CreditScoreRepository
import io.reactivex.Single

class CreditScoreRepositoryImpl(private val retrofitServiceApi: RetrofitServiceApi) : CreditScoreRepository {
    override fun getCreditReport(): Single<CreditReport> = retrofitServiceApi.getCreditReport()
}