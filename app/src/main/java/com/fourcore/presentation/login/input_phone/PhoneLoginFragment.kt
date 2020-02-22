package com.fourcore.presentation.login.input_phone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.fourcore.MainActivity
import com.fourcore.NavFragment
import com.fourcore.R
import com.fourcore.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import org.koin.android.ext.android.get
import kotlin.coroutines.CoroutineContext

class PhoneLoginFragment : NavFragment(), CoroutineScope {
    override fun layoutId(): Int = R.layout.activity_splash

    companion object {
        private const val RC_SIGN_IN = 23
    }

    private val providers = arrayListOf(AuthUI.IdpConfig.PhoneBuilder().build())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser == null) {
            startLoginScreen()
        } else {
            launch {
                withContext(Dispatchers.IO) { get<UserRepository>().initCurrentUser(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!) }
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }

        }
    }

    private fun startLoginScreen() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    navController.navigate(
                        PhoneLoginFragmentDirections.actionInputPhoneFragmentToUserInfoFragment(
                            user.phoneNumber!!
                        )
                    )
                } else {
                    Toast.makeText(requireContext(), "Login error", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(), response?.error?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}