package com.sohaib.roomdatabase.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sohaib.roomdatabase.R
import com.sohaib.roomdatabase.databinding.FragmentAddRecordBinding
import com.sohaib.roomdatabase.presentation.viewModels.FragmentAddRecordViewModel
import com.google.android.material.appbar.MaterialToolbar

class FragmentAddRecord : Fragment() {

    private var binding: FragmentAddRecordBinding? = null
    private val args: FragmentAddRecordArgs by navArgs()
    private lateinit var viewModel: FragmentAddRecordViewModel

    private fun initializations() {
        viewModel = ViewModelProvider(this)[FragmentAddRecordViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddRecordBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializations()
        setUI()

        binding!!.btnSubmitAddRecord.setOnClickListener { onAddClick() }
    }

    private fun setUI() {
        if (args.note != null) {
            val toolbar: MaterialToolbar? = activity?.findViewById(R.id.toolbar_Main)
            toolbar?.title = "Update Record"

            args.note.let {
                binding?.apply {
                    etTextAddRecord.setText(it?.text)
                }
            }
        }
    }

    private fun onAddClick() {
        val text: String = binding!!.etTextAddRecord.text.toString()
        if (text.isEmpty()) {
            Toast.makeText(context, "Field can't be empty", Toast.LENGTH_SHORT).show()
            return
        }
        val added: Boolean = viewModel.addNote(text)
        if (added) {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}