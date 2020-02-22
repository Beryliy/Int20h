package com.fourcore.presentation.login.user_info

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.fourcore.MainActivity
import com.fourcore.NavFragment
import com.fourcore.R
import kotlinx.android.synthetic.main.fragment_user_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoFragment : NavFragment() {

    private val args by navArgs<UserInfoFragmentArgs>()
    override fun layoutId(): Int = R.layout.fragment_user_info

    private val viewModel by viewModel<UserInfoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.createUserResultLiveData.observe(viewLifecycleOwner, Observer {
            if(it) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
        })
        userInfoNextTextView.setOnClickListener {
            val userName = userInfoNameEditText.text.toString()
            if(userName.isEmpty()) {
                Toast.makeText(requireContext(), "Enter user name", Toast.LENGTH_LONG).show()
            }else {
                viewModel.createUser(userName, args.userPhone)
            }
        }
    }
}