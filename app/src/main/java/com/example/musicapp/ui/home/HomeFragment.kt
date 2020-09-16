package com.example.musicapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.ui.home.adapters.vp.CarouselEffectTransformer
import com.example.musicapp.R
import com.example.musicapp.ui.home.adapters.vp.VpAdapterTabs
import com.example.musicapp.databinding.HomeFragmentBinding
import com.example.musicapp.ui.base.BaseFragment
import com.example.musicapp.ui.home.adapters.rv.HomeMusicAdapter
import com.example.musicapp.ui.home.adapters.vp.ItemSelectedListener
import com.example.musicapp.ui.home.delegats.TabsDelegate
import com.example.musicapp.ui.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment() : BaseFragment(), ItemSelectedListener {

    private val viewModel: HomeViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by activityViewModels()
    private lateinit var binder: HomeFragmentBinding

    private lateinit var vpTabsAdapterTabs: VpAdapterTabs
    private lateinit var rvMusicAdapter:HomeMusicAdapter


    private val listOfTabs by lazy { TabsDelegate.createTabsList() }

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
        lightStatusBarController.setIsLightStatusBar(true)
        initTabsAdapter()

        initMusicListAdapter()
        observeSongList()
    }

    private fun initTabsAdapter(){
        vpTabsAdapterTabs = VpAdapterTabs(requireContext()).also {
            it.setItemSelectedListener(this)
        }

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
    }

    private fun initMusicListAdapter(){
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
    }

    private fun observeSongList(){
        viewModel.listOfSongs.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            rvMusicAdapter.submitList(it)
        })
    }


    override fun onItemSelected(position: Int, view: View) {
        val action = HomeFragmentDirections.actionHomeFragmentToSongListFragment(listOfTabs[position].tabColor, view.transitionName)
        val extras = FragmentNavigatorExtras(view to view.transitionName)
        findNavController().navigate(action, extras)
    }


}