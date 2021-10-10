package com.wwt.nimbleviewing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.wwt.nimbleviewing.data.repository.MainRepository
import com.wwt.nimbleviewing.utils.Resource
import kotlinx.coroutines.Dispatchers

class ApiViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Please try again"))
        }
    }
}