package com.randomuser.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.randomuser.R
import com.randomuser.navigation.Destinations
import com.randomuser.ui.RandomUserGenViewModel
import com.randomuser.ui.model.User
import timber.log.Timber

private const val PAGINATION_OFFSET = 3

@Composable
fun UsersList(navController: NavController, viewModel: RandomUserGenViewModel) {
    val userListFlow = viewModel.usersListFlow.collectAsState()
    val errorFlow = viewModel.errorFlow.collectAsState()
    val listState = rememberLazyListState()

    val usersList = userListFlow.value?.users ?: emptyList()
    val currentPage = userListFlow.value?.page
    if (currentPage == null && errorFlow.value == null) {
        viewModel.loadRandomUsersPage()
    }

    errorFlow.value?.let {
        Timber.w(it, "Error!")
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.generic_error))
        }
    }

    userListFlow.value?.let {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(count = usersList.size) { index ->
                if (!userListFlow.value?.users.isNullOrEmpty()) {
                    val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                        ?: -1
                    if (lastVisibleItem >= usersList.lastIndex - PAGINATION_OFFSET && currentPage != null) {
                        viewModel.loadRandomUsersPage(currentPage + 1)
                    }
                }
                UserCard(usersList[index], navController)
            }
        }
    }
}

@Composable
fun UserCard(model: User, navController: NavController) {
    Row(
        Modifier
            .background(Color.White)
            .shadow(elevation = 2.dp)
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate(route = "${Destinations.USER_DETAIL.destination}/${model.username}") })
    ) {
        AsyncImage(
            model = model.thumbnail,
            contentDescription = model.name,
            modifier = Modifier.size(60.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(model.name)
            Text(model.location)
        }
    }
}
