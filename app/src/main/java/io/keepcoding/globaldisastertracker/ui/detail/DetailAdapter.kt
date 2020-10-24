package io.keepcoding.globaldisastertracker.ui.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.keepcoding.globaldisastertracker.R
import kotlinx.android.synthetic.main.events_recycler_view_item.view.*
import kotlinx.android.synthetic.main.images_recycler_view_item.view.*
import kotlinx.android.synthetic.main.news_recycler_view_item.view.*

class DetailAdapter(val context: Context, itemClickListener: DetailInteractionListener? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var newsItems = listOf<NewsItemViewModel?>()
    private var imagesItems = listOf<ImageItemViewModel?>()
    private var isNewsFragment: Boolean = false

    private var newsDetailInteractionListener: ((View) -> Unit)? = {
        if(it.tag is NewsItemViewModel){
            itemClickListener?.onNewsItemClick((it.tag as NewsItemViewModel).newsUrl as String)
        }
    }
    private var imagesDetailInteractionListener: ((View) ->Unit)? = {
        if(it.tag is ImageItemViewModel){
            itemClickListener?.onImageItemClick((it.tag as ImageItemViewModel).image as String)
        }
    }
    companion object {
        const val NEWS = 1
        const val IMAGE = 2
    }

    fun setData(newsList: List<NewsItemViewModel?>?, imagesList: List<ImageItemViewModel?>?, isNews: Boolean){
        isNewsFragment = isNews
        if(isNewsFragment) {
            newsList?.let {
                newsItems = it
            }
        }
        else{
            imagesList?.let {
                this.imagesItems = it
            }
        }
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var article: NewsItemViewModel? = null
            set(value) {
                field = value
                itemView.tag = field
                field?.let {
                    Glide.with(context)
                        .load(it.thumbnail)
                        .apply {
                            RequestOptions()
                                .placeholder(R.drawable.ic_launcher_background)

                        }.into(itemView.news_image)
                    itemView.headline.text = it.title
                    itemView.content.text = it.description
                }
            }
    }

    inner class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageItemViewModel? = null
            set(value) {
                field = value
                itemView.tag = field
                field?.let {
                    Glide.with(context)
                        .load(it.image)
                        .apply {
                            RequestOptions()
                                .placeholder(R.drawable.ic_launcher_background)

                        }.into(itemView.image)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(isNewsFragment){
            return NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_recycler_view_item, parent, false))
        }
        return ImagesViewHolder(LayoutInflater.from(context).inflate(R.layout.images_recycler_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(isNewsFragment){
            val article = newsItems[position]
            (holder as NewsViewHolder).article = article
            holder.itemView.setOnClickListener(newsDetailInteractionListener)
        } else {
            val image = imagesItems[position]
            (holder as ImagesViewHolder).image = image
            holder.itemView.setOnClickListener(imagesDetailInteractionListener)
        }
    }

    override fun getItemCount(): Int {
        return if(isNewsFragment){
            newsItems.size
        } else imagesItems.size
    }
}