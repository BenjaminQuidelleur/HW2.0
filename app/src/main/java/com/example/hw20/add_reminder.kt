package com.example.hw20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class add_reminder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)


        val button = findViewById<Button>(R.id.button5)
        button.setOnClickListener(){
            val intent = Intent(this, reminders::class.java)
            startActivity(intent)

        }

        val button2 = findViewById<Button>(R.id.buttonsave)
        button2.setOnClickListener(){
            val intent = Intent(this, reminders::class.java)
            startActivity(intent)

        }
    }
}