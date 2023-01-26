package com.riggle.netset.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riggle.netset.R

class NetworkListAdapter(private val viewModel : MainViewModel) : RecyclerView.Adapter<NetworkListAdapter.ViewHolder>() {
    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView: TextView = view.findViewById<TextView>(R.id.netName)
            private set
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.net_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val netNameTextView = holder.nameTextView
        val netName : String = viewModel.getNetName(position)
        netNameTextView.text = netName
    }

    override fun getItemCount(): Int {
        return viewModel.getNetCount()
    }
}