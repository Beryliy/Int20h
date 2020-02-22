package com.fourcore.presentation.received_tasks

import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.fourcore.NavFragment
import com.fourcore.R
import kotlinx.android.synthetic.main.fragment_received_tasks.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReceivedTasksFragment : NavFragment() {
    override fun layoutId(): Int = R.layout.fragment_received_tasks

    private val viewModel by viewModel<ReceivedTasksViewModel>()

    private val adapter by lazy {
        ReceivedTasksAdapter(ArrayList()) {
            viewModel.performTask(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receivedTasksRecyclerView.adapter = adapter
        receivedTasksRecyclerView.setHasFixedSize(true)
        receivedTasksLoadingProgressBar.visibility = VISIBLE
        viewModel.getChallangesLiveData().observe(viewLifecycleOwner, Observer {
            adapter.addNewItems(it)
            receivedTasksLoadingProgressBar.visibility = INVISIBLE
        })
    }
}