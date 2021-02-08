package com.example.hw20

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class signin : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)


        val button = findViewById<Button>(R.id.buttonreturn)
        button.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        var username = findViewById<EditText>(R.id.editTextTextPersonName)
        var password = findViewById<EditText>(R.id.editTextTextPassword)

        val usernamevalue:String = username.text.toString()
        val passwordvalue:String = password.text.toString()
        val preferences: SharedPreferences = applicationContext.getSharedPreferences(getString(R.string.sharedPreference),Context.MODE_PRIVATE)

        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener(){
            val editor:SharedPreferences.Editor = preferences.edit()
            editor.putString(usernamevalue,passwordvalue)

            editor.apply()
            editor.commit()

            val intent = Intent(this, login::class.java)
            startActivity(intent)

        }



    }
}


