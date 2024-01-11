package org.leftbrained.uptaskapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DatabaseStateViewmodel: ViewModel() {
    var databaseState by mutableIntStateOf(1)
    var sortingCriteria by mutableStateOf(SortingCriteria.None)
}