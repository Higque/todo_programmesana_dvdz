package com.example.to_dolistandroidapp

import Models.TaskModelItem
import Models.TaskPostModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
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

    var gson = GsonBuilder().create()
    private var retrofitInterface: RetrofitInterface?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(RetrofitInterface::class.java)
        showTasks(API)

        add.setOnClickListener {
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
                        Toast.makeText(this@MainActivity, "New ToDo added to list", Toast.LENGTH_SHORT)
                                .show()
                    }
                    else{
                        Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_SHORT)
                                .show()
                    }
                    showTasks(API)
                }

            })

            editText.text.clear()
        }
//
//        clear.setOnClickListener {
//
//            itemlist.clear()
//            adapter.notifyDataSetChanged()
//        }
//
//        listView.setOnItemClickListener { adapterView, view, i, l ->
//            android.widget.Toast.makeText(this, "You Selected the item --> "+itemlist.get(i), android.widget.Toast.LENGTH_SHORT).show()
//        }
//
//        delete.setOnClickListener {
//            val position: SparseBooleanArray = listView.checkedItemPositions
//            val count = listView.count
//            var item = count - 1
//            while (item >= 0) {
//                if (position.get(item))
//                {
//                    adapter.remove(itemlist.get(item))
//                }
//                item--
//            }
//            position.clear()
//            adapter.notifyDataSetChanged()
//        }
//    }

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
                var taskList : List<TaskModelItem>? = response.body() as List<TaskModelItem>
                var task = arrayOfNulls<String>(taskList!!.size)

                for (i in taskList!!.indices)
                    task[i] = taskList!![i]!!.content

                var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_multiple_choice
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

        })
    }
}