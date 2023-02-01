package com.riggle.netset.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.riggle.netset.R
import com.riggle.netset.model.NetInfo
import com.riggle.netset.model.Nets
import com.riggle.netset.viewmodel.NetworkListFragmentViewModel

class NetworkListAdapter(
    private val navController: NavController,
    private val viewModel: NetworkListFragmentViewModel
    ) :
    RecyclerView.Adapter<NetworkListAdapter.ViewHolder>() {

    class ViewHolder(
        view: View,
        private val navController: NavController
    ) : RecyclerView.ViewHolder(view) {
        var netInfo : NetInfo? = null
            set(value) {
                nameTextView.text = value?.netName
                field = value
            }

        private val nets : Nets = Nets.getInstance()
        private  val nameTextView: TextView = view.findViewById(R.id.netName)

        init{
            view.setOnClickListener {
                if(netInfo != null) {
                    nets.current = netInfo
                    navController.navigate(
                        R.id.action_mainFragment_to_detailFragment
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.net_list_item, parent, false)
        return ViewHolder(view, navController)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val netInfo : NetInfo = viewModel.getNetInfo(position)
        holder.netInfo = netInfo
    }

    override fun getItemCount(): Int {
        return viewModel.getNetCount()
    }
}