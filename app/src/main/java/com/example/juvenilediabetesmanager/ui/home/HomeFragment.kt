package com.example.juvenilediabetesmanager.ui.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
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
/*        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()*/
        //val textView: TextView = binding.textHome
        //homeViewModel.text.observe(viewLifecycleOwner) {
         //   textView.text = it
        //}
        val currencyDisplay: TextView = binding.currencyDisplay
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val highScore: Int
        if (sharedPref != null) {
            val defaultValue = resources.getInteger(R.integer.saved_balance_default_key)
            highScore = sharedPref.getInt(getString(R.string.saved_balance_key), defaultValue)
            currencyDisplay.text = highScore.toString()
        }

        val logButton: ImageButton = binding.logButton
        logButton.setOnClickListener {
            Thread {
                val entryDao = db.entryDao()
                val newEntry = Entry(LocalDateTime.now().toString())
                entryDao.insertAll(newEntry)

                Log.d("database", "Inserting diary entry with time ${newEntry.time}")

                val entries: List<Entry> = entryDao.loadByTimeRange("2022-11-13T14:43:40.310", "2022-11_30T17:59.07.722")
                Log.d("test",entries.toString())
            }.start()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}