package com.sohaib.roomdatabase.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sohaib.roomdatabase.data.dataSource.model.Note
import com.sohaib.roomdatabase.data.repository.AppRepository

class FragmentHomeViewModel(application: Application) : AndroidViewModel(application) {

    var noteList: LiveData<List<Note>>

    private var repository: AppRepository = AppRepository(application.applicationContext)

    init {
        noteList = repository.noteList
    }
}