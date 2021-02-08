package com.example.hw20

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class reminders : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)


        val button = findViewById<Button>(R.id.button4)
        button.setOnClickListener(){
            val intent = Intent(this, add_reminder::class.java)
            startActivity(intent)

        }

        val button2 = findViewById<Button>(R.id.buttonlogout)
        button2.setOnClickListener(){
            applicationContext.getSharedPreferences(getString(R.string.sharedPreference), Context.MODE_PRIVATE).edit().clear().commit()
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        var listView = findViewById<ListView>(R.id.reminderslist)



        val reminders_list = arrayOf(
            "Reminder 1", "Reminder 2", "Reminder 3", "Reminder 4", "Reminder 5", "Reminder 6", "Reminder 7", "Reminder 8", "Reminder 9", "Reminder 10", "Reminder 11", "Reminder 12", "Reminder 10", "Reminder 11", "Reminder 12"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, reminders_list)
        listView.adapter = adapter
    }



}