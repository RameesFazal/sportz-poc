package com.example.sportz.presentation.listing

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportz.R
import com.example.sportz.domain.model.Sports
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class SportsListingAdapter(
    val sports: List<Sports>,
    private val glideToVectorYou: GlideToVectorYou,
    val onItemClicked: ((index: Int) -> Unit)
) :
    RecyclerView.Adapter<SportsListingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvSportItemName: TextView = itemView.findViewById(R.id.tv_sports_list_item_name)
        var imSportsIcon: ImageView = itemView.findViewById(R.id.im_sports_icon)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                onItemClicked(sports[position].id)
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
        glideToVectorYou.load(Uri.parse(sports[position].icon), holder.imSportsIcon)
    }

    override fun getItemCount(): Int {
        return sports.size
    }
}
