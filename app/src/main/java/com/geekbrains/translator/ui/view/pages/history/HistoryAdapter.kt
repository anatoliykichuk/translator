package com.geekbrains.translator.ui.view.pages.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.translator.R

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var data: List<com.geekbrains.model.data.DataModel> = arrayListOf()

    fun setData(data: List<com.geekbrains.model.data.DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(dataItem: com.geekbrains.model.data.DataModel) {
            if (layoutPosition == RecyclerView.NO_POSITION) {
                return
            }

            itemView.findViewById<TextView>(R.id.history_header_view).text = dataItem.text

            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context, "on click: ${dataItem.text}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_history_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}