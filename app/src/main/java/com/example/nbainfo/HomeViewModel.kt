package com.example.nbainfo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val _sort = mutableStateOf("Name")
    val sortText: State<String> = _sort

    fun setSort(sort: String) {
        _sort.value = sort
    }

}