package com.riggle.netset.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.riggle.netset.databinding.FragmentDetailBinding
import com.riggle.netset.model.NetInfo
import com.riggle.netset.model.Nets

/**
 * Fragment show information obout selected net
 */
class DetailFragment : Fragment() {

    private val nets : Nets = Nets.getInstance()

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val netInfo : NetInfo = nets.current!!

        binding.apply {
            netName.text = netInfo.netName
            ip.text = netInfo.ipAddress
            netmask.text = netInfo.netMask
        }
    }
}