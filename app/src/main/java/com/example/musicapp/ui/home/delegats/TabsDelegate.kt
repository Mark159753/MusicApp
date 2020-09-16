package com.example.musicapp.ui.home.delegats

import android.graphics.Color
import com.example.musicapp.model.homeTabs.HomeTabs

class TabsDelegate {

    companion object{
        fun createTabsList():List<HomeTabs>{
            return listOf(HomeTabs(
                0,
                "Ambient",
                Color.parseColor("#6200ea")
            ),
                HomeTabs(
                    1,
                    "Ambient",
                    Color.parseColor("#00bfa5")
                ),
                HomeTabs(
                    2,
                    "Ambient",
                    Color.parseColor("#c51162")
                ),
                HomeTabs(
                    3,
                    "Ambient",
                    Color.parseColor("#ffd600")
                ),
                HomeTabs(
                    4,
                    "Ambient",
                    Color.parseColor("#c51162")
                ))
        }
    }
}