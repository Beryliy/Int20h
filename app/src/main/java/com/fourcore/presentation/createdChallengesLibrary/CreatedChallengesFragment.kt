package com.fourcore.presentation.createdChallengesLibrary


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fourcore.NavFragment

import com.fourcore.R
import kotlinx.android.synthetic.main.fragment_crated_challenges.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatedChallengesFragment : NavFragment() {
    val viewModel: CreatedChallengesViewModel by viewModel()
    val createdChallengesAdapter: ChallengesAdapter by lazy {
        ChallengesAdapter()
    }
    override fun layoutId() = R.layout.fragment_crated_challenges

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveCreatedChalenges.observe(viewLifecycleOwner, Observer {
            createdChallengesAdapter.updateCreatedChallenges(it)
            createdChallengesRv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = createdChallengesAdapter
            }
        })
        viewModel.initChallengesData()
        createNewChallengeB.setOnClickListener {
            val action = CreatedChallengesFragmentDirections.actionCreatedChallengesFragmentToChallengeConstructorFragment()
            findNavController().navigate(action)
        }
    }
}
