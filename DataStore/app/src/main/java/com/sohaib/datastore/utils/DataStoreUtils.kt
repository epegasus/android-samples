package com.sohaib.datastore.utils


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sohaib.datastore.SearchRequest
import com.sohaib.datastore.proto.ProtoPreferenceManager

// Singleton for Proto DataStore
val Context.protoDataStore: DataStore<SearchRequest> by dataStore(
    fileName = "search_request.pb",
    serializer = ProtoPreferenceManager.SearchRequestSerializer
)

// Singleton for Preferences DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "preferencesDataStore"
)