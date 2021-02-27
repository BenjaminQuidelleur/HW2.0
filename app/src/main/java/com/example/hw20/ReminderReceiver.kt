package com.example.hw20

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.example.hw20.fragments.add.AddFragment
import com.example.hw20.fragments.list.ListFragment

//import com.mobicomp2020.labbankingapp.PaymentHistory

class ReminderReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        // Retrieve data from intent
        val id = intent?.getIntExtra("id", 0)
        val text = intent?.getStringExtra("message")


        MainActivity.showNofitication(context!!,text!!)
    }
}