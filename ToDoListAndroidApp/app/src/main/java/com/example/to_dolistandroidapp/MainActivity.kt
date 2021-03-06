package com.example.to_dolistandroidapp

import Models.TaskModelItem
import Models.TaskPostModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    lateinit var taskList : List<TaskModelItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disableButtons()

        val rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val API = rf.create(RetrofitInterface::class.java)
//        showTasks(API)
        val loggedUserId = intent.getStringExtra("loggedUserId")
        val loggedUserName = intent.getStringExtra("loggedUserName")
        val loggedEmail = intent.getStringExtra("loggedEmail")
        val loggedPassword = intent.getStringExtra("loggedPassword")

        println(loggedUserName)

        userName.text = loggedUserName
        listView.emptyView = empty
        if (loggedUserId != null) {
            showUserTasks(loggedUserId, API)
        }

        userName.setOnClickListener{
            val intent = Intent(this@MainActivity, EditUserActivity::class.java)
            intent.putExtra("loggedUserId", loggedUserId)
            intent.putExtra("loggedUserName", loggedUserName)
            intent.putExtra("loggedEmail", loggedEmail)
            intent.putExtra("loggedPassword", loggedPassword)
            startActivity(intent)
        }

        add.setOnClickListener {
            if (editText.text.toString() != "") {
                API.createTask(TaskPostModel(
                        content = editText.text.toString(),
                        createdDate = LocalDateTime.now().toString(),
                        userId = loggedUserId!!)
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
//                        showTasks(API)
                        showUserTasks(loggedUserId, API)
                        editText.text.clear()
                        disableButtons()
                    }

                })
            } else {
                Toast.makeText(this@MainActivity, "Text field is empty", Toast.LENGTH_SHORT)
                        .show()
                editText.error = "Text field cannot be empty"
            }
        }

        edit.setOnClickListener {
            val position = listView.checkedItemPosition
            val task = taskList.get(position)
            if (task != null) {
                if (editText.text.toString() != "") {
                    val call = API.putTask(id = task.taskId.toString(),
                            taskModelItem = TaskPostModel(
                                    content = editText.text.toString(),
                                    createdDate = task.createdDate,
                                    userId = task.userId
                            ))

                    call.enqueue(object:Callback<Void>{
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
//                        showTasks(API)
                            if (loggedUserId != null) {
                                showUserTasks(loggedUserId, API)
                            }
                            editText.text.clear()
                            disableButtons()
                        }

                    })
                } else {
                    Toast.makeText(this@MainActivity, "Text field is empty", Toast.LENGTH_SHORT)
                            .show()
                    editText.error = "Text field cannot be empty"
                }
            }
        }

        clear.setOnClickListener {
            for(i in taskList.iterator()) {
                val call = API.deleteTask(id = i.taskId.toString())

                call.enqueue(object:Callback<Void>{
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
//                        showTasks(API)
                        if (loggedUserId != null) {
                            showUserTasks(loggedUserId, API)
                            editText.text.clear()
                            disableButtons()
                        }
                    }
                })
            }
        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val position = listView.checkedItemPosition
            val taskContent = taskList.get(position).content
            editText.setText(taskContent)
            checkTaskIsSelected()
        }
//
        delete.setOnClickListener {
            val position = listView.checkedItemPosition
            val task = taskList.get(position)
            if (task != null) {
                val call = API.deleteTask(id = task.taskId.toString())

                call.enqueue(object:Callback<Void>{
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
//                        showTasks(API)
                        if (loggedUserId != null) {
                            showUserTasks(loggedUserId, API)
                        }
                        editText.text.clear()
                        disableButtons()
                    }

                })
            }
        }
    }

    fun showTasks(API: RetrofitInterface){
        val call = API.tasks
        call?.enqueue(object:Callback<List<TaskModelItem?>?>{
            override fun onFailure(call:Call<List<TaskModelItem?>?>, t: Throwable) {
                Toast.makeText(
                        this@MainActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                    call: Call<List<TaskModelItem?>?>,
                    response: Response<List<TaskModelItem?>?>
            ) {
                val tempList = response.body()
                if (tempList != null) {
                    taskList = tempList as List<TaskModelItem>
                    val task = arrayOfNulls<String>(taskList.size)

                    for (i in taskList.indices)
                        task[i] = taskList[i].content

                    val adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_single_choice
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

    fun showUserTasks(userId: String, API: RetrofitInterface){
        val call = API.getUserTasks(userId)
        call?.enqueue(object:Callback<List<TaskModelItem?>?>{
            override fun onFailure(call:Call<List<TaskModelItem?>?>, t: Throwable) {
                Toast.makeText(
                        this@MainActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                    call: Call<List<TaskModelItem?>?>,
                    response: Response<List<TaskModelItem?>?>
            ) {
                val tempList = response.body()
                if (tempList != null) {
                    taskList = tempList as List<TaskModelItem>
                    val task = arrayOfNulls<String>(taskList.size)

                    for (i in taskList.indices)
                        task[i] = taskList[i].content

                    val adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_single_choice
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

    private fun checkTaskIsSelected() {
        for (i in taskList.indices) {
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