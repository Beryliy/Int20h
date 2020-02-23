package com.fourcore.presentation.challengeConstructor


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.WorkManager
import com.fourcore.NavFragment
import com.fourcore.ProgressDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.fourcore.R
import com.fourcore.domain.User
import com.fourcore.global.util.showShortToast
import kotlinx.android.synthetic.main.fragment_challenge_constructor.*
import org.koin.android.ext.android.get
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ChallengeConstructorFragment : NavFragment() {
    val viewModel: ChallengeConstructorViewModel by viewModel()
    lateinit var deadline: Date

    override fun layoutId() = R.layout.fragment_challenge_constructor
    private val loadingDialog = ProgressDialogFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            if(it)loadingDialog.show(parentFragmentManager, "Sfs") else {
                loadingDialog.dismissAllowingStateLoss()
                navController.popBackStack()
            }
        })
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
            ContactsArrayAdapter(context!!, it)
                .also { it1 ->
                    it1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    receiverSp.adapter = it1
                }
        })
        viewModel.workerRequestEvent.observe(viewLifecycleOwner, Observer {
            WorkManager.getInstance(get()).enqueue(it)
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
        deadlineB.setOnClickListener{
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
            DatePickerDialog(
                context!!,
                object: DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        viewModel.changeDeadlineDate(year, month, dayOfMonth)
                        showTimepickerDialog()
                    }
                },
                viewModel.deadlineCalendar.get(Calendar.YEAR),
                viewModel.deadlineCalendar.get(Calendar.MONTH),
                viewModel.deadlineCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
    }

    private fun showTimepickerDialog(){
            TimePickerDialog(
                context!!,
                TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                    viewModel.changeDeadlineTime(hourOfDay, minute)
                },
                viewModel.deadlineCalendar.get(Calendar.HOUR_OF_DAY),
                viewModel.deadlineCalendar.get(Calendar.MINUTE),
                true
            ).show()
    }
}
