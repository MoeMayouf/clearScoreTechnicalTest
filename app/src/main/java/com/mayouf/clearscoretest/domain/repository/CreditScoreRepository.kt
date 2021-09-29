package com.mayouf.clearscoretest.domain.repository

import com.mayouf.clearscoretest.domain.model.CreditReport
import io.reactivex.Single

interface CreditScoreRepository {
    fun getCreditReport(): Single<CreditReport>
}