package com.example.musicapp.ui.base

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment:Fragment() {

    internal lateinit var lightStatusBarController: LightStatusBarController

    override fun onAttach(context: Context) {
        if (context is LightStatusBarController)
            lightStatusBarController = context
        else
            throw IllegalStateException("Wrong Context Or bad Activity")
        super.onAttach(context)
    }
}