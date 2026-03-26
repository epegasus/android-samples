package com.sohaib.navigationcomponents.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sohaib.navigationcomponents.R
import com.sohaib.navigationcomponents.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnProfileHome?.setOnClickListener { onProfileClick() }
    }

    private fun onProfileClick() {
        if (findNavController().currentDestination?.id == R.id.fragmentHome)
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentProfile)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}