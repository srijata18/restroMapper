package com.example.restro.dashboard.useCase

interface IDataSource{
    interface getDetails{
        fun onPostSuccess(response: Any)
        fun onPostFailure(errorMsg: String, code: Int? = 0)
    }
    fun getVariantDetails(callBack: getDetails)
}