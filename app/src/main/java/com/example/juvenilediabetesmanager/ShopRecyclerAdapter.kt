package com.example.juvenilediabetesmanager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShopRecyclerAdapter: RecyclerView.Adapter<ShopRecyclerAdapter.ViewHolder>() {
    private var costsArr = arrayOf("10", "15", "20", "35", "40", "45")
    private var imagesArr = intArrayOf(R.drawable.clothingitem01, R.drawable.clothingitem02, R.drawable.clothingitem03, R.drawable.clothingitem04, R.drawable.clothingitem05, R.drawable.clothingitem06)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        //Return the number of card rows
        return costsArr.size
    }

    override fun onBindViewHolder(holder: ShopRecyclerAdapter.ViewHolder, position: Int) {
        //Populate the arrays data to each card view
        holder.itemCost1.text = costsArr[position]
        holder.itemImage1.setImageResource(imagesArr[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage1: ImageView
        var itemCost1: TextView

        init {
            itemImage1 = itemView.findViewById(R.id.item_image1)
            itemCost1 = itemView.findViewById(R.id.textView1)
        }
    }
}