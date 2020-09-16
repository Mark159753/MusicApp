package com.example.musicapp.ui.songList

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionListenerAdapter
import com.example.musicapp.R
import com.example.musicapp.databinding.SongListFragmentBinding
import com.example.musicapp.ui.base.BaseFragment
import com.example.musicapp.ui.songList.transition.CircleTransition
import com.example.musicapp.ui.songList.transition.SlideUpAnimation

class SongListFragment : BaseFragment() {

    private val viewModel:SongListFrafmentViewModel by viewModels()

    private val slideUpAnimation by lazy { SlideUpAnimation(requireContext()) }

    private val args:SongListFragmentArgs by navArgs()

    private lateinit var binder:SongListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.song_list_fragment, container, false)

        val colorMain = args.color
        val colorSecond = ColorUtils.blendARGB(colorMain, Color.WHITE, 0.25f)
        binder.songListBackArrow.setBackgroundColor(colorSecond)
        binder.root.setBackgroundColor(colorMain)
        (binder.songListBackground.background as GradientDrawable).apply {
            mutate()
            setColor(colorSecond)
        }
        return binder.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postponeEnterTransition()
        sharedElementEnterTransition = CircleTransition().addListener(object: TransitionListenerAdapter(){

            override fun onTransitionStart(transition: Transition) {
                slideUpAnimation.apply {
                    addAnimView(binder.songListBackground, delay = SLIDE_UP_DELAY_ANIM)
                    addAnimView(binder.songListRc, delay = SLIDE_UP_DELAY_ANIM)
                    addAnimView(binder.songListTitle, delay = SLIDE_UP_DELAY_ANIM)
                    addAnimView(binder.songListQuantity, delay = SLIDE_UP_DELAY_ANIM)
                    addAnimView(binder.songListIcon, delay = SLIDE_UP_DELAY_ANIM)
                    start()
                }
            }
        })
        sharedElementReturnTransition = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder.root.transitionName = args.transitionName
        startPostponedEnterTransition()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        backStack()

        //TODO Change this line below
        lightStatusBarController.setIsLightStatusBar(false)
    }

    private fun backStack(){
        binder.songListBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object{
        private const val SLIDE_UP_DELAY_ANIM = 45L
    }
}