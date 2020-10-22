package io.keepcoding.globaldisastertracker.ui.detail

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DetailAdapter(context: Context, itemClickListener: DetailInteractionListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var newsList: List<NewsItemViewModel>? = null
    var imagesList: List<ImageItemViewModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {

    }

}