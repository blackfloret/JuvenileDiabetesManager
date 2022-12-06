package com.example.juvenilediabetesmanager.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juvenilediabetesmanager.R
import com.example.juvenilediabetesmanager.ShopRecyclerAdapter
import com.example.juvenilediabetesmanager.databinding.FragmentShopBinding

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private var shopLayoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ShopRecyclerAdapter.ViewHolder>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val shopViewModel =
            ViewModelProvider(this).get(ShopViewModel::class.java)

        _binding = FragmentShopBinding.inflate(inflater, container, false)

        val shopRecyclerView: RecyclerView = binding.shopRecyclerView
        shopLayoutManager = GridLayoutManager(requireActivity(), 3)
        shopRecyclerView.layoutManager = shopLayoutManager

        adapter = ShopRecyclerAdapter()
        shopRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}