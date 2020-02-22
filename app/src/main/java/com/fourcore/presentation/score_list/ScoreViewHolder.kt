package com.fourcore.presentation.score_list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fourcore.domain.User
import kotlinx.android.synthetic.main.item_user_score.view.*

class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        itemView.apply {
            scoreItemNameTextView.text = user.name
            scoreTextView.text = user.points.toString()
        }
    }
}