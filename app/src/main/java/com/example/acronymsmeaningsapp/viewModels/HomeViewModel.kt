package com.example.acronymsmeaningsapp.viewModels

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acronymsmeaningsapp.base.BaseResponse
import com.example.acronymsmeaningsapp.base.LiveDataResource
import com.example.acronymsmeaningsapp.remote.Repository
import com.example.acronymsmeaningsapp.utils.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    val typedText = MutableLiveData<String>()
    private val _resultData = MutableLiveData<LiveDataResource<BaseResponse>>()
    val weatherData: LiveData<LiveDataResource<BaseResponse>> = _resultData

    fun getAcronymResults() {
        if (!CommonUtils.isOnline(connectivityManager)) return
        if (typedText.value.isNullOrEmpty()) return // checking typed text is empty or not
        viewModelScope.launch {
            _resultData.value = LiveDataResource.loading(null)
            val response1 = withContext(Dispatchers.IO) {
                repository.getAbbreviationList(typedText.value.toString())
            }
            if (response1.isSuccessful) {
                response1.body()?.let { _resultData.value = LiveDataResource.success(BaseResponse(it, null)) }
            } else {
                response1.errorBody()?.let { _resultData.value = LiveDataResource.error(BaseResponse(null, it.toString()))}
            }
        }
    }
}

//@HiltViewModel
//class HomeViewModel @Inject constructor(
//    private val repository: Repository,
//    private val connectivityManager: ConnectivityManager
//) : ViewModel() {
//
//    val typedText = MutableLiveData<String>()
//
//    private val _resultData: MutableLiveData<LiveDataResource<BaseResponse>> =
//        MutableLiveData()
//    val weatherData = _resultData
//
//    fun getAcronymResults() {
//        if (!CommonUtils.isOnline(connectivityManager)) return
//        if (typedText.value.toString().isEmpty()) return  //checking internet connection
//        viewModelScope.launch {
//            _resultData.value = LiveDataResource.loading(null)
//            val response1 = withContext(Dispatchers.IO) {
//                repository.getAbbreviationList(typedText.value.toString())
//            }
//            if (response1.isSuccessful) {
//                response1.body()?.let { _resultData.value = LiveDataResource.success(BaseResponse(it, null)) }
//            } else {
//                response1.errorBody()?.let { _resultData.value = LiveDataResource.error(BaseResponse(null, it.toString()))}
//            }
//        }
//    }
//}

