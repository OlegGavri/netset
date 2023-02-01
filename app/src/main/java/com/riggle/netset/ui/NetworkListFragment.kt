package com.riggle.netset.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.riggle.netset.adapters.NetworkListAdapter
import com.riggle.netset.databinding.FragmentNetworkListBinding

//
// This fragment show list of network interfaces
//
class NetworkListFragment : Fragment() {

    private var _binding: FragmentNetworkListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNetworkListBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        val navController = findNavController()
        val networkListAdapter = NetworkListAdapter(navController)
        val networkRecycleView = binding.networksRecycleView
        networkRecycleView.adapter = networkListAdapter
    }
}