package org.leftbrained.uptaskapp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.leftbrained.uptaskapp.classes.Stats

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsActivity(navController: NavController, userId: Int) {
    val sharedPref = LocalContext.current.getSharedPreferences("logs", Context.MODE_PRIVATE)
    val pickerState = rememberDateRangePickerState()
    var currentStats by remember { mutableStateOf(Stats()) }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.stats)) }, navigationIcon = {
                IconButton(
                    onClick = {
                        navController.navigate("taskList/$userId")
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "Back icon"
                    )
                }
            }, colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                Modifier
                    .height(500.dp)
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                DateRangePicker(
                    state = pickerState
                )
            }
            Button(
                onClick = {
                    val startDate =
                        Instant.fromEpochMilliseconds(pickerState.selectedStartDateMillis!!)
                            .toLocalDateTime(
                                TimeZone.currentSystemDefault()
                            )
                    val endDate = Instant.fromEpochMilliseconds(pickerState.selectedEndDateMillis!!)
                        .toLocalDateTime(
                            TimeZone.currentSystemDefault()
                        )
                    currentStats = currentStats.checkStats(sharedPref, startDate, endDate, userId)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.apply))
            }
            Column(Modifier.padding(16.dp)) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.stats_period),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    stringResource(R.string.total_tasks_added, currentStats.totalTasks)
                )
                Text(
                    stringResource(R.string.total_tasks_completed, currentStats.completedTasks)
                )
                Text(
                    stringResource(R.string.total_tasks_undone, currentStats.undoneTasks)
                )
            }
        }
    }
}