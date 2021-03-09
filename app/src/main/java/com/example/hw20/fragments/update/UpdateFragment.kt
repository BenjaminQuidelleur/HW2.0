package com.example.hw20.fragments.update

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw20.MainActivity
import com.example.hw20.R
import com.example.hw20.fragments.add.AddFragment
import com.example.hw20.model.Reminder
import com.example.hw20.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateFragment : Fragment(),DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    var speachButton: ImageView? = null
    var speachText: EditText? = null

    private val RECOGNIZER_RESULT = 1


    private lateinit var reminderCalender: Calendar

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mReminderViewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mReminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)

        view.updateMessage_et.setText(args.currentReminder.message)
        view.updateDate_et.setText(args.currentReminder.reminderDate)
        //view.updateTime_et.setText(args.currentReminder.reminderTime)

        /*view.update_btn.setOnClickListener{
            updateItem()
        }*/

        //Add menu
        setHasOptionsMenu(true)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        speachButton = btn_mic2
        speachText = updateMessage_et

        speachButton!!.setOnClickListener {
            val speachIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text")
            startActivityForResult(speachIntent, RECOGNIZER_RESULT)
        }

        //hide keyboard when the dateTextBox is clicked
        updateDate_et.inputType = InputType.TYPE_NULL
        updateDate_et.isClickable = true

        //show date and time dialog

        updateDate_et.setOnClickListener {
            reminderCalender = GregorianCalendar.getInstance()
            DatePickerDialog(
                    requireContext(),
                    this,
                    reminderCalender.get(Calendar.YEAR),
                    reminderCalender.get(Calendar.MONTH),
                    reminderCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        update_btn.setOnClickListener {
            //validate entry values here
            if (updateDate_et.text.isEmpty()) {
                Toast.makeText(
                        requireContext(),
                        "Date should not be empty and should be in dd.mm.yyyy format",
                        Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val message = updateMessage_et.text.toString()
            val reminderDate = updateDate_et.text.toString()

            val reminder = Reminder(
                    0,
                    message,
                    //iconId.toInt(),
                    reminderDate,
                    //reminderTime,
                    reminder_seen = false,
                "0.0",
                "0.0"

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



            lifecycleScope.launch{




                val updatedReminder = Reminder(args.currentReminder.id, message, reminderDate,false,"0.0","0.0")
                val reminderId = mReminderViewModel.updateReminder(updatedReminder).toInt()

                Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()



                MainActivity.setReminderWithWorkManager(
                    requireContext(),
                    reminderId,
                    paymentCalender.timeInMillis,
                    message

                )

                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                //findNavController().navigate(R.id.action_updateFragment_to_listFragment)


            }







           /* val updatedReminder = Reminder(args.currentReminder.id, message, reminderDate,false)

            mReminderViewModel.updateReminder(updatedReminder)*/



            //val reminderId = args.currentReminder.id
            /*MainActivity.setReminderWithWorkManager(
                    requireContext(),
                    id,
                    paymentCalender.timeInMillis,
                    message,
                    reminderId
            )*/



            //findNavController().navigate(R.id.action_updateFragment_to_listFragment)

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
    private fun updateItem(){
        val message = updateMessage_et.text.toString()
        val reminderDate = updateDate_et.text.toString()
        val reminderTime = updateTime_et.text.toString()
        //val reminder_seen = false
        //val iconId = updateIcon_et.selectedItemId
        //val age = Integer.parseInt(updateAge_et.text.toString())

        if(inputCheck(message, reminderDate, reminderTime)){
            //create reminder object

            val updatedReminder = Reminder(args.currentReminder.id, message, reminderDate,false)

            /*val reminderDate2 = updateDate_et.text.split(".").toTypedArray()


            val currentReminderdate = GregorianCalendar(
                    reminderDate2[2].toInt(),
                    reminderDate2[1].toInt() - 1,
                    reminderDate2[0].toInt()
            )
*/


            //Update Current Reminder
            mReminderViewModel.updateReminder(updatedReminder)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()

            /*//add notification
            AddFragment.setRemnder(
                    requireContext(),
                    id,
                    currentReminderdate.timeInMillis,
                    message
            )*/

            //Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)


        }else{
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show()
        }
    }
    */


    private fun inputCheck(message : String, reminderDate: String): Boolean{
        return!(TextUtils.isEmpty(message) && TextUtils.isEmpty(reminderDate))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteReminder()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteReminder(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mReminderViewModel.deleteReminder(args.currentReminder)
            Toast.makeText(requireContext(), "Successfully removed : ${args.currentReminder.message}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Delete ${args.currentReminder.message}?")
        builder.setMessage("Are you sure you want to delete ${args.currentReminder.message}?")
        builder.create().show()
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
        updateDate_et.setText(simleDateFormat.format(reminderCalender.time))

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
        updateDate_et.setText(simleDateFormat.format(reminderCalender.time))
    }


}