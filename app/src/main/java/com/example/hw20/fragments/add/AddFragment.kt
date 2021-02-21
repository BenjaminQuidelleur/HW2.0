package com.example.hw20.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hw20.R
import com.example.hw20.model.Reminder
import com.example.hw20.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.util.*


class AddFragment : Fragment() {


    private lateinit var mReminderViewModel: ReminderViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mReminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        view.add_btn.setOnClickListener{
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase(){
        val message = addMessage_et.text.toString()
        val reminderDate = addDate_et.text.toString()
        val reminderTime = addTime_et.text.toString()

       /*
       val reminderDate = addDate_et.text.split(".").toTypedArray()


        val currentReminderdate = GregorianCalendar(
            reminderDate[2].toInt(),
            reminderDate[1].toInt() - 1,
            reminderDate[0].toInt()
        )
        */

        if(inputCheck(message, reminderDate, reminderTime)){
            //Create reminder object
            val reminder = Reminder(
                0,
                message,
                reminderDate,
                reminderTime

            )

            //Add data to Database
            mReminderViewModel.addReminder(reminder)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(message : String, reminderDate: String, reminderTime: String): Boolean{
        return!(TextUtils.isEmpty(message) && TextUtils.isEmpty(reminderDate) && TextUtils.isEmpty(reminderTime))
    }

}