package com.sohaib.roomdatabase.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sohaib.roomdatabase.data.dataSource.model.Note
import com.sohaib.roomdatabase.data.repository.AppRepository

class FragmentAddRecordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepository = AppRepository(application.applicationContext)

    fun addNote(text: String): Boolean {
        val note = Note(text, System.currentTimeMillis())
        return repository.addNote(note)
    }
}