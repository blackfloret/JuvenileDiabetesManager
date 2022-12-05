package com.example.juvenilediabetesmanager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryRecyclerAdapter(private val context: Context, entries: List<Entry>) : RecyclerView.Adapter<DiaryRecyclerAdapter.ViewHolder>() {

    private var listEntries: List<Entry>
    private val mList: List<Entry>
    private val mDatabase: AppDatabase
    init {
        this.listEntries = entries
        this.mList = entries
        mDatabase = AppDatabase.getDatabase(context)
    }

    // create card view, inflate it, pass to inner class
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // view object
        val v = LayoutInflater.from(parent.context).inflate(R.layout.diary_card_layout, parent, false)
        return ViewHolder(v) // pass the view object back
    }

    // identify how many items to pass to viewHolder; return number of indexes
    override fun getItemCount(): Int {
        return listEntries.size
    }

    // populates the data to the card_view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entries = listEntries[position]
        holder.itemTitle.text = entries.time
        holder.itemDetail.text = "Filler"
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