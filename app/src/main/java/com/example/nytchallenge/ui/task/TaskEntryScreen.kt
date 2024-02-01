package com.example.nytchallenge.ui.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nytchallenge.R
import com.example.nytchallenge.TaskTopAppBar
import com.example.nytchallenge.ui.AppViewModelProvider
import com.example.nytchallenge.ui.nav.NavigationDestination
import com.example.nytchallenge.ui.theme.NYTChallengeTheme
import kotlinx.coroutines.launch

object TaskEntryDestination: NavigationDestination {
    override val route = "task_entry"
    override val titleRes = R.string.task_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TaskTopAppBar(
                title = stringResource(TaskEntryDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        TaskEntryBody(
            taskUiState = viewModel.taskUiState,
            onValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveTask()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEntryBody(
    taskUiState: TaskUiState,
    modifier: Modifier = Modifier,
    onValueChange: (TaskDetails) -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        OutlinedTextField(
            value = taskUiState.taskDetails.task,
            onValueChange = { onValueChange(taskUiState.taskDetails.copy(task = it)) },
            label = { R.string.task_label },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            singleLine = true
        )
        //save button
        Button(
            onClick = onSaveClick,
            enabled = taskUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    NYTChallengeTheme {
        TaskEntryBody(
            taskUiState = TaskUiState(
                taskDetails = TaskDetails(
                    task = "Task 1"
                )
            ),
            onValueChange = {},
            onSaveClick = {}
        )
    }
}