package com.fourcore.presentation.challengeConstructor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.fourcore.NavFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.fourcore.R
import com.fourcore.global.util.showShortToast
import kotlinx.android.synthetic.main.fragment_challenge_constructor.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ChallengeConstructorFragment : NavFragment() {
    val viewModel: ChallengeConstructorViewModel by viewModel()
    lateinit var deadline: Date

    override fun layoutId() = R.layout.fragment_challenge_constructor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenge_constructor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createChallengeB.setOnClickListener{
            viewModel.createChallenge(
                PresentationChallenge(
                    challengeNameEt.text.toString(),
                    challengeDescriptionEt.text.toString(),
                    deadline
                )
                )
        }
        viewModel.challengeNotValidEvent.observe(viewLifecycleOwner, Observer {
            showShortToast(context!!, it)
        })
    }


}
