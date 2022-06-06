package com.example.sportz.presentation.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportz.R
import com.example.sportz.domain.model.Sports

class SportsListingAdapter(val sports: List<Sports>) :
    RecyclerView.Adapter<SportsListingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvSportItemName: TextView = itemView.findViewById(R.id.tv_sports_list_item_name)

        init {
            itemView.setOnClickListener {
                var position: Int = getAdapterPosition()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_sports_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSportItemName.text = sports[position].name
    }

    override fun getItemCount(): Int {
        return sports.size
    }
}
