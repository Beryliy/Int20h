package com.fourcore.presentation.vote_challenge

import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.fourcore.NavFragment
import com.fourcore.R
import com.fourcore.domain.ChallengePerform
import com.fourcore.global.util.showShortToast
import kotlinx.android.synthetic.main.fragment_vote_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class VoteTasksFragment : NavFragment() {
    override fun layoutId(): Int = R.layout.fragment_vote_task

    private val viewModel by viewModel<VoteTaskViewModel>()

    private val challengPerformList = Stack<ChallengePerform>()
    private var currentPerform: ChallengePerform? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getVoteList().observe(viewLifecycleOwner, Observer {
            challengPerformList.clear()
            challengPerformList.addAll(it)
            showFirstItem()
        })
        acceptVoteFAB.setOnClickListener {
            currentPerform?.let { it1 -> viewModel.increaseVote(it1) }
            showFirstItem()
        }
        rejectVoteFAB.setOnClickListener {
            currentPerform?.let { it1 -> viewModel.decreaseVote(it1) }
            showFirstItem()
        }
    }

    private fun showFirstItem() {
        if(challengPerformList.isEmpty()) {
            voteRoot.visibility = INVISIBLE
            showShortToast(requireContext(), "List is empty")
            return
        }
        voteRoot.visibility = VISIBLE
        currentPerform = challengPerformList.pop()
        voteTitleTextView.text = currentPerform!!.challenge.name
        voteDescriptionTextView.text = currentPerform!!.challenge.description
        Glide
            .with(this)
            .load(currentPerform!!.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(voteImageView)
    }
}