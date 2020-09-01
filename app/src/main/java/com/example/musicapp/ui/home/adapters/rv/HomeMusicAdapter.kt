package com.example.musicapp.ui.home.adapters.rv

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.model.song.Song
import com.example.musicapp.ui.views.CircleImageView
import com.squareup.picasso.Picasso
import java.io.File


class HomeMusicAdapter: ListAdapter<Song, HomeMusicAdapter.MusicHolder>(COMPARATOR) {

    private var listener: ((String) -> Unit)? = null

    fun setListener(listener: (parentId:String)-> Unit){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return MusicHolder(v)
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MusicHolder(private val view: View):RecyclerView.ViewHolder(view){
        private val title = view.findViewById<TextView>(R.id.song_item_title)
        private val img = view.findViewById<CircleImageView>(R.id.song_item_img)


        fun bind(item:Song?){
            title.text = item?.title
            item?.albumArtPath?.let {
                Picasso.get()
                    .load(File(it))
                    .into(img)
            }
            view.setOnClickListener {
                listener?.invoke(item!!.id.toString())
            }
        }
    }

    companion object{
        private val COMPARATOR = object :DiffUtil.ItemCallback<Song>(){
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem == newItem
            }
        }
    }
}