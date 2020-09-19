package com.example.musicapp.ui.songList.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.model.song.Song
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import java.io.File

class SongListAdapter: ListAdapter<Song, SongListAdapter.SongItemHolder>(COMPARATOR) {

    private var listener: ((String) -> Unit)? = null

    fun setListener(listener: (parentId:String)-> Unit){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return SongItemHolder(v)
    }

    override fun onBindViewHolder(holder: SongItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SongItemHolder(val view:View):RecyclerView.ViewHolder(view){
        private val title = view.findViewById<TextView>(R.id.song_item_name)
        private val img = view.findViewById<ShapeableImageView>(R.id.song_item_lable)

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
        private val COMPARATOR = object : DiffUtil.ItemCallback<Song>(){
            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem == newItem
            }
        }
    }
}