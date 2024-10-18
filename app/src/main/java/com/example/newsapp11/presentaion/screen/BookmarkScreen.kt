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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.newsapp11.R
import com.example.newsapp11.data.remote.model.Article
import com.example.newsapp11.presentaion.event.BookmarkEvent
import com.example.newsapp11.presentaion.screen.component.ArticleCard
import com.example.newsapp11.presentaion.state.BookmarkState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    state: BookmarkState,
    onEvent: (BookmarkEvent) -> Unit,
    moveToSearchScreen: () -> Unit,
    moveToArticleScreen: (Article) -> Unit,
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.loading,
        onRefresh = { onEvent(BookmarkEvent.LoadArticles) }
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
                Text(text = "My Bookmark", style = MaterialTheme.typography.titleLarge)
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
            }

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
                                onEvent(BookmarkEvent.OnArticleClicked(article))
                                moveToArticleScreen(article)
                            }
                        )
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