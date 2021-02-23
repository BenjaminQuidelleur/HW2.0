package com.example.hw20.fragments

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ArrayAdapter
import android.widget.ImageView


class MySpinnerAdapter(context: Context, images: Array<Int>) :
        ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, images) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup) =
            getImageForPosition(position)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup) =
            getImageForPosition(position)

    private fun getImageForPosition(position: Int) = ImageView(context).apply {
        setBackgroundResource(getItem(position)!!)
        layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }
}