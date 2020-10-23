package io.keepcoding.globaldisastertracker.ui.detail

import android.content.Context
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

    private var newsList: List<NewsItemViewModel?>? = mutableListOf(null)
    private var imagesList: List<ImageItemViewModel?>? = mutableListOf(null)
    private var isNewsFragment: Boolean = false
    private var newsDetailInteractionListener: ((View) -> Unit)? = {
        if(it.tag is NewsItemViewModel){
            itemClickListener?.onNewsItemClick((it.tag as NewsItemViewModel)?.newsUrl as String)
        }
    }
    private var imagesDetailInteractionListener: ((View) ->Unit)? = {
        if(it.tag is ImageItemViewModel){
            itemClickListener?.onImageItemClick((it.tag as ImageItemViewModel)?.image as String)
        }
    }
    companion object {
        const val NEWS = 1
        const val IMAGE = 2
    }

    fun setData(newsList: List<NewsItemViewModel?>?, imagesList: List<ImageItemViewModel?>?, isNewsFragment: Boolean){
        this.isNewsFragment = isNewsFragment
        if(isNewsFragment)
            this.newsList = newsList
        else
            this.imagesList = imagesList
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
                    itemView.title.text = it.title
                    itemView.description.text = it.description
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
        if(viewType == NEWS){
            return NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_recycler_view_item, parent, false))
        }
        return ImagesViewHolder(LayoutInflater.from(context).inflate(R.layout.images_recycler_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(isNewsFragment){
            val article = newsList?.get(position)
            (holder as NewsViewHolder).article = article
            holder.itemView.setOnClickListener(newsDetailInteractionListener)
        } else {
            val image = imagesList?.get(position)
            (holder as ImagesViewHolder).image = image
            holder.itemView.setOnClickListener(imagesDetailInteractionListener)
        }
    }

    override fun getItemCount(): Int {
        if(isNewsFragment){
            newsList?.let { news ->
                return news.size
            }
        } else {
            imagesList?.let { images ->
                return images.size
            }
        }
        return 0
    }

}