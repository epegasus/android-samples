package com.sohaib.composeretrofit.helpers.pagination.interfaces

/**
 * @property T: Type of Object for pagination
 */

interface PaginationCallbacks<T> {
    fun onPreload()
    fun onLoaded(subList: List<T>)
    fun onCompleted()
}