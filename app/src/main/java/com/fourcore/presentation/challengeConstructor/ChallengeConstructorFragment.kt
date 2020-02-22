package com.fourcore.presentation.challengeConstructor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fourcore.NavFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.fourcore.R

/**
 * A simple [Fragment] subclass.
 */
class ChallengeConstructorFragment : NavFragment() {
    val viewModel: ChallengeConstructorViewModel by viewModel()
    override fun layoutId() = R.layout.fragment_challenge_constructor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge_constructor, container, false)
    }
}
