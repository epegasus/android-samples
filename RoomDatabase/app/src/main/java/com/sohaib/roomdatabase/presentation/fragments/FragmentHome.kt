package com.sohaib.roomdatabase.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sohaib.roomdatabase.R
import com.sohaib.roomdatabase.data.dataSource.model.Note
import com.sohaib.roomdatabase.databinding.FragmentHomeBinding
import com.sohaib.roomdatabase.interfaces.OnItemClick
import com.sohaib.roomdatabase.presentation.adapters.AdapterHome
import com.sohaib.roomdatabase.presentation.viewModels.FragmentHomeViewModel
import com.sohaib.roomdatabase.utils.Constants.TAG

class FragmentHome : Fragment(), OnItemClick {

    private var binding: FragmentHomeBinding? = null
    private var adapterHome: AdapterHome? = null

    private lateinit var viewModel: FragmentHomeViewModel

    private val navController: NavController get() = findNavController()

    private fun initializations() {
        viewModel = ViewModelProvider(this)[FragmentHomeViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializations()
        initializeRecyclerView()
        fillData()

        binding!!.fabAddHome.setOnClickListener { onAddClick() }
    }

    private fun initializeRecyclerView() {
        adapterHome = AdapterHome(this)
        binding!!.rvHome.adapter = adapterHome
    }

    private fun fillData() {
        Log.d(TAG, "fillData: called fill")
        val observer: Observer<List<Note>> = Observer {
            Log.d(TAG, "fillData: called $it")
            adapterHome?.submitList(it)
        }
        viewModel.noteList.observe(viewLifecycleOwner, observer)
    }

    private fun onAddClick() {
        if (navController.currentDestination?.id == R.id.fragmentHome) {
            navController.navigate(R.id.action_fragmentHome_to_fragmentAdd)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onItemClickListener(note: Note) {
        if (navController.currentDestination?.id == R.id.fragmentHome) {
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentAdd(note)
            navController.navigate(action)
        }
    }
}