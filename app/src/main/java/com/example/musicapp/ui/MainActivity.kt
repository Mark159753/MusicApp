package com.example.musicapp.ui

import android.Manifest
import android.animation.ValueAnimator
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentFactory
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.ui.base.LightStatusBarController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

private const val PERMISSION_CODE = 5

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LightStatusBarController {

    private lateinit var binder:ActivityMainBinding
    private lateinit var bottomPlayer:BottomSheetBehavior<FragmentContainerView>

    private var isBottomPlayerShow = false

    private val viewModel: MainActivityViewModel by viewModels()

    private var permissionListener: ((Boolean) -> Unit)? = null

    @Inject
    lateinit var mainFragmentFactory:FragmentFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionIfNeed()
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binder.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initBottomPlayer()

        viewModel.playerPlaybackState.observe(this, Observer {
            animateNavHostFragmentMargin(it)
        })
    }

    override fun setIsLightStatusBar(light:Boolean){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val view = window.decorView
            if (light){
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }else{
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
    }

    private fun animateNavHostFragmentMargin(state:Int){
        when(state){
            PlaybackStateCompat.STATE_NONE,
            PlaybackStateCompat.STATE_STOPPED-> {
                navFragmentRemoveMarginBottom()
            }
            else -> {
                navFragmentAddMarginBottom()
            }
        }
    }

    private fun navFragmentAddMarginBottom(){
        if (!isBottomPlayerShow){
            bottomPlayer.apply {
                isHideable = false
                state = BottomSheetBehavior.STATE_COLLAPSED
            }
            animateValue(0, resources.getDimensionPixelSize(R.dimen.bottom_player_height)){
                navFragmentMarginBottom(it)
            }
            isBottomPlayerShow = true
        }
    }

    private fun navFragmentRemoveMarginBottom(){
        if (isBottomPlayerShow){
            bottomPlayer.apply {
                isHideable = true
                state = BottomSheetBehavior.STATE_HIDDEN
            }
            animateValue(resources.getDimensionPixelSize(R.dimen.bottom_player_height), 0){
                navFragmentMarginBottom(it)
            }
            isBottomPlayerShow = false
        }
    }

    private fun animateValue(start:Int, end:Int, duration:Long = 150, animFun: (Int) -> Unit){
        ValueAnimator.ofInt(start, end).apply {
            this.duration = duration
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener {
                animFun((it.animatedValue as Int))
            }
            start()
        }
    }

    private fun navFragmentMarginBottom(margin:Int){
        val param = binder.navHostFragment.layoutParams as ViewGroup.MarginLayoutParams
        param.bottomMargin = margin
        binder.navHostFragment.layoutParams = param
    }

    private fun initBottomPlayer(){
        bottomPlayer = BottomSheetBehavior.from(bottom_player)
        bottomPlayer.apply {
            isHideable = true
            state = BottomSheetBehavior.STATE_HIDDEN
            addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED){
                        binder.navHostFragment.visibility = View.GONE
                        setIsLightStatusBar(false)
                    }else{
                        binder.navHostFragment.visibility = View.VISIBLE
                        setIsLightStatusBar(true)
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    viewModel.setBottomPlayerProgress(slideOffset)
                }
            })
        }
    }


    fun requestPermissionIfNeed(listener: (Boolean) -> Unit){
        permissionListener = listener
        if (isPermissionGranted())
            listener(true)
        else {
            listener(false)
            requestPermission()
        }
    }

    private fun requestPermissionIfNeed(){
        if (!isPermissionGranted())
            requestPermission()
    }

    private fun isPermissionGranted():Boolean{
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    permissionListener?.let { it(true) }
                    Log.d("WE GOT PERMISSION", "READ_EXTERNAL_STORAGE")
                }else{
                    permissionListener?.let { it(false) }
                }
            }
        }
    }
}