package com.example.juvenilediabetesmanager.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juvenilediabetesmanager.AppDatabase
import com.example.juvenilediabetesmanager.DiaryRecyclerAdapter
import com.example.juvenilediabetesmanager.Entry
import com.example.juvenilediabetesmanager.databinding.FragmentDiaryBinding

private var diaryLayoutManager: RecyclerView.LayoutManager? = null
private var diaryAdapter: RecyclerView.Adapter<DiaryRecyclerAdapter.ViewHolder>? = null
class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val diaryViewModel =
            ViewModelProvider(this).get(DiaryViewModel::class.java)

        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val db = AppDatabase.getDatabase(requireContext())
        val entryDao = db.entryDao()

        // db pull
        Thread {
            val allEntries: List<Entry>  = entryDao.getAll() // gets all entries
            diaryAdapter = DiaryRecyclerAdapter(requireContext(), allEntries)
            requireActivity().runOnUiThread {

                val recyclerView: RecyclerView = binding.diaryRecyclerView
                diaryLayoutManager = LinearLayoutManager(requireActivity())
                recyclerView.layoutManager = diaryLayoutManager
                recyclerView.adapter = diaryAdapter
            }

        }.start()



        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}