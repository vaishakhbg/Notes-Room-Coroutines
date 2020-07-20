package com.personal.roomcoroutinessample.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.personal.roomcoroutinessample.Status
import com.personal.roomcoroutinessample.entity.Message
import com.personal.roomcoroutinessample.repository.MessageRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(application: Application) : AndroidViewModel(application){


    val  _allMessageList = MutableLiveData<MutableList<Message>>()
    val allMessageList:LiveData<MutableList<Message>> = _allMessageList

    val  _editMessage = MutableLiveData<Message>()
    val editMessage:LiveData<Message> = _editMessage

    private val _isLoading = MutableLiveData<Int>()
    val isLoading : LiveData<Int>
        get()=_isLoading


    private var messageRespository:MessageRespository= MessageRespository(application)


    fun processData(data : String,lat: String,lon: String){
        runBlocking {
            val cityName = messageRespository.getCity(lat, lon)

        }
    }

    suspend fun  getCityFromCoordinates(lat: String,lon: String) : String?{
        /*runBlocking {
            val cityNameResponse = messageRespository.getCity(lat, lon)
            val cityData = cityNameResponse?.body()
            val cityName = cityData?.name
            return@runBlocking cityName
        }
        return null*/
        val cityNameResponse = messageRespository.getCity(lat,lon)
        return cityNameResponse!!.body()!!.name
    }

    fun setMessage(message: Message){
        runBlocking {
            messageRespository.setMessage(message)
            getAllMessages()
        }

    }

    fun updateMessage(message: Message){
        runBlocking {
            messageRespository.updateMessage(message)
            getAllMessages()
        }

    }

    fun getAllMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            val allMessages = messageRespository.getMessages()
            if(!allMessages.isNullOrEmpty()){
                _allMessageList.postValue(allMessages.toMutableList())
            }

        }
    }




    /*private val _status= MutableLiveData<Status>()
    val status: LiveData<Status> = _status*/



}