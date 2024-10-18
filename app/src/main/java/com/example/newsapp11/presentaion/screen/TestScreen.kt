package com.example.newsapp11.presentaion.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp11.presentaion.screen.component.PullToRefreshLazyColumn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TestScreen(modifier: Modifier = Modifier) {
    var items = remember {
        mutableStateListOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    }
    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    Text(text = "Test Screen")
    Spacer(Modifier.size(12.dp))

    Box(modifier = Modifier.fillMaxSize()) {

        PullToRefreshLazyColumn(
            items = items,
            content = {
                Column {
                    Text(text = it, modifier = Modifier.padding(12.dp))
                }
            },
            refresh = isRefreshing,
            onRefresh = {
                Log.d("TAG", "TestScreen: onRefresh called")
                scope.launch {
                    isRefreshing = true
                    delay(3000)
                    items.add("Item ${items.size + 1}")
                    Log.d("TAG", "TestScreen: items ${items.size}")
                    isRefreshing = false
                }
            }
        )

        Button(
            onClick = {
                isRefreshing = true
            },
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        ) {
            Text(text = "Refresh")
        }

    }


}