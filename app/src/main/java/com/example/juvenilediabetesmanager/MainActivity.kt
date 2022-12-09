package com.example.juvenilediabetesmanager

import android.media.MediaPlayer
import android.media.VolumeShaper
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavAction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juvenilediabetesmanager.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var rampDown: VolumeShaper.Configuration
    private lateinit var shaperDown: VolumeShaper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_diary, R.id.navigation_shop, R.id.navigation_pet, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // music! strange delay when looping and transitioning between tracks,
        // maybe something to do with handling mp3s?
        mediaPlayer = MediaPlayer.create(this, R.raw.music_home)
        rampDown = VolumeShaper.Configuration.Builder()
            .setDuration(1000)
            .setCurve(floatArrayOf(0f, 1f), floatArrayOf(1f, 0f))
            .setInterpolatorType(VolumeShaper.Configuration.INTERPOLATOR_TYPE_LINEAR)
            .build()
        mediaPlayer.start()
        mediaPlayer.isLooping = true

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_pet -> {
                    switchMusic(R.raw.music_info)
                    navController.navigate(R.id.navigation_pet) // needed to manually navigate here
                }                                               // instead for some reason
                R.id.navigation_diary -> {
                    switchMusic(R.raw.music_diary)
                    navController.navigate(R.id.navigation_diary)
                }
                R.id.navigation_home -> {
                    switchMusic(R.raw.music_home)
                    navController.navigate(R.id.navigation_home)
                }
                R.id.navigation_shop -> {
                    switchMusic(R.raw.music_shop)
                    navController.navigate(R.id.navigation_shop)
                }
                R.id.navigation_settings -> {
                    switchMusic(R.raw.music_settings)
                    navController.navigate(R.id.navigation_settings)
                }
            }
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun switchMusic(newTrackId: Int) {
        val handler = Handler(Looper.getMainLooper())
        val newMediaPlayer = MediaPlayer.create(this, newTrackId)
        val shaperUp = newMediaPlayer.createVolumeShaper(VolumeShaper.Configuration.LINEAR_RAMP)
        shaperUp.apply(VolumeShaper.Operation.PLAY)
        newMediaPlayer.start()
        newMediaPlayer.seekTo(mediaPlayer.currentPosition)
        shaperDown = mediaPlayer.createVolumeShaper(rampDown)
        shaperDown.apply(VolumeShaper.Operation.PLAY)
        handler.postDelayed({
            mediaPlayer.release()
            mediaPlayer = newMediaPlayer
            mediaPlayer.isLooping = true
        }, 2000)
    }

    override fun onStop() {
        mediaPlayer.pause()
        super.onStop()
    }

    override fun onStart() {
        mediaPlayer.start()
        super.onStart()
    }
}