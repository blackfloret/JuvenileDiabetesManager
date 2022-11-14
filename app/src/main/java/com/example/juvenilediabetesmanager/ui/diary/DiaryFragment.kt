package com.example.juvenilediabetesmanager.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juvenilediabetesmanager.DiaryRecyclerAdapter
import com.example.juvenilediabetesmanager.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<DiaryRecyclerAdapter.ViewHolder>? = null

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

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(getContext())
        recyclerView.layoutManager = layoutManager

        adapter = DiaryRecyclerAdapter()
        recyclerView.adapter = adapter

//        val textView: TextView = binding.
//        diaryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}