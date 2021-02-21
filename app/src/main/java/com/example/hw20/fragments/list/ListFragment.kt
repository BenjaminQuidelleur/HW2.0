package com.example.hw20.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw20.R
import com.example.hw20.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private lateinit var mReminderViewModel: ReminderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        //Recyclerview
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //ReminderViewModel
        mReminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)
        mReminderViewModel.readAllData.observe(viewLifecycleOwner, Observer { reminder ->
            adapter.setData(reminder) })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        } //add menu
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllReminders()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllReminders(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mReminderViewModel.deleteAllReminders()
            Toast.makeText(requireContext(), "Successfully removed everything", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Delete everything ?")
        builder.setMessage("Are you sure you want to delete everything ?")
        builder.create().show()
    }

}