package com.sohaib.navigationcomponents.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.sohaib.navigationcomponents.databinding.FragmentNotificationsBinding

class FragmentNotifications : Fragment() {

    private var binding: FragmentNotificationsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNotificationsBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillList()
    }

    private fun fillList() {
        val notificationList: ArrayList<String> = ArrayList()
        for (i in 1..50) {
            notificationList.add("Notification # $i")
        }
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, notificationList)
        binding?.lvNotifications?.adapter = arrayAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}