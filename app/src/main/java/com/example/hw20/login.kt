package com.example.hw20

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class login : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)





        val button = findViewById<Button>(R.id.buttonback)
        button.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }



        val button2 = findViewById<Button>(R.id.button3)
        button2.setOnClickListener() {



            var username = findViewById<EditText>(R.id.editTextTextPersonName2)
            var password = findViewById<EditText>(R.id.editTextTextPassword3)

            val usernamevalue:String = username.text.toString()
            val passwordvalue:String = password.text.toString()
            var right_user = "Benjamin"
            var right_password = "benjaminpassword"



            if (usernamevalue.equals(right_user) && passwordvalue.equals(right_password)) {


                applicationContext.getSharedPreferences(
                    getString(R.string.sharedPreference),
                    Context.MODE_PRIVATE).edit().putInt("LoginStatus", 1).apply()

                val intent2 = Intent(this, reminders::class.java)
                startActivity(intent2)
            }
        }
        checkLoginStatus()


    }


    override fun onResume() {
        super.onResume()
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        val loginStatus = applicationContext.getSharedPreferences(
            getString(R.string.sharedPreference),
            Context.MODE_PRIVATE
        ).getInt("LoginStatus", 0)
        if (loginStatus == 1) {
            startActivity(Intent(applicationContext, reminders::class.java))
        }
    }
}









