package com.example.juvenilediabetesmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryRecyclerAdapter: RecyclerView.Adapter<DiaryRecyclerAdapter.ViewHolder>(){

    // arrays values are hard-coded for now
    private var titles = arrayOf("10/22/2022", "10/21/2022", "12/21/2022")
    private var details = arrayOf("12:02 pm", "7:35 pm", "6:51 pm")

    // create card view, inflate it, pass to inner class
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // view object
        val v = LayoutInflater.from(parent.context).inflate(R.layout.diary_card_layout, parent, false)
        return ViewHolder(v) // pass the view object back
    }

    // identify how many items to pass to viewHolder; return number of indexes
    override fun getItemCount(): Int {
        return titles.size
    }

    // populates the data to the card_view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_details)
        }
    }
}