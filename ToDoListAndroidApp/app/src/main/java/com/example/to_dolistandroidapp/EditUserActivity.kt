package com.example.to_dolistandroidapp

import Models.UserBody
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_user.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        val rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val API = rf.create(RetrofitInterface::class.java)

        val loggedUserId = intent.getStringExtra("loggedUserId")
        val loggedUserName = intent.getStringExtra("loggedUserName")
        val loggedEmail = intent.getStringExtra("loggedEmail")
        val loggedPassword = intent.getStringExtra("loggedPassword")
        println(loggedUserName)

        editUserName.setText(loggedUserName)

        btnEditUserName.setOnClickListener {
            if (editUserName.text.toString() != "") {
                if (editUserName.text.toString().length >= 5) {
                    if (loggedUserId != null && loggedEmail != null && loggedPassword != null) {
                        val call =
                                API.putUser(
                                        id = loggedUserId,
                                        userBody = UserBody(
                                                userId = loggedUserId,
                                                userName = editUserName.text.toString(),
                                                email = loggedEmail,
                                                password = loggedPassword
                                        )
                                )
                        call.enqueue(object:retrofit2.Callback<Void> {
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                println(t.message)
                                Toast.makeText(
                                        this@EditUserActivity,
                                        t.message,
                                        Toast.LENGTH_SHORT
                                ).show()
                            }

                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                println(response.body())
                                if (response.code() == 200) {
                                    Toast.makeText(this@EditUserActivity, "User is updated", Toast.LENGTH_SHORT)
                                            .show()
                                    val intent = Intent(this@EditUserActivity, MainActivity::class.java)
                                    intent.putExtra("loggedUserId", loggedUserId)
                                    intent.putExtra("loggedUserName", editUserName.text.toString())
                                    intent.putExtra("loggedEmail", loggedEmail)
                                    intent.putExtra("loggedPassword", loggedPassword)
                                    startActivity(intent)
                                }
                                else{
                                    Toast.makeText(this@EditUserActivity, "Failed!", Toast.LENGTH_SHORT)
                                            .show()
                                }
                            }

                        })
                    }
                } else {
                    editUserName.error = "User Name must be 5 or more characters in length"
                }
            } else {
                Toast.makeText(this@EditUserActivity, "User Name field is empty", Toast.LENGTH_SHORT)
                    .show()
                editUserName.error = "User Name field cannot be empty"
            }
        }
    }
}