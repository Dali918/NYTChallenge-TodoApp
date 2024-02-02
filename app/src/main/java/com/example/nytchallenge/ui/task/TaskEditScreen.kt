package com.example.nytchallenge.ui.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nytchallenge.R
import com.example.nytchallenge.TaskTopAppBar
import com.example.nytchallenge.ui.AppViewModelProvider
import com.example.nytchallenge.ui.nav.NavigationDestination
import com.example.nytchallenge.ui.theme.NYTChallengeTheme
import kotlinx.coroutines.launch

object TaskEditDestination: NavigationDestination{
    override val route = "task_edit"
    override val titleRes = R.string.task_edit_title
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TaskTopAppBar(
                title = stringResource(TaskEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        TaskEntryBody(
            taskUiState = viewModel.uiState,
            onValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTask()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskEditScreenPreview() {
    NYTChallengeTheme {
        TaskEditScreen(
            navigateBack = {},
            onNavigateUp = {}
        )
    }
}
