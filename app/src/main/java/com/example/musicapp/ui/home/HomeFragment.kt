package com.example.musicapp.ui.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.ui.home.adapters.vp.CarouselEffectTransformer
import com.example.musicapp.model.homeTabs.HomeTabs
import com.example.musicapp.R
import com.example.musicapp.ui.home.adapters.vp.VpAdapterTabs
import com.example.musicapp.databinding.HomeFragmentBinding
import com.example.musicapp.ui.home.adapters.rv.HomeMusicAdapter
import com.example.musicapp.ui.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment() : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by activityViewModels()
    private lateinit var binder: HomeFragmentBinding

    private lateinit var vpTabsAdapterTabs: VpAdapterTabs
    private lateinit var rvMusicAdapter:HomeMusicAdapter

    //TODO Remove this from here
    private val listObserver = object :RecyclerView.AdapterDataObserver(){
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            if (positionStart == 0){
                binder.homeSongList.scrollToPosition(0)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vpTabsAdapterTabs = VpAdapterTabs(requireContext())
        val listOfTabs = arrayListOf<HomeTabs>(
            HomeTabs(
                "Ambient",
                Color.parseColor("#6200ea")
            ),
            HomeTabs(
                "Ambient",
                Color.parseColor("#00bfa5")
            ),
            HomeTabs(
                "Ambient",
                Color.parseColor("#c51162")
            ),
            HomeTabs(
                "Ambient",
                Color.parseColor("#ffd600")
            ),
            HomeTabs(
                "Ambient",
                Color.parseColor("#c51162")
            )
        )
        vpTabsAdapterTabs.setDataList(listOfTabs)
        binder.homeTabsItems.apply {
            adapter = vpTabsAdapterTabs
            setPageTransformer(true,
                CarouselEffectTransformer(
                    requireContext()
                )
            )
            pageMargin = 21
            offscreenPageLimit = 3
        }

        // ---------------------------------------------------------
        ////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////

        rvMusicAdapter = HomeMusicAdapter()
        rvMusicAdapter.setListener {
            playerViewModel.playMediaId(it)
        }
        rvMusicAdapter.registerAdapterDataObserver(listObserver)
        binder.homeSongList.apply {
            adapter = rvMusicAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        viewModel.listOfSongs.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            rvMusicAdapter.submitList(it)
        })
    }


}