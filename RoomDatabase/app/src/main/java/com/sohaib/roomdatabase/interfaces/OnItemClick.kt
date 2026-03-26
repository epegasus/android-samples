package com.sohaib.roomdatabase.interfaces

import com.sohaib.roomdatabase.data.dataSource.model.Note

interface OnItemClick {
    fun onItemClickListener(note: Note)
}