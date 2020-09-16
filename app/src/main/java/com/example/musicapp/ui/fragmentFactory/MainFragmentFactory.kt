package com.example.musicapp.ui.fragmentFactory

import androidx.fragment.app.FragmentFactory
import com.example.musicapp.ui.home.HomeFragment
import com.example.musicapp.ui.home.adapters.rv.HomeMusicAdapter
import com.example.musicapp.ui.home.adapters.vp.VpAdapterTabs
import com.example.musicapp.ui.songList.SongListFragment

class MainFragmentFactory():FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =
        when(className){
            HomeFragment::class.java.name -> HomeFragment()
            SongListFragment::class.java.name -> SongListFragment()
            else -> HomeFragment()
        }
}