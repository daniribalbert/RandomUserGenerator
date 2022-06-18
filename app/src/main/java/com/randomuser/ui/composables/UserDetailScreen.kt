package com.randomuser.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.randomuser.R
import com.randomuser.ui.RandomUserGenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun UserDetailScreen(username: String) {
    val viewModel = getViewModel<RandomUserGenViewModel>()

    val userDetails = viewModel.userDetailsFlow.collectAsState()
    viewModel.loadSingleUserDetails(username)

    userDetails.value?.let { user ->
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AsyncImage(
                model = user.image,
                contentDescription = user.name,
                modifier = Modifier.fillMaxWidth()
            )
            Column(Modifier.padding(8.dp)) {
                Text(text = user.name, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Text(
                    text = stringResource(id = R.string.address),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(text = "${user.city}, ${user.country}", fontSize = 16.sp)
                Text(text = user.street, fontSize = 16.sp)
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    ) {
                        append(stringResource(id = R.string.contact_cell))
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    ) {
                        append(user.cell)
                    }
                })
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    ) {
                        append(stringResource(id = R.string.contact_email))
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    ) {
                        append(user.email)
                    }
                })
            }
        }
    }
}
