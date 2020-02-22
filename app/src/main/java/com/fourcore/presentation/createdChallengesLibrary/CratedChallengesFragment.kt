package com.fourcore.presentation.createdChallengesLibrary


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fourcore.NavFragment

import com.fourcore.R
import kotlinx.android.synthetic.main.fragment_crated_challenges.*

/**
 * A simple [Fragment] subclass.
 */
class CratedChallengesFragment : NavFragment() {
    override fun layoutId() = R.layout.fragment_crated_challenges

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
