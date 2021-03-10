package com.example.to_dolistandroidapp

import Models.TaskModelItem
import Models.TaskPostModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitInterface {

    @get:GET("tasks")
    val tasks : Call<List<TaskModelItem?>?>?

//    @FormUrlEncoded
    @POST("tasks")
    fun createTask(@Body task: TaskPostModel
    ): Call<TaskPostModel>

    companion object {
        const val BASE_URL = "http://10.0.2.2:5000/api/"
    }
}