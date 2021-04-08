package com.example.restro.dashboard.dataRepositories

import com.example.restro.dashboard.useCase.IDataSource

class DashboardDataRepository (val remoteRepository: DashboardRemoteRepository,
                               val localRepository: DashboardLocalRepository
) : IDataSource {

    override fun getVariantDetails(callBack: IDataSource.getDetails) {

        remoteRepository.getVariantDetails(object :
            IDataSource.getDetails{
            override fun onPostSuccess(response: Any) {
                callBack.onPostSuccess(response)
            }

            override fun onPostFailure(errorMsg: String, code: Int?) {
                callBack.onPostFailure(errorMsg, code)
            }

        })
    }

}