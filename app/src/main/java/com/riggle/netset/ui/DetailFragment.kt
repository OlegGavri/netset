package com.riggle.netset.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.riggle.netset.R
import com.riggle.netset.databinding.FragmentDetailBinding
import com.riggle.netset.model.Nets
import com.riggle.netset.viewmodel.MainViewModel

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
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val netInfo = nets.getSelected()

        binding.apply {
            netName.text = netInfo.netName
            ip.text = netInfo.ipAddress
            netmask.text = netInfo.netMask
        }
    }
}