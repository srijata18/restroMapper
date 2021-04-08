package com.example.restro.dashboard.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.restro.view.base.BaseViewModel
import com.example.restro.networkClasses.ErrorModel
import com.example.restro.networkClasses.UseCase
import com.example.restro.networkClasses.UseCaseHandler
import com.example.restro.dashboard.dataModel.RestroModel
import com.example.restro.dashboard.useCase.RestroUseCase

class DashboardViewModel(
    override val useCaseHandler: UseCaseHandler,
    private val restroUseCase: RestroUseCase
) : BaseViewModel(useCaseHandler) {

    val receivedResponse = MutableLiveData<RestroModel>()
    val errorModel = MutableLiveData<ErrorModel>()

    fun fetchDetails(){
        val requestValue = RestroUseCase.RequestValues()
        useCaseHandler.execute(
            restroUseCase, requestValue,
            object : UseCase.UseCaseCallback<RestroUseCase.ResponseValue>{
                override fun onSuccess(response: RestroUseCase.ResponseValue) {
                    if (response.response is RestroModel) {
                        receivedResponse.value = response.response
                    }
                }

                override fun onError(t: String, code: Int?) {
                    errorModel.value = ErrorModel(t,code)
                }
            }
        )
    }
}