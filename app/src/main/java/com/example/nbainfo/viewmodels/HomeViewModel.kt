package com.example.nbainfo.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nbainfo.models.SortCriteria

class HomeViewModel: ViewModel() {
    // state for the sort text
    private val _sortText = mutableStateOf("Name")
    val sortText: State<String> = _sortText

    fun setSort(sort: SortCriteria) {
        _sortText.value = sort.toString()
    }
}