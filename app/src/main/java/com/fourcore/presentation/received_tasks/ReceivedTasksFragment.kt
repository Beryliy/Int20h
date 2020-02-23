package com.fourcore.presentation.received_tasks

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.fourcore.NavFragment
import com.fourcore.ProgressDialogFragment
import com.fourcore.R
import com.fourcore.domain.Challenge
import kotlinx.android.synthetic.main.fragment_received_tasks.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReceivedTasksFragment : NavFragment() {
    override fun layoutId(): Int = R.layout.fragment_received_tasks

    companion object {
        private const val CAMERA_REQUEST = 323
    }

    private val viewModel by viewModel<ReceivedTasksViewModel>()

    private val loadingDialog = ProgressDialogFragment()


    private var choosedChallenge: Challenge? = null

    private val adapter by lazy {
        ReceivedTasksAdapter(ArrayList()) {
            choosedChallenge = it
            openCameraScreen()
        }
    }

    private fun openCameraScreen() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            choosedChallenge?.let {
                viewModel.createChallengePerform(it, photo)
                adapter.removeItem(it)
            }
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
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            if(it) loadingDialog.show(parentFragmentManager, "sadas") else loadingDialog.dismissAllowingStateLoss()
        })
    }
}