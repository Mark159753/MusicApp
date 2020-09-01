package com.example.musicapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.musicapp.R
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import me.tankery.lib.circularseekbar.CircularSeekBar
import javax.inject.Inject

private const val PERMISSION_CODE = 5

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binder:ActivityMainBinding

    private var seekBarIsTracking = false

    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var mainFragmentFactory:FragmentFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binder.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )

        handleSongProgress()

        //TODO Remove this
        binder.playerTopBtnContainer.setOnClickListener {
            Log.e("CLICK", "HU_RA")
        }
    }



    private fun handleSongProgress(){
        viewModel.mediaPosition.observe(this, Observer {
            if (!seekBarIsTracking){
                binder.playerSeekBar.progress = it.toFloat()
            }
        })

        binder.playerSeekBar.setOnSeekBarChangeListener(object :CircularSeekBar.OnCircularSeekBarChangeListener{
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

    fun requestPermission():Boolean{
        return if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE)
            false
        }else{
            true
        }
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
                    Log.d("WE GOT PERMISSION", "READ_EXTERNAL_STORAGE")
                }
            }
        }
    }
}