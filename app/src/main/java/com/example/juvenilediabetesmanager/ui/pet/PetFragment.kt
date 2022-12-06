package com.example.juvenilediabetesmanager.ui.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.juvenilediabetesmanager.databinding.FragmentPetBinding

class PetFragment : Fragment() {

    private var _binding: FragmentPetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val petViewModel =
            ViewModelProvider(this).get(PetViewModel::class.java)

        _binding = FragmentPetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        petViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}