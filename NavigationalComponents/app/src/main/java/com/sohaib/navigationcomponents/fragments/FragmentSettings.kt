package com.sohaib.navigationcomponents.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sohaib.navigationcomponents.R
import com.sohaib.navigationcomponents.databinding.FragmentSettingsBinding

class FragmentSettings : Fragment() {

    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSettingsHome?.setOnClickListener { onSettingsClick() }
    }

    private fun onSettingsClick() {
        if (findNavController().currentDestination?.id == R.id.fragmentSettings)
            findNavController().navigate(R.id.action_fragmentSettings_to_fragmentAbout)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}