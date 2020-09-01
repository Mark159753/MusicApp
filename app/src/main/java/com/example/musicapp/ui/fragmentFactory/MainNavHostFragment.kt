package com.example.musicapp.ui.fragmentFactory

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.example.musicapp.ui.MainActivity

class MainNavHostFragment:NavHostFragment() {

    override fun onAttach(context: Context) {
        childFragmentManager.fragmentFactory =
            (activity as MainActivity).mainFragmentFactory
        super.onAttach(context)
    }
}