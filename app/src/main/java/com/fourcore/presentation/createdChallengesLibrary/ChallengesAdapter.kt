package com.fourcore.presentation.createdChallengesLibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fourcore.R

class ChallengesAdapter: RecyclerView.Adapter<ChallengesAdapter.ChallengeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.created_challenge_item)
    }

    override fun getItemCount(): Int {
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
    }


    inner class ChallengeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}