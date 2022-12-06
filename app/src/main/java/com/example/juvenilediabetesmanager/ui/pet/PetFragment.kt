package com.example.juvenilediabetesmanager.ui.pet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
        //Visible and invisible element toggles
        val selectInventoryBox = binding.selectInventoryBox
        val openInventoryButton = binding.OpenInventoryButton
        val closeInventoryButton = binding.CloseInventoryButton
        openInventoryButton.setOnClickListener {
            if(!selectInventoryBox.isVisible) {
                selectInventoryBox.visibility = View.VISIBLE
                closeInventoryButton.visibility = View.VISIBLE
                openInventoryButton.visibility = View.INVISIBLE
            }
        }

        closeInventoryButton.setOnClickListener {
            if(selectInventoryBox.isVisible) {
                selectInventoryBox.visibility = View.INVISIBLE
                openInventoryButton.visibility = View.VISIBLE
                closeInventoryButton.visibility = View.INVISIBLE
            }
        }
        petViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}