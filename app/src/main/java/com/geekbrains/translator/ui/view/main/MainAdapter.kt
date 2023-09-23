package com.geekbrains.translator.ui.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.translator.R

class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var data: List<com.geekbrains.model.data.DataModel> = arrayListOf()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(dataItem: com.geekbrains.model.data.DataModel) {
            if (layoutPosition == RecyclerView.NO_POSITION) {
                return
            }

            itemView.findViewById<TextView>(R.id.translation_title).text = dataItem.text

            dataItem.meanings?.get(0)?.translation?.text.let {
                itemView.findViewById<TextView>(R.id.translation_value).text = it
            }

            itemView.setOnClickListener { openInNewWindow(dataItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.translation_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<com.geekbrains.model.data.DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun openInNewWindow(dataItem: com.geekbrains.model.data.DataModel) {
        onListItemClickListener.onItemClick(dataItem)
    }

    interface OnListItemClickListener {
        fun onItemClick(dataItem: com.geekbrains.model.data.DataModel)
    }
}