package com.example.hw20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.buttonlog)
        button.setOnClickListener(){
            val intent = Intent(this, login::class.java)
            startActivity(intent)

        }

        val button2 = findViewById<Button>(R.id.buttonSign)
        button2.setOnClickListener(){
            val intent = Intent(this, signin::class.java)
            startActivity(intent)

        }
    }
}