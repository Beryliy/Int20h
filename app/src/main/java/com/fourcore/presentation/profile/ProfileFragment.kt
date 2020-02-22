package com.fourcore.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.fourcore.NavFragment
import com.fourcore.R
import com.fourcore.presentation.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : NavFragment() {
    override fun layoutId(): Int = R.layout.fragment_profile

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            profileNameTextView.text = "Name: ${it.name}"
            profileScoreTextView.text = "Name: ${it.points}"
            profilePhoneTextView.text = "Name: ${it.phoneNumber}"
        })
        profileLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        viewModel.updateUser()
    }
}