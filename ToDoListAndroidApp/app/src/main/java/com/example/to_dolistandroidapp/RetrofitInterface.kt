package com.example.to_dolistandroidapp

import Models.Task
import Models.TaskModelItem
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {

    @get:GET("tasks")
    val tasks : Call<List<TaskModelItem?>?>?

    companion object {
        const val BASE_URL = "http://10.0.2.2:5000/api/"
    }
}