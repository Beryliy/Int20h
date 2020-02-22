package com.fourcore.presentation.score_list

import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.fourcore.NavFragment
import com.fourcore.R
import kotlinx.android.synthetic.main.fragment_score_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreListFragment : NavFragment() {
    override fun layoutId(): Int = R.layout.fragment_score_list

    private val viewModel by viewModel<ScoreListViewModel>()

    private val adapter by lazy {
        ScoreListAdapter(ArrayList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scoreListRecyclerView.setHasFixedSize(true)
        scoreListRecyclerView.adapter = adapter
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            scoreLoadingProgressBar.visibility = if(it) VISIBLE else INVISIBLE
        })
        viewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            adapter.swapData(it)
        })
        viewModel.initScoreList()
    }
}