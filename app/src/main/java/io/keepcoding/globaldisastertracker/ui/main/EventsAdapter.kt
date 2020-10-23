package io.keepcoding.globaldisastertracker.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.globaldisastertracker.R
import kotlinx.android.synthetic.main.events_recycler_view_item.view.*

class EventsAdapter(val context: Context, val itemClickListener: ((EventItemViewModel) -> Unit)? = null) : RecyclerView.Adapter<EventsAdapter.EventHolder>(){

    private val listener: ((View) -> Unit) = {
        if (it.tag is EventItemViewModel) {
            itemClickListener?.invoke(it.tag as EventItemViewModel)
        } else {
            throw IllegalArgumentException("Post item view has not a Post Data as a tag")
        }

    }
    var eventItems: List<EventItemViewModel?>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class EventHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var eventItemViewModel: EventItemViewModel? = null
            set(value) {
                field = value
                itemView.tag = field
                field?.let {
                    itemView.title.text = it.title
                    itemView.description.text = it.description
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.events_recycler_view_item, parent, false)
        return EventHolder(view)
    }

    override fun getItemCount(): Int {
        return if(eventItems != null){
            eventItems!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = eventItems?.get(position)
        holder.eventItemViewModel = event
        holder.itemView.setOnClickListener(listener)
    }
}