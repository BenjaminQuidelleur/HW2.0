package com.example.hw20.fragments.add

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.InputType
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.hw20.MainActivity
import com.example.hw20.R
import com.example.hw20.ReminderReceiver
import com.example.hw20.ReminderWorker
import com.example.hw20.MainActivity.Companion.setReminderWithWorkManager
import com.example.hw20.MainActivity.Companion.setRemnder
import com.example.hw20.model.Reminder
import com.example.hw20.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

//import kotlin.random.Random.Default.Companion


class AddFragment : Fragment() ,DatePickerDialog.OnDateSetListener,
TimePickerDialog.OnTimeSetListener {


    var speachButton: ImageView? = null
    var speachText: EditText? = null

    private val RECOGNIZER_RESULT = 1

    private lateinit var reminderCalender: Calendar


    private lateinit var mReminderViewModel: ReminderViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)





        mReminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        /*view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }*/

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

        //hide keyboard when the dateTextBox is clicked
        addDate_et.inputType = InputType.TYPE_NULL
        addDate_et.isClickable = true

        //show date and time dialog

        addDate_et.setOnClickListener {
            reminderCalender = GregorianCalendar.getInstance()
            DatePickerDialog(
                    requireContext(),
                    this,
                    reminderCalender.get(Calendar.YEAR),
                    reminderCalender.get(Calendar.MONTH),
                    reminderCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        add_btn.setOnClickListener {
            //validate entry values here
            if (addDate_et.text.isEmpty()) {
                Toast.makeText(
                        requireContext(),
                        "Date should not be empty and should be in dd.mm.yyyy format",
                        Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val message = addMessage_et.text.toString()
            val reminderDate = addDate_et.text.toString()

            val reminder = Reminder(
                    0,
                    message,
                    //iconId.toInt(),
                    reminderDate,
                    //reminderTime,
                    reminder_seen = false

            )


            val paymentCalender = GregorianCalendar.getInstance()
            val dateFormat = "dd.MM.yyyy HH:mm" // change this format to dd.MM.yyyy if you have not time in your date.
            // a better way of handling dates but requires API version 26 (Build.VERSION_CODES.O)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val formatter = DateTimeFormatter.ofPattern(dateFormat)
                val date = LocalDateTime.parse(reminder.reminderDate, formatter)

                paymentCalender.set(Calendar.YEAR, date.year)
                paymentCalender.set(Calendar.MONTH, date.monthValue - 1)
                paymentCalender.set(Calendar.DAY_OF_MONTH, date.dayOfMonth)
                paymentCalender.set(Calendar.HOUR_OF_DAY, date.hour)
                paymentCalender.set(Calendar.MINUTE, date.minute)

            } else {
                if (dateFormat.contains(":")) {
                    // if your date contains hours and minutes and its in the format dd.mm.yyyy HH:mm
                    val dateparts = reminder.reminderDate.split(" ").toTypedArray()[0].split(".").toTypedArray()
                    val timeparts = reminder.reminderDate.split(" ").toTypedArray()[1].split(":").toTypedArray()

                    paymentCalender.set(Calendar.YEAR, dateparts[2].toInt())
                    paymentCalender.set(Calendar.MONTH, dateparts[1].toInt() - 1)
                    paymentCalender.set(Calendar.DAY_OF_MONTH, dateparts[0].toInt())
                    paymentCalender.set(Calendar.HOUR_OF_DAY, timeparts[0].toInt())
                    paymentCalender.set(Calendar.MINUTE, timeparts[1].toInt())

                } else {
                    //no time part
                    //convert date  string value to Date format using dd.mm.yyyy
                    // here it is assumed that date is in dd.mm.yyyy
                    val dateparts = reminder.reminderDate.split(".").toTypedArray()
                    paymentCalender.set(Calendar.YEAR, dateparts[2].toInt())
                    paymentCalender.set(Calendar.MONTH, dateparts[1].toInt() - 1)
                    paymentCalender.set(Calendar.DAY_OF_MONTH, dateparts[0].toInt())
                }


            }

            mReminderViewModel.addReminder(reminder)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()


                MainActivity.setReminderWithWorkManager(
                        requireContext(),
                        id,
                        paymentCalender.timeInMillis,
                        message
                )



            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        }
    }


         override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if (requestCode == RECOGNIZER_RESULT && resultCode == Activity.RESULT_OK) {
                val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                speachText!!.setText(matches!![0].toString())
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
/*
    private fun insertDataToDatabase(){
        val message = addMessage_et.text.toString()
        val reminderDate = addDate_et.text.toString()
        //val reminderTime = addTime_et.text.toString()
        //val iconId = icon_spinner.selectedItemId


       /*val reminderDate2 = addDate_et.text.split(".").toTypedArray()


        val currentReminderdate = GregorianCalendar(
            reminderDate2[2].toInt(),
            reminderDate2[1].toInt() - 1,
            reminderDate2[0].toInt()
        )*/


        if(inputCheck(message, reminderDate)){
            //Create reminder object
            val reminder = Reminder(
                0,
                message,
                    //iconId.toInt(),
                reminderDate,
                //reminderTime,
                    reminder_seen = false

            )

            //Add data to Database
            mReminderViewModel.addReminder(reminder)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()

            /*//add notification
            ListFragment.setRemnder(
                    requireContext(),
                    id,
                    currentReminderdate.timeInMillis,
                    message
            )*/

            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else
    {
        Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
    }

    }
*/


        fun inputCheck(message: String, reminderDate: String): Boolean {
            return !(TextUtils.isEmpty(message) && TextUtils.isEmpty(reminderDate))
        }


        override fun onDateSet(
                dailogView: DatePicker?,
                selectedYear: Int,
                selectedMonth: Int,
                selectedDayOfMonth: Int
        ) {
            reminderCalender.set(Calendar.YEAR, selectedYear)
            reminderCalender.set(Calendar.MONTH, selectedMonth)
            reminderCalender.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth)
            val simleDateFormat = SimpleDateFormat("dd.MM.yyyy")
            addDate_et.setText(simleDateFormat.format(reminderCalender.time))

            // if you want to show time picker after the date
            // you dont need this,change dateFormat value to dd.MM.yyyy
            TimePickerDialog(
                    requireContext(),
                    this,
                    reminderCalender.get(Calendar.HOUR_OF_DAY),
                    reminderCalender.get(Calendar.MINUTE),
                    true
            ).show()
        }

         override fun onTimeSet(view: TimePicker?, selectedhourOfDay: Int, selectedMinute: Int) {
            reminderCalender.set(Calendar.HOUR_OF_DAY, selectedhourOfDay)
            reminderCalender.set(Calendar.MINUTE, selectedMinute)
            val simleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
            addDate_et.setText(simleDateFormat.format(reminderCalender.time))
        }


    }
