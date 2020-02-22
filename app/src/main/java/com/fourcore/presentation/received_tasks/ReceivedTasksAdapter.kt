package com.fourcore.presentation.received_tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fourcore.R
import com.fourcore.domain.Challenge
import kotlinx.android.synthetic.main.item_received_task.view.*

class ReceivedTasksAdapter(
    private val data: MutableList<Challenge>,
    private val onPerformClick: (challenge: Challenge) -> Unit
) : RecyclerView.Adapter<ReceivedTaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceivedTaskViewHolder {
        return ReceivedTaskViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_received_task,
                parent,
                false
            )
        ).apply {
            itemView.perfomTaskTextView.setOnClickListener {
                onPerformClick.invoke(data[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ReceivedTaskViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun swapData(newData: List<Challenge> ) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun addNewItems(challenges: List<Challenge>) {
        data.addAll(0, challenges)
        notifyItemRangeInserted(0, challenges.size)
    }
}