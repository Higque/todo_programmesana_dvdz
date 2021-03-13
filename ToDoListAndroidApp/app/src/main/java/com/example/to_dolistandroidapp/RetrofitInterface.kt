package com.example.to_dolistandroidapp

import Models.SignInBody
import Models.TaskModelItem
import Models.TaskPostModel
import Models.UserBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitInterface {

    @get:GET("tasks")
    val tasks : Call<List<TaskModelItem?>?>?

    @GET("users/{id}/tasks")
    fun getUserTasks(@Path("id") id: String): Call<List<TaskModelItem?>?>?

    @PUT("tasks/{id}")
    fun putTask(@Path("id") id: String, @Body taskModelItem: TaskPostModel): Call<Void>

    @DELETE("tasks/{id}")
    fun deleteTask(@Path("id") id: String): Call<Void>

    @POST("tasks")
    fun createTask(@Body task: TaskPostModel
    ): Call<TaskPostModel>

    companion object {
        const val BASE_URL = "http://10.0.2.2:5000/api/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Headers("Content-Type:application/json")
    @POST("users/login")
    fun signInUser(@Body info: SignInBody, @Query("email") email: String,
                   @Query("password") password: String): retrofit2.Call<UserBody>
}