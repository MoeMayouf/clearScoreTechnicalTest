package com.mayouf.clearscoretest.presentation.creditreport

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mayouf.clearscoretest.domain.model.CreditReport
import com.mayouf.clearscoretest.domain.usecase.creditreport.GetCreditReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreditReportViewModel @Inject constructor(
    application: Application,
    private val getCreditReportUseCase: GetCreditReportUseCase
) : AndroidViewModel(application) {

    val loadingLiveData = MutableLiveData<Boolean>()
    val creditLiveData = MutableLiveData<CreditReport>()
    val errorLiveData = MutableLiveData<Throwable>()

    fun getCreditReport() {
        getCreditReportUseCase.execute(
            onLoading = { loadingLiveData.postValue(true) },
            onSuccess = {
                loadingLiveData.postValue(false)
                creditLiveData.value = it
            },
            onError = { errorLiveData.value = it }
        )
    }
}