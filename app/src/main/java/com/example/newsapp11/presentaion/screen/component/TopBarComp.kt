package com.example.newsapp11.presentaion.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.newsapp11.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComp(
    modifier: Modifier = Modifier
//        .height(20.dp)
    ,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchClicked: () -> Unit,
    onMoreVertClicked: () -> Unit,
) {
    TopAppBar(
        windowInsets = WindowInsets(top = 0.dp),
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp).clip(RoundedCornerShape(16.dp)),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(text = "My News App", style = MaterialTheme.typography.titleLarge)
        },
        actions = {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "search",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onSearchClicked()
                    })
            Spacer(
                modifier = Modifier.size(12.dp)
            )
            Icon(imageVector = Icons.Default.MoreVert,
                contentDescription = "ic_newspaper",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onMoreVertClicked()
                    })

        },
        scrollBehavior = scrollBehavior,

        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_newspaper),
                contentDescription = "ic_newspaper",
                modifier = Modifier.size(24.dp)
            )

        }
    )


}