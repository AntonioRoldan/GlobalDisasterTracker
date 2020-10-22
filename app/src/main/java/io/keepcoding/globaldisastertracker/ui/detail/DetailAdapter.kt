package io.keepcoding.globaldisastertracker.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.keepcoding.globaldisastertracker.R
import kotlinx.android.synthetic.main.events_recycler_view_item.view.*

class DetailAdapter(val context: Context, itemClickListener: DetailInteractionListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var newsList: List<NewsItemViewModel?>? = mutableListOf(null)
    private var imagesList: List<ImageItemViewModel?>? = mutableListOf(null)
    private var isNewsFragment: Boolean = false

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

                    itemView.title.text = it.title
                    itemView.description.text = it.description
                }
            }
    }

    inner class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == NEWS){
            return NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.news_recycler_view_item, parent, false))
        }
        return ImagesViewHolder(LayoutInflater.from(context).inflate(R.layout.images_recycler_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
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