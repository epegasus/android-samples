package com.sohaib.navigationcomponents.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sohaib.navigationcomponents.databinding.FragmentAboutBinding

class FragmentAbout : Fragment() {

    private var binding: FragmentAboutBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAboutBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}