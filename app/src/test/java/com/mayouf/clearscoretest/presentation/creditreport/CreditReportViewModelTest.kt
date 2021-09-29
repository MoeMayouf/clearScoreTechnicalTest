package com.mayouf.clearscoretest.presentation.creditreport

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mayouf.clearscoretest.domain.model.CreditReport
import com.mayouf.clearscoretest.domain.repository.CreditScoreRepository
import com.mayouf.clearscoretest.domain.usecase.creditreport.GetCreditReportUseCase
import com.mayouf.clearscoretest.domain.utils.ThreadScheduler
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CreditReportViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    lateinit var repo: CreditScoreRepository

    @Mock
    lateinit var mockLiveDataObserver: Observer<CreditReport>

    private var threadScheduler: ThreadScheduler = mock {
        on { io() } doReturn Schedulers.trampoline()
        on { main() } doReturn Schedulers.trampoline()
        on { computation() } doReturn Schedulers.trampoline()
    }

    @Before
    fun `set up`() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Given useCase returns data, when getCats() called, then update catsLiveData`() {
        val resp: CreditReport = mock()
        repo = mock {
            on { it.getCreditReport() } doReturn Single.just(resp)
        }
        val useCase = GetCreditReportUseCase(threadScheduler, repo)
        val myViewModel = CreditReportViewModel(Application(), useCase)
        myViewModel.creditLiveData.observeForever(mockLiveDataObserver)
        myViewModel.getCreditReport()
        Assert.assertEquals(resp, myViewModel.creditLiveData.value)
    }

    @Test
    fun `Given useCase returns error, when getCats() called, then do not change catsLiveData`() {
        //Setting how up the mock behaves
        repo = mock {
            on { it.getCreditReport() } doReturn Single.error(Exception())
        }
        val useCase = GetCreditReportUseCase(threadScheduler, repo)
        val myViewModel = CreditReportViewModel(Application(), useCase)
        myViewModel.creditLiveData.observeForever(mockLiveDataObserver)
        myViewModel.getCreditReport()
        verify(mockLiveDataObserver, times(0)).onChanged(any())
    }
}