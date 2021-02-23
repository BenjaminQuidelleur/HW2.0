package com.example.hw20.fragments.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hw20.R
import com.example.hw20.model.Reminder
import com.example.hw20.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {


    var speachButton: ImageView? = null
    var speachText: EditText? = null

    private val RECOGNIZER_RESULT = 1




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

        /*
        val adapter = MySpinnerAdapter(requireContext(), arrayOf<Int>(R.drawable.ic_baseline_people_24, R.drawable.baseline_circle_notifications_24, R.drawable.ic_baseline_warning_24))
        view.icon_spinner.setAdapter(adapter)
        */





        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        speachButton = btn_mic
        speachText = addMessage_et

        speachButton!!.setOnClickListener {
            val speachIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text")
            startActivityForResult(speachIntent, RECOGNIZER_RESULT)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOGNIZER_RESULT && resultCode == Activity.RESULT_OK) {
            val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            speachText!!.setText(matches!![0].toString())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun insertDataToDatabase(){
        val message = addMessage_et.text.toString()
        val reminderDate = addDate_et.text.toString()
        val reminderTime = addTime_et.text.toString()
        //val iconId = icon_spinner.selectedItemId

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
                    //iconId.toInt(),
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