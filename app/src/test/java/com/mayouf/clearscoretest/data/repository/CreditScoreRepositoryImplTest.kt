package com.mayouf.clearscoretest.data.repository

import com.mayouf.clearscoretest.data.source.RetrofitServiceApi
import com.mayouf.clearscoretest.domain.model.CreditReport
import com.mayouf.clearscoretest.domain.repository.CreditScoreRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Test

class CreditScoreRepositoryImplTest {
    lateinit var sut: CreditScoreRepository
    lateinit var api: RetrofitServiceApi

    @Test
    fun `get cat`() {
        val resp: CreditReport = mock()

        api = mock {
            on { it.getCreditReport() } doReturn Single.just(resp)
        }

        sut = CreditScoreRepositoryImpl(api)

        val testObserver = sut.getCreditReport().test()

        testObserver.assertNoErrors()
        testObserver.assertValue { it == resp }
        testObserver.assertComplete()

        verify(api).getCreditReport()
    }
}