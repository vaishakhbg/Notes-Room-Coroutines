package com.personal.roomcoroutinessample.repository

import android.app.Application
import android.util.Log
import com.personal.roomcoroutinessample.apiservice.ApiClient
import com.personal.roomcoroutinessample.apiservice.ApiService
import com.personal.roomcoroutinessample.dao.MessageDao
import com.personal.roomcoroutinessample.database.AppDatabase
import com.personal.roomcoroutinessample.entity.CityData
import com.personal.roomcoroutinessample.entity.Message
import kotlinx.coroutines.*
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class MessageRespository(application: Application): CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    private  var messageDao: MessageDao?
    private var apiService: ApiService?

    init {
        val db = AppDatabase.getAppDataBase(application)
        messageDao = db?.messageDao()
        apiService = ApiClient.getClient()
    }

    fun getMessages() : List<Message>? {

        return messageDao?.getMessages()

    }

    fun setMessage(message:Message){
        launch {setMessageInBackGround(message)}
    }

    fun updateMessage(message : Message){
        launch {updateMessageInBackGround(message)}
    }

    fun deleteMessage(message: Message){
        messageDao?.deleteMessage(message)

    }

    private suspend fun updateMessageInBackGround(message: Message) {
        withContext(Dispatchers.IO){
            messageDao?.updateMessage(message)
        }
    }

    private suspend fun setMessageInBackGround(message: Message) {
        withContext(Dispatchers.IO){
            messageDao?.insertMessage(message)
        }
    }

    suspend fun  getCity(lat: String,lon : String):Response<CityData>?{
        //launch{getCityInBackGround(lat,lon,"aa9a27e3bbe36c45d90d7851c687d904")}
        val cityName = async { getCityInBackGround(lat,lon,ApiKeyConstant.API_KEY) }
        val cc = cityName.await()
        return cc
    }

    private suspend fun getCityInBackGround(lat: String, lon: String, s: String) : Response<CityData>?{
        var result:Response<CityData>? = null
        withContext(Dispatchers.IO){
            result =apiService?.getCity(lat,lon,s)
        }
        return result
    }


}

