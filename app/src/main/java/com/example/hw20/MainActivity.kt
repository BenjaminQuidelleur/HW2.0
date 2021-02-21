package com.example.hw20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupActionBarWithNavController(findNavController(R.id.fragment3))

        }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment3)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    }
