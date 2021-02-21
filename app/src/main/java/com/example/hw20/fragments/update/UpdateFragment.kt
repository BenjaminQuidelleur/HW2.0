package com.example.hw20.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw20.R
import com.example.hw20.model.Reminder
import com.example.hw20.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateFragment : Fragment() {


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
        view.updateTime_et.setText(args.currentReminder.reminderTime)

        view.update_btn.setOnClickListener{
            updateItem()
        }

        //Add menu
        setHasOptionsMenu(true)
        return view
    }

    private fun updateItem(){
        val message = updateMessage_et.text.toString()
        val reminderDate = updateDate_et.text.toString()
        val reminderTime = updateTime_et.text.toString()
        //val age = Integer.parseInt(updateAge_et.text.toString())

        if(inputCheck(message, reminderDate, reminderTime)){
            //create reminder object

            val updatedReminder = Reminder(args.currentReminder.id, message, reminderDate, reminderTime)

            //Update Current Reminder
            mReminderViewModel.updateReminder(updatedReminder)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            //Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)


        }else{
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(message : String, reminderDate: String, reminderTime: String): Boolean{
        return!(TextUtils.isEmpty(message) && TextUtils.isEmpty(reminderDate) && TextUtils.isEmpty(reminderTime))
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


}