package com.example.musicapp.ui.songList

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.graphics.ColorUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import com.example.musicapp.R
import com.example.musicapp.databinding.SongListFragmentBinding
import com.example.musicapp.ui.MainActivity
import com.example.musicapp.ui.base.BaseFragment
import com.example.musicapp.ui.player.PlayerViewModel
import com.example.musicapp.ui.songList.adapters.SongListAdapter
import com.example.musicapp.ui.songList.transition.CircleTransition
import com.example.musicapp.ui.songList.transition.SlideUpAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongListFragment : BaseFragment() {

    private val viewModel:SongListFragmentViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by activityViewModels()

    private val slideUpAnimation by lazy { SlideUpAnimation(requireContext()) }

    private val args:SongListFragmentArgs by navArgs()

    private lateinit var musicAdapter:SongListAdapter
    private val listObserver = object : RecyclerView.AdapterDataObserver(){
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == 0){
                binder.songListRc.scrollToPosition(0)
            }
        }
    }

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
        (activity as MainActivity).requestPermissionIfNeed {
            if (it) viewModel.subscribeMusicService()
            else Toast.makeText(requireContext(), getString(R.string.permission_message), Toast.LENGTH_SHORT).show()
        }
        initMusicList()
        observeSongList()

        //TODO Change this line below
        lightStatusBarController.setIsLightStatusBar(false)
    }


    private fun initMusicList(){
        musicAdapter = SongListAdapter().also {adapter ->
            adapter.setListener {
                playerViewModel.playMediaId(it)
            }
            adapter.setMoreBtnListener { view, id ->
                val popupMenu = PopupMenu(view.context, view, Gravity.END)
                popupMenu.inflate(R.menu.song_item_popup_menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {menuItem ->
                    when(menuItem.itemId){
                        R.id.add_to_fav_btn -> {
                            Toast.makeText(requireContext(), "Item Added to Favorite $id", Toast.LENGTH_SHORT).show()
                            true
                        }
                        else -> false
                    }
                })
                popupMenu.show()
            }
            adapter.registerAdapterDataObserver(listObserver)
        }
        binder.songListRc.apply {
            adapter = musicAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeSongList(){
        viewModel.listOfSongs.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            musicAdapter.submitList(it)
        })
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