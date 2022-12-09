package com.example.juvenilediabetesmanager.ui.home

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.juvenilediabetesmanager.AppDatabase
import com.example.juvenilediabetesmanager.Entry
import com.example.juvenilediabetesmanager.R
import com.example.juvenilediabetesmanager.databinding.FragmentHomeBinding
import java.time.LocalDateTime

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

/*        if (sharedPref != null) {
            with (sharedPref.edit()) {
                putInt(getString(R.string.saved_balance_key), newBalance)
                apply()
            }
        }*/
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val db = AppDatabase.getDatabase(requireContext())

        val currencyDisplay: TextView = binding.currencyDisplay
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        var balance: Int
        val defaultValue = resources.getInteger(R.integer.saved_balance_default_key)
        balance = sharedPref.getInt(getString(R.string.saved_balance_key), defaultValue)
        currencyDisplay.text = balance.toString()

        val logButton: ImageButton = binding.logButton
        val selectSnackBox: ConstraintLayout = binding.selectSnackBox
        val confirmTestBox: ConstraintLayout = binding.confirmTestBox
        val mouseButton: ImageView = binding.mouseFood
        val fishButton: ImageView = binding.fishFood
        val canButton: ImageView = binding.canFood
        val confirmTestButton: Button = binding.testConfirm
        val cancelTestButton: Button = binding.testCancel
        selectSnackBox.visibility = View.GONE
        confirmTestBox.visibility = View.GONE
        logButton.setOnClickListener {
            if(!selectSnackBox.isVisible) {
                selectSnackBox.visibility = View.VISIBLE
                val mediaPlayer = MediaPlayer.create(this.activity, R.raw.sfx_xyloup)
                mediaPlayer.start()
                Handler(Looper.getMainLooper()).postDelayed({
                    mediaPlayer.release()
                }, mediaPlayer.duration.toLong())
            } else {
                selectSnackBox.visibility = View.GONE
                val mediaPlayer = MediaPlayer.create(this.activity, R.raw.sfx_xylodown)
                mediaPlayer.start()
                Handler(Looper.getMainLooper()).postDelayed({
                    mediaPlayer.release()
                }, mediaPlayer.duration.toLong())
            }
        }

        mouseButton.setOnClickListener {
            selectSnackBox.visibility = View.GONE
            confirmTestBox.visibility = View.VISIBLE
            val mediaPlayer = MediaPlayer.create(this.activity, R.raw.sfx_eating)
            mediaPlayer.start()
            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer.release()
            }, mediaPlayer.duration.toLong())
        }
        fishButton.setOnClickListener {
            selectSnackBox.visibility = View.GONE
            confirmTestBox.visibility = View.VISIBLE
            val mediaPlayer = MediaPlayer.create(this.activity, R.raw.sfx_eating)
            mediaPlayer.start()
            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer.release()
            }, mediaPlayer.duration.toLong())
        }
        canButton.setOnClickListener {
            selectSnackBox.visibility = View.GONE
            confirmTestBox.visibility = View.VISIBLE
            val mediaPlayer = MediaPlayer.create(this.activity, R.raw.sfx_eating)
            mediaPlayer.start()
            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer.release()
            }, mediaPlayer.duration.toLong())
        }

        confirmTestButton.setOnClickListener {
            confirmTestBox.visibility = View.GONE
            val mediaPlayer = MediaPlayer.create(this.activity, R.raw.sfx_xylo3up)
            mediaPlayer.start()
            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer.release()
            }, mediaPlayer.duration.toLong())
            Thread {
                val entryDao = db.entryDao()
                val newEntry = Entry(LocalDateTime.now().toString())
                entryDao.insertAll(newEntry)

                Log.d("database", "Inserting diary entry with time ${newEntry.time}")

                val entries: List<Entry> = entryDao.loadByTimeRange("2022-11-13T14:43:40.310", "2022-11_30T17:59.07.722")
                Log.d("test",entries.toString())
                balance += 50
                with (sharedPref.edit()) {
                    putInt(getString(R.string.saved_balance_key), balance)
                    apply()
                }
                requireActivity().runOnUiThread {
                    currencyDisplay.text = balance.toString()
                    Toast.makeText(requireActivity(), "New entry added! Thank you so much!", Toast.LENGTH_LONG).show()
                }
            }.start()
        }

        cancelTestButton.setOnClickListener {
            confirmTestBox.visibility = View.GONE
            val mediaPlayer = MediaPlayer.create(this.activity, R.raw.sfx_xylodown)
            mediaPlayer.start()
            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer.release()
            }, mediaPlayer.duration.toLong())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}