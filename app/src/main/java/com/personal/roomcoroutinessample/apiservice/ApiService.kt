package com.personal.roomcoroutinessample.apiservice

import com.personal.roomcoroutinessample.entity.CityData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/data/2.5/weather")
    suspend fun getCity(@Query("lat") lat: String,@Query("lon")lon :String,@Query("appid")api_token :String):Response<CityData>

}