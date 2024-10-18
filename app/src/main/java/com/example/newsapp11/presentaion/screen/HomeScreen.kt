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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp11.R
import com.example.newsapp11.data.remote.model.Article
import com.example.newsapp11.presentaion.event.HomeEvent
import com.example.newsapp11.presentaion.screen.component.ArticleCard
import com.example.newsapp11.presentaion.screen.component.CategoryItem
import com.example.newsapp11.presentaion.state.HomeState
import com.example.newsapp11.presentaion.utils.Category

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    moveToSearchScreen: () -> Unit,
    moveToArticleScreen: (Article) -> Unit,
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.loading,
        onRefresh = { onEvent(HomeEvent.LoadArticles) }
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {

        Column {

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
                Text(text = "My News App", style = MaterialTheme.typography.titleLarge)
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .size(12.dp)
                )
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "search",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            moveToSearchScreen()
                        })
                Spacer(
                    modifier = Modifier.size(12.dp)
                )
                Icon(imageVector = Icons.Default.MoreVert,
                    contentDescription = "ic_newspaper",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onEvent(HomeEvent.UpdateMoreVert)
                        })

                DropdownMenu(
                    expanded = state.moreVert, onDismissRequest = {
                        onEvent(HomeEvent.UpdateMoreVert)
                    }, modifier = Modifier
//                        .align(Alignment.TopEnd)
                        .width(200.dp)
                ) {
                    DropdownMenuItem(text = {
                        Text(text = "Country")
                    }, onClick = {
                        onEvent(HomeEvent.UpdateShowCountry)
                    })
                    DropdownMenuItem(text = {
                        Text(text = "Language")
                    }, onClick = {
                        onEvent(HomeEvent.UpdateShowLanguage)
                    })
                }
            }

            LazyRow(modifier = Modifier.clickable {
                Log.d("TAG", "HomeScreen: ")
            })
            {

                items(Category.toList()) { category ->
                    CategoryItem(category = category, selected = state.category == category) {
                        onEvent(HomeEvent.OnCategoryClicked(category))
                    }
                    Spacer(modifier.size(12.dp))
                }
            }

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
                    onEvent(HomeEvent.IncreasePage)
                    onEvent(HomeEvent.LoadArticles)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn(Modifier.fillMaxSize(), state = listState) {
                    items(state.articles) { article ->
                        ArticleCard(article = article,
                            onClick = {
                                Log.d("TAG", "HomeScreen: ArticleCard  onClick")
                                onEvent(HomeEvent.OnArticleClicked(article))
                                moveToArticleScreen(article)
                            })
                    }

                    // Optional: Add a loading item at the bottom if you're fetching more content
                    item {
                        CircularProgressIndicator(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentSize(Alignment.Center))
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


}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(state = HomeState(), onEvent = {}, moveToSearchScreen = {}, moveToArticleScreen = {})
}

