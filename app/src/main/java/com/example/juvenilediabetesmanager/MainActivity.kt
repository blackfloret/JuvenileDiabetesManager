package com.example.juvenilediabetesmanager

import android.media.MediaPlayer
import android.media.VolumeShaper
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.juvenilediabetesmanager.databinding.ActivityMainBinding

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
                R.id.navigation_home,
                R.id.navigation_diary,
                R.id.navigation_shop,
                R.id.navigation_pet,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // music! strange delay when looping and transitioning between tracks,
        // maybe something to do with handling mp3s?
        mediaPlayer = MediaPlayer.create(this, R.raw.music_home)
        rampDown = VolumeShaper.Configuration.Builder()
            .setDuration(500)
            .setCurve(floatArrayOf(0f, 1f), floatArrayOf(1f, 0f))
            .setInterpolatorType(VolumeShaper.Configuration.INTERPOLATOR_TYPE_LINEAR)
            .build()
        mediaPlayer.start()
        mediaPlayer.isLooping = true

        // not the cleanest implementation but i was having issues with SoundPool
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_pet -> {
                    val mediaPlayerSfx = MediaPlayer.create(this, R.raw.sfx_chime)
                    mediaPlayerSfx.start()
                    Handler(Looper.getMainLooper()).postDelayed({
                        mediaPlayerSfx.release()
                    }, mediaPlayerSfx.duration.toLong())
                    switchMusic(R.raw.music_info)
                    navController.navigate(R.id.navigation_pet) // needed to manually navigate here instead
                }
                R.id.navigation_diary -> {
                    val mediaPlayerSfx = MediaPlayer.create(this, R.raw.sfx_pageturn)
                    mediaPlayerSfx.start()
                    Handler(Looper.getMainLooper()).postDelayed({
                        mediaPlayerSfx.release()
                    }, mediaPlayerSfx.duration.toLong())
                    switchMusic(R.raw.music_diary)
                    navController.navigate(R.id.navigation_diary)
                }
                R.id.navigation_home -> {
                    switchMusic(R.raw.music_home)
                    navController.navigate(R.id.navigation_home)
                }
                R.id.navigation_shop -> {
                    val mediaPlayerSfx = MediaPlayer.create(this, R.raw.sfx_doorbell)
                    mediaPlayerSfx.start()
                    Handler(Looper.getMainLooper()).postDelayed({
                        mediaPlayerSfx.release()
                    }, mediaPlayerSfx.duration.toLong())
                    switchMusic(R.raw.music_shop)
                    navController.navigate(R.id.navigation_shop)
                }
                R.id.navigation_settings -> {
                    val mediaPlayerSfx = MediaPlayer.create(this, R.raw.sfx_ratchet)
                    mediaPlayerSfx.start()
                    Handler(Looper.getMainLooper()).postDelayed({
                        mediaPlayerSfx.release()
                    }, mediaPlayerSfx.duration.toLong())
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
        }, 500)
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