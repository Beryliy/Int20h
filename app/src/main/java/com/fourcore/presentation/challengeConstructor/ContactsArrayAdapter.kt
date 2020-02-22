package com.fourcore.presentation.challengeConstructor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.fourcore.R
import com.fourcore.domain.User
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactsArrayAdapter(
    context: Context,
    contacts: List<User>
): ArrayAdapter<User>(context, 0, contacts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = getItem(position)
        val spinnerItem = if(convertView == null) {
            LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
        } else {
            convertView
        }
        spinnerItem.contactNameTv.text = contact!!.name
        return spinnerItem
    }
}