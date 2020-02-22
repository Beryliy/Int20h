package com.fourcore.presentation.score_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fourcore.R
import com.fourcore.domain.User

class ScoreListAdapter(
    private val users: ArrayList<User>
) : RecyclerView.Adapter<ScoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return ScoreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_score,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun swapData(newData: List<User>) {
        users.clear()
        users.addAll(newData)
        notifyDataSetChanged()
    }
}