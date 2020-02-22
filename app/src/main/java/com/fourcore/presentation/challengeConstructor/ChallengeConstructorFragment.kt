package com.fourcore.presentation.challengeConstructor


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.fourcore.NavFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.fourcore.R
import com.fourcore.domain.User
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createChallengeB.setOnClickListener{
            viewModel.presentationChallenge = PresentationChallenge(
                challengeNameEt.text.toString(),
                challengeDescriptionEt.text.toString()
            )
            viewModel.createChallenge()
        }
        viewModel.initContacts()
        viewModel.challengeNotValidEvent.observe(viewLifecycleOwner, Observer {
            showShortToast(context!!, it)
        })
        viewModel.contactsInitedEvent.observe(viewLifecycleOwner, Observer {
            ArrayAdapter(context!!, R.layout.contact_item, it)
                .also {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    receiverSp.adapter = it
                }
        })
        receiverSp.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.challengeReceiver = parent!!.getItemAtPosition(position) as User
            }
        })
    }
}
