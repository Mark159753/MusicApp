package com.example.musicapp.ui.player

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.musicapp.R
import com.example.musicapp.databinding.PlayerFragmentBinding
import com.example.musicapp.ui.MainActivityViewModel
import com.example.musicapp.ui.player.animation.RotationAnimation
import me.tankery.lib.circularseekbar.CircularSeekBar

class PlayerFragment : Fragment() {

    private val viewModel: PlayerViewModel by activityViewModels()
    private val mainActivityViewModel:MainActivityViewModel by activityViewModels()

    private var seekBarIsTracking = false

    private val rotationAnimation:RotationAnimation by lazy { RotationAnimation(binder.playerAlbumLabel) }

    //Corner change background for topPlayerBlackHead
    private val playerBlackHead:GradientDrawable by lazy {
        GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(Color.BLACK)
        }
    }

    private lateinit var binder:PlayerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.player_fragment, container, false)
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binder.apply {
            lifecycleOwner = this@PlayerFragment
            viewModel = this@PlayerFragment.viewModel
        }

        handleSongProgress()
        observeMediaButtonsRes()

        mainActivityViewModel.bottomPlayerProgress.observe(viewLifecycleOwner, Observer {
            (binder.root as MotionLayout).progress = it
            starStopRotating(it, viewModel.mediaButtonRes.value == R.drawable.ic_baseline_pause_24)
            animTopLeftCorner(1f - it)
        })
    }

    private fun animTopLeftCorner(p: Float){
        val cornerRadius = p * 33f.dp
        playerBlackHead.cornerRadii = floatArrayOf(cornerRadius, cornerRadius, 0f, 0f, 0f, 0f, 0f, 0f)
        binder.playerBlackHead.background = playerBlackHead
    }

    private fun observeMediaButtonsRes(){
        viewModel.mediaButtonRes.observe(viewLifecycleOwner, Observer {
            when (it){
                R.drawable.ic_play_arrow_24 -> {
                    starStopRotating(mainActivityViewModel.bottomPlayerProgress.value ?: 0f, false)
                }
                R.drawable.ic_baseline_pause_24 -> {
                    starStopRotating(mainActivityViewModel.bottomPlayerProgress.value ?: 0f, true)
                }
            }
        })
    }

    private fun starStopRotating(p:Float, isPlaying:Boolean){
        if (p == 1f && isPlaying) {
            rotationAnimation.start(duration = ROTATING_DURATION)
        }
        else {
            rotationAnimation.stop()
            binder.playerAlbumLabel.rotation = 0f
        }
    }

    private fun handleSongProgress(){
        viewModel.mediaPosition.observe(viewLifecycleOwner, Observer {
            if (!seekBarIsTracking){
                binder.playerSeekBar.progress = it.toFloat()
            }
        })

        binder.playerSeekBar.setOnSeekBarChangeListener(object : CircularSeekBar.OnCircularSeekBarChangeListener{
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {}

            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {
                seekBarIsTracking = true
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {
                viewModel.seekTo(seekBar!!.progress.toLong())
                seekBarIsTracking = false
            }
        })
    }

    private val Float.dp
        get() = this * resources.displayMetrics.density

    companion object{
        private const val ROTATING_DURATION = 2500L
    }
}