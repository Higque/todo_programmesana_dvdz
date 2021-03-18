package com.example.to_dolistandroidapp

import Models.SignInBody
import Models.UserBody
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnSignUp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailField.text.clear()
        passwordField.text.clear()

        var rf = Retrofit.Builder()
            .baseUrl(RetrofitInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var API = rf.create(RetrofitInterface::class.java)

        btnLogin.setOnClickListener {
            if (emailField.text.isEmpty()){
                emailField.error = "Fill Email field"
            } else if (!isValidEmail(emailField.text.toString())) {
                emailField.error = "Invalid email"
            }
            if (passwordField.text.isEmpty()) {
                passwordField.error = "Fill Password field"
            }
            if (emailField.error.isNullOrEmpty() && passwordField.error.isNullOrEmpty()) {
                signIn(emailField.text.toString(), passwordField.text.toString(), API)
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String, API: RetrofitInterface) {
        var encodedPassword = Base64.getEncoder().encodeToString(password.toByteArray())
        var encodedEmail = Base64.getEncoder().encodeToString(email.toByteArray())

        val signInInfo = SignInBody(encodedEmail, encodedPassword)

        var loginToken = Base64.getEncoder().encodeToString("$email:$password".toByteArray())
        API.signInUser(signInInfo, loginToken).enqueue(object: Callback<UserBody> {
            override fun onFailure(call: Call<UserBody>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<UserBody>, response: Response<UserBody>) {
                if (response.code() == 200) {
                    Toast.makeText(this@LoginActivity, "Login success!", Toast.LENGTH_SHORT).show()
                    println(response.body())
                    val user = response.body()
                    if (user != null) {
                        emailField.text.clear()
                        passwordField.text.clear()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("loggedUserId", user.userId)
                        intent.putExtra("loggedUserName", user.userName)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                    emailField.text.clear()
                    passwordField.text.clear()
                    emailField.error = "Email or password is incorrect"
                    passwordField.error = "Email or password is incorrect"
                }
            }

        })
    }
    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}