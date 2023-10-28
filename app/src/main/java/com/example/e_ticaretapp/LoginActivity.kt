package com.example.e_ticaretapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.e_ticaretapp.api.ApiConfig
import com.example.e_ticaretapp.databinding.ActivityLoginBinding
import com.example.e_ticaretapp.models.JWTData
import com.example.e_ticaretapp.models.LoginData
import com.example.e_ticaretapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txtUsername = binding.txtUsername
        val txtPassword = binding.txtPassword
        val btnLogin = binding.btnLogin

        btnLogin.setOnClickListener {
            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()){
                val loginData = LoginData(username, password)
                val loginClient = ApiConfig.getApiService().login(loginData)

                loginClient.enqueue(object: Callback<JWTData>{
                    override fun onResponse(call: Call<JWTData>, response: Response<JWTData>) {
                        val JWTData = response.body()
                        if (JWTData != null){
                            Utils.user = JWTData
                            val i = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(i)
                            finish()
                        }else{
                            Toast.makeText(this@LoginActivity, "Username or password is incorrect", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<JWTData>, t: Throwable) {
                        Log.e("Login Client Error:", t.toString())
                        Toast.makeText(this@LoginActivity, "Cannot connect to server", Toast.LENGTH_SHORT).show()
                    }
                })

            }else{
                if (username.isEmpty()) txtUsername.setError("This field cannot be empty")
                if (password.isEmpty()) txtPassword.setError("This field cannot be empty")
            }
        }
    }
}