package com.example.nytchallenge.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nytchallenge.R
import com.example.nytchallenge.TaskTopAppBar
import com.example.nytchallenge.data.TaskEntity
import com.example.nytchallenge.ui.AppViewModelProvider
import com.example.nytchallenge.ui.nav.NavigationDestination
import com.example.nytchallenge.ui.task.TaskDetails
import com.example.nytchallenge.ui.task.toTaskDetails
import com.example.nytchallenge.ui.task.toTaskEntity
import com.example.nytchallenge.ui.theme.NYTChallengeTheme
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home_title
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToTaskEdit: (Int) -> Unit,
    navigateToTaskEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    //collect home ui state as state from view model
    val homeUIState by viewModel.homeUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TaskTopAppBar(
                title = stringResource(R.string.home_title),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTaskEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_task)
                )
            }
        },
    ){
        innnerPadding->
        HomeBody(
            tasksList = homeUIState.tasksList,
            onTaskClick = navigateToTaskEdit,
            onCheckedChange = {
                coroutineScope.launch {
                    viewModel.onTaskCheckedChange(it)
                }
            },
            modifier = Modifier.padding(innnerPadding)
        )
    }

}

@Composable
fun HomeBody(
    tasksList: List<TaskEntity>,
    onTaskClick: (Int) -> Unit,
    onCheckedChange: (TaskEntity) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (tasksList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_tasks),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            TasksList(
                tasksList = tasksList.sortedWith(compareBy { it.isDone }),
                onTaskClick = { onTaskClick(it.id) },
                onCheckedChange = {onCheckedChange(it)},
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun TasksList(
    tasksList: List<TaskEntity>,
    onTaskClick: (TaskEntity) -> Unit,
    onCheckedChange: (TaskEntity) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = modifier) {
        items(items= tasksList, key = {task -> task.id}){ task->
            Task(
                task = task,
                onCheckedChange = onCheckedChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTaskClick(task) }
            )

        }
    }

}
@Composable
fun Task(
    task: TaskEntity,
    onCheckedChange: (TaskEntity) -> Unit,
    modifier: Modifier = Modifier,
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(Modifier.padding(8.dp)) {
                Checkbox(
                    task.isDone,
                    onCheckedChange = {
                        onCheckedChange(
                            task.copy(isDone = task.isDone.not())
                        )
                    }
                )
            }

            Text(
                text = task.task,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskPreview() {
    Task(
        TaskDetails(1, "Task 1", false).toTaskEntity(),
        {}
    )
}

@Preview(showBackground = true)
@Composable
fun TaskListPreview(){
    NYTChallengeTheme {
        HomeBody(
            tasksList = listOf(
                TaskDetails(1, "Task 1", true).toTaskEntity(),
                TaskDetails(2, "Task 2", false).toTaskEntity(),
                TaskDetails(3, "Task 3", false).toTaskEntity(),
            ),
            onTaskClick = {},
            onCheckedChange = {}
        )
    }
}