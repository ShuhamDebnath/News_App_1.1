package com.example.newsapp11.presentaion.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.newsapp11.R
import com.example.newsapp11.data.remote.model.Article
import com.example.newsapp11.presentaion.event.SearchEvent
import com.example.newsapp11.presentaion.screen.component.ArticleCard
import com.example.newsapp11.presentaion.state.SearchState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    moveToArticleScreen: (Article) -> Unit,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {


        val pullRefreshState = rememberPullRefreshState(
            refreshing = state.loading,
            onRefresh = { onEvent(SearchEvent.OnSearch) }
        )

        val listState = rememberLazyListState()

        // Check if we've scrolled to the end of the list
        val isAtEnd = remember {
            derivedStateOf {
                val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
                // Check if last visible item is the last item in the list
                visibleItemsInfo.isNotEmpty() &&
                        visibleItemsInfo.lastOrNull()?.index == state.articles.lastIndex
            }
        }

        // Trigger loading more when reaching the end of the list
        LaunchedEffect(isAtEnd.value) {
            if (isAtEnd.value) {
                onEvent(SearchEvent.IncreasePage)
                onEvent(SearchEvent.OnSearch)
            }
        }



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_newspaper),
                contentDescription = "ic_newspaper",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(text = "My Search", style = MaterialTheme.typography.titleLarge)
            Spacer(
                modifier = Modifier.size(12.dp)
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.search,
            onValueChange = {
                onEvent(SearchEvent.UpdateSearch(it))
            },
            label = {
                Text(text = "Search")
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search",
                    modifier = Modifier.clickable {
                        focusManager.clearFocus()
                        onEvent(SearchEvent.OnSearch)
                    }
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                onEvent(SearchEvent.OnSearch)

            })

        )
        Spacer(modifier = Modifier.size(12.dp))



        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(state.articles) { article ->
                    ArticleCard(article = article,
                        onClick = {
                            Log.d("TAG", "HomeScreen: ArticleCard  onClick")
                            onEvent(SearchEvent.OnArticleClicked(article))
                            moveToArticleScreen(article)
                        })
                }
            }


            PullRefreshIndicator(
                refreshing = state.loading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = if (state.loading) Color.Red else Color.Green,
            )
        }
    }

}



