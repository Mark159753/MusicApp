package com.example.musicapp.binding

import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.musicapp.R
import com.example.musicapp.ui.MainActivityViewModel
import com.example.musicapp.ui.player.PlayerViewModel
import com.example.musicapp.ui.views.CircleImageView
import com.squareup.picasso.Picasso
import me.tankery.lib.circularseekbar.CircularSeekBar
import java.io.File

@BindingAdapter("loadImg")
fun loadImg(view:ImageView, res:Int){
    Picasso.get()
        .load(res)
        .fit()
        .centerCrop()
        .into(view)
}

@BindingAdapter("loadImg")
fun loadImg(view:CircleImageView, path:String?){
    if (path != null){
            Picasso.get()
                .load(File(path))
                .into(view)
    }else{
        view.setImageResource(R.drawable.ic_launcher_background)
    }
}

@BindingAdapter("timeFormat")
fun timeFormat(view:TextView, time:Long){
    view.text = PlayerViewModel.NowPlayingMetadata.timestampToMSS(view.context, time)
}

@BindingAdapter("songDurationMax")
fun songDurationMax(view: CircularSeekBar, max:Long){
    view.max = max.toFloat()
}

@BindingAdapter("playPauseBtnRes")
fun playPauseBtnRes(view: ImageButton, res:Int){
    view.setImageResource(res)
}
