package com.example.to_dolistandroidapp

import Models.TaskModelItem
import Models.TaskPostModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.iterator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    lateinit var taskList : List<TaskModelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disableButtons()

        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(RetrofitInterface::class.java)
        showTasks(API)
        val loggedUserId = intent.getStringExtra("loggedUserId")
        println(loggedUserId)

        add.setOnClickListener {
            if (editText.text.toString() != "") {
                API.createTask(TaskPostModel(
                        editText.text.toString(),
                        LocalDateTime.now().toString(),
                        "614c93e3-a880-43cb-a2ac-d89105507922")
                ).enqueue(object : Callback<TaskPostModel>{
                    override fun onFailure(call: Call<TaskPostModel>, t: Throwable) {
                        Toast.makeText(
                                this@MainActivity,
                                t.message,
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<TaskPostModel>, response: Response<TaskPostModel>) {
                        val responseText = "Response code: ${response.code()}\n content: ${response.body()?.content}"
                        println(responseText)
                        if (response.code() == 201) {
                            Toast.makeText(this@MainActivity, "New Task added to list", Toast.LENGTH_SHORT)
                                    .show()
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT)
                                    .show()
                        }
                        showTasks(API)
                        editText.text.clear()
                        disableButtons()
                    }

                })
            } else {
                Toast.makeText(this@MainActivity, "Text field is empty", Toast.LENGTH_SHORT)
                        .show()
            }
        }

        edit.setOnClickListener {
            val position = listView.checkedItemPosition
            var task = taskList?.get(position)
            if (task != null) {
                var call = API.putTask(id = task.taskId.toString(),
                        taskModelItem = TaskPostModel(
                                content = editText.text.toString(),
                                createdDate = task.createdDate,
                                userId = task.userId
                        ))

                call.enqueue(object:retrofit2.Callback<Void>{
                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        println(t.message)
                        Toast.makeText(
                                this@MainActivity,
                                t.message,
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        println(response.body())
                        if (response.code() == 201) {
                            Toast.makeText(this@MainActivity, "Task is updated", Toast.LENGTH_SHORT)
                                    .show()
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT)
                                    .show()
                        }
                        showTasks(API)
                        editText.text.clear()
                        disableButtons()
                    }

                })
            }
        }

        clear.setOnClickListener {
            for(i in taskList?.iterator()) {
                var call = API.deleteTask(id = i.taskId.toString())

                call.enqueue(object:retrofit2.Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        println(t.message)
                        Toast.makeText(
                                this@MainActivity,
                                t.message,
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        println(response.body())
                        if (response.code() == 201) {
                            Toast.makeText(this@MainActivity, "Task is deleted", Toast.LENGTH_SHORT)
                                    .show()
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT)
                                    .show()
                        }
                        showTasks(API)
                    }
                })
            }
        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val position = listView.checkedItemPosition
            var taskContent = taskList?.get(position)?.content
            editText.setText(taskContent)
            checkTaskIsSelected()
        }
//
        delete.setOnClickListener {
            val position = listView.checkedItemPosition
            var task = taskList?.get(position)
            if (task != null) {
                var call = API.deleteTask(id = task.taskId.toString())

                call.enqueue(object:retrofit2.Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        println(t.message)
                        Toast.makeText(
                                this@MainActivity,
                                t.message,
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        println(response.body())
                        if (response.code() == 201) {
                            Toast.makeText(this@MainActivity, "Task is deleted", Toast.LENGTH_SHORT)
                                    .show()
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT)
                                    .show()
                        }
                        showTasks(API)
                        editText.text.clear()
                        disableButtons()
                    }

                })
            }
        }
    }

    fun showTasks(API: RetrofitInterface){
        var call = API.tasks
        call?.enqueue(object:retrofit2.Callback<List<TaskModelItem?>?>{
            override fun onFailure(call: retrofit2.Call<List<TaskModelItem?>?>, t: Throwable) {
                Toast.makeText(
                        this@MainActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                    call: retrofit2.Call<List<TaskModelItem?>?>,
                    response: retrofit2.Response<List<TaskModelItem?>?>
            ) {
                val tempList = response.body()
                if (tempList != null) {
                    taskList = tempList as List<TaskModelItem>
                    var task = arrayOfNulls<String>(taskList!!.size)

                    for (i in taskList!!.indices)
                        task[i] = taskList!![i]!!.content

                    var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_single_choice
                        , task)
                    listView.adapter = adapter

                    if (response.code() == 200) {
                        Toast.makeText(this@MainActivity, "Success!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else{
                        Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
//                taskList = response.body() as List<TaskModelItem>

            }

        })
    }

    fun checkTaskIsSelected() {
        for (i in taskList!!.indices) {
            if (listView.isItemChecked(i)) {
                edit.isEnabled = true
                delete.isEnabled = true
                break
            }
        }
    }

    fun disableButtons() {
        edit.isEnabled = false
        delete.isEnabled = false
    }
}