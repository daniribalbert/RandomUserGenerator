package com.randomuser.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.randomuser.ui.RandomUserGenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun UsersListScreen(navController: NavController) {
    val viewModel = getViewModel<RandomUserGenViewModel>()
    Box(
        Modifier
            .background(Color.White)
            .padding(horizontal = 8.dp)) {
        UsersList(navController, viewModel)
    }
}
