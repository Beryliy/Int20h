package com.fourcore.presentation.createdChallengesLibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fourcore.R
import kotlinx.android.synthetic.main.created_challenge_item.view.*

class ChallengesAdapter: RecyclerView.Adapter<ChallengesAdapter.ChallengeViewHolder>() {
    lateinit var createdChaleges: MutableList<CreatedChallenge>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(
            R.layout.created_challenge_item,
            parent,
            false
        )
        return ChallengeViewHolder(viewItem)
    }

    override fun getItemCount() = createdChaleges.size

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.bind(createdChaleges.get(position))
    }

    fun updateCreatedChallenges(updatedChallenges: MutableList<CreatedChallenge>) {
        createdChaleges = updatedChallenges
        notifyDataSetChanged()
    }

    inner class ChallengeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(createdChallenge: CreatedChallenge) {
            itemView.challengeNameTv.text = createdChallenge.name
            itemView.challengeDescriptionTv.text = createdChallenge.description
            itemView.deadlineTv.text = createdChallenge.deadline
        }
    }
}