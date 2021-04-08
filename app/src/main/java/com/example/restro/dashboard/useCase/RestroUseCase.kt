package com.example.restro.dashboard.useCase

import com.example.restro.networkClasses.UseCase
import com.example.restro.dashboard.dataRepositories.DashboardDataRepository

class RestroUseCase(private val dataRepository: DashboardDataRepository) :
    UseCase<RestroUseCase.RequestValues, RestroUseCase.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        dataRepository.getVariantDetails(
            object :
                IDataSource.getDetails {
                override fun onPostSuccess(response: Any) {
                    val res =
                        ResponseValue(
                            response
                        )
                    useCaseCallback?.onSuccess(res)
                }

                override fun onPostFailure(errorMsg: String, code: Int?) {
                    useCaseCallback?.onError(errorMsg, code)
                }
            }
        )
    }

    class RequestValues :
        UseCase.RequestValues

    class ResponseValue(val response: Any) : UseCase.ResponseValue

}