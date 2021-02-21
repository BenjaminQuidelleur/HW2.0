package com.example.hw20.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hw20.R
import com.example.hw20.model.Reminder
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var reminderList = emptyList<Reminder>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = reminderList[position]
        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.message_txt.text = currentItem.message
        holder.itemView.date_txt.text = currentItem.reminderDate
        holder.itemView.time_txt.text = currentItem.reminderTime
        //holder.itemView.age_txt.text = currentItem.age.toString()

        holder.itemView.rowLayout.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(reminder: List<Reminder>){
        this.reminderList = reminder
        notifyDataSetChanged()
    }
}