package com.example.to_dolistandroidapp

import Models.Task
import Models.TaskModelItem
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
//import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList as ArrayList1

class MainActivity : AppCompatActivity() {

//    private val client = OkHttpClient()
    var itemlist = arrayListOf<String>()
//    var taskList = arrayListOf<Task>()
    var gson = GsonBuilder().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(RetrofitInterface::class.java)
        var call = API.tasks

        call?.enqueue(object:retrofit2.Callback<List<TaskModelItem?>?>{
            override fun onFailure(call: retrofit2.Call<List<TaskModelItem?>?>, t: Throwable) {
                TODO("Not yet implemented")
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
            }

        })

//        run("http://10.0.2.2:5000/api/tasks")
////        run("https://api.github.com/users/Evin1-/repos")
//
////        var itemlist = arrayListOf<String>()1
//        var adapter =ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_multiple_choice
//                , itemlist)
//
//        add.setOnClickListener {
//
//            itemlist.add(editText.text.toString())
//            listView.adapter =  adapter
//            adapter.notifyDataSetChanged()
//            editText.text.clear()
//        }
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

//    private fun run(url: String) {
//        var responseCheck = ""
//        val request = Request.Builder()
//                .url(url)
//                .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {}
//            override fun onResponse(call: Call, response: Response) {
//                if (!response.isSuccessful()) {
//                    throw IOException("Unexpected code $response")
//                } else {
//                    responseCheck = response.body()?.string().toString()
//                }
//            }
////            = println(response.body()?.string())
//        })
//        println(responseCheck)
//        taskList = gson.fromJson(responseCheck, Array<Task>::class.java).toCollection(ArrayList())
    }
}