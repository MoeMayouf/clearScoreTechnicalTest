package com.mayouf.clearscoretest.domain.usecase.creditreport

import com.mayouf.clearscoretest.domain.model.CreditReport
import com.mayouf.clearscoretest.domain.repository.CreditScoreRepository
import com.mayouf.clearscoretest.domain.usecase.base.SingleUseCase
import com.mayouf.clearscoretest.domain.utils.ThreadScheduler
import io.reactivex.Single
import javax.inject.Inject

class GetCreditReportUseCase @Inject constructor(
    threadScheduler: ThreadScheduler,
    private val repository: CreditScoreRepository
) : SingleUseCase<CreditReport>(threadScheduler) {

    override fun createSingleUseCase(): Single<CreditReport> = repository.getCreditReport()
}