package com.fourcore.presentation.received_tasks

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fourcore.domain.Challenge
import kotlinx.android.synthetic.main.item_received_task.view.*
import java.text.SimpleDateFormat

class ReceivedTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("dd MMM EEE HH:mm:ss")

    fun bind(challenge: Challenge) {
        itemView.apply {
            taskNameTextView.text = challenge.name
            taskDescriptionTextView.text = challenge.description
            deadLineTextView.text = simpleDateFormat.format(challenge.deadline)
            awardTextView.text = "Award: ${challenge.completeAward}"
            loseTextView.text = "Lose: ${challenge.fine}"
            taskOwnerTextView.text = challenge.owner.name
        }
    }
}