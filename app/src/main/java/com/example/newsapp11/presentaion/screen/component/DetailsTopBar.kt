package com.example.newsapp11.presentaion.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.newsapp11.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleTopAppBar(
    bookmark: Boolean,
    onBrowseClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onBookmarkClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    TopAppBar(
        title = { },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.teal_700),
            navigationIconContentColor = colorResource(id = R.color.teal_700)
        ),
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(painter = painterResource(R.drawable.ic_back_arrow), contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = onBookmarkClicked) {
                Icon(
                    painter = painterResource(id = if (bookmark) R.drawable.baseline_bookmark_24 else R.drawable.ic_bookmark),
                    contentDescription = null
                )
            }
            IconButton(onClick = onShareClicked) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
            IconButton(onClick = onBrowseClicked) {
                Icon(painter = painterResource(R.drawable.ic_network), contentDescription = null)
            }

        }
    )
}


