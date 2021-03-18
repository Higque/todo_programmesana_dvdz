package com.example.to_dolistandroidapp

import Models.SignUpUser
import Models.TaskModelItem
import android.content.Intent
import kotlinx.android.synthetic.main.activity_register.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(RetrofitInterface::class.java)

        btnSignUp.setOnClickListener {
            if (userNameSignUp.text.isEmpty()) {
                userNameSignUp.error = "Fill User Name field"
            }
            if (emailSignUp.text.isEmpty()){
                emailSignUp.error = "Fill Email field"
            } else if (!isValidEmail(emailSignUp.text.toString())) {
                emailSignUp.error = "Invalid email"
            }
            if (passwordSignUp.text.isEmpty()) {
                passwordSignUp.error = "Fill Password field"
            } else if (!IsValidPassword(passwordSignUp.text.toString())) {
                passwordSignUp.error = "Password must be 8 or more characters in length and must contain 1 or more digit character"
            }
            if (userNameSignUp.error.isNullOrEmpty() && emailSignUp.error.isNullOrEmpty() && passwordSignUp.error.isNullOrEmpty()) {
                var encodedPassword = Base64.getEncoder().encodeToString(passwordSignUp.text.toString().toByteArray())
                var encodedEmail = Base64.getEncoder().encodeToString(emailSignUp.text.toString().toByteArray())
                signUp(userNameSignUp.text.toString(), encodedEmail, encodedPassword, API)
            }
        }
    }

    fun signUp(userName: String, email: String, password: String, API: RetrofitInterface) {
        val signUpUserInfo = SignUpUser(userName, password, email)
        API.signUpPost(signUpUserInfo).enqueue(object : Callback<SignUpUser> {
            override fun onFailure(call: Call<SignUpUser>, t: Throwable) {
                Toast.makeText(
                    this@RegisterActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<SignUpUser>, response: Response<SignUpUser>) {
                if (response.code() == 201) {
                    Toast.makeText(this@RegisterActivity, "Complete!", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@RegisterActivity, "Failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }
    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun  IsValidPassword(password: String): Boolean {
        val regex = Regex("(?=.*[0-9]).{8,}$", RegexOption.IGNORE_CASE)
        return regex.matches(password)
    }
}