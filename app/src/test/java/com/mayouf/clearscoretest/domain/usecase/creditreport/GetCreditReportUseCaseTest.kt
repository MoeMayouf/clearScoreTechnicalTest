package com.mayouf.clearscoretest.domain.usecase.creditreport

import com.mayouf.clearscoretest.domain.model.CreditReport
import com.mayouf.clearscoretest.domain.repository.CreditScoreRepository
import com.mayouf.clearscoretest.domain.utils.ThreadScheduler
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class GetCreditReportUseCaseTest {
    private lateinit var sut: GetCreditReportUseCase
    private lateinit var repository: CreditScoreRepository

    private var threadScheduler: ThreadScheduler = mock {
        on { io() } doReturn Schedulers.trampoline()
        on { main() } doReturn Schedulers.trampoline()
        on { computation() } doReturn Schedulers.trampoline()
    }

    @Test
    fun `verify interactions in success case`() {
        val resp: CreditReport = mock()

        repository = mock {
            on { getCreditReport() } doReturn Single.just(resp)
        }

        sut = GetCreditReportUseCase(threadScheduler, repository)

        val testObserver = sut.execute(onLoading = {}, onSuccess = {}, onError = {}).test()

        testObserver.assertNoErrors()
        verify(repository, times(2)).getCreditReport()
    }

    @Test
    fun `verify interactions in failure case`() {

        repository = mock {
            on { getCreditReport() } doReturn Single.error(Exception())
        }

        sut = GetCreditReportUseCase(threadScheduler, repository)

        val testObserver = sut.execute(onLoading = {}, onSuccess = {}, onError = {}).test()

        testObserver.assertError(Exception::class.java)
        verify(repository, times(2)).getCreditReport()
    }
}