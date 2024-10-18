package com.example.newsapp11.presentaion.screen

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp11.R
import com.example.newsapp11.presentaion.event.ArticleEvent
import com.example.newsapp11.presentaion.screen.component.ArticleTopAppBar
import com.example.newsapp11.presentaion.state.ArticleState

@Composable
fun ArticleScreen(
    state: ArticleState,
    onEvent: (ArticleEvent) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val browseLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Handle the result if needed
        }
    val shareLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Handle the result if needed
        }




    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        state.article.let {
            ArticleTopAppBar(state.bookmark, onBackClicked = {
                navigateUp()
            }, onShareClicked = {

                val articleUrl = state.article?.url
                if (articleUrl != null) {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, articleUrl)
                        type = "text/plain"
                    }
                    shareLauncher.launch(sendIntent)
                }
            }, onBookmarkClicked = {
                onEvent(ArticleEvent.UpdateBookmark)
            },
                onBrowseClicked = {
                    val articleUrl = state.article?.url
                    if (articleUrl != null) {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl))
                            browseLauncher.launch(intent)
                        } catch (e: Exception) {
                            Log.e("BrowseClick", "Error opening URL", e)
                        }
                    }
                },
//                onBrowseClicked = {
//                    Log.d("TAG", "ArticleScreen: onBrowseClicked ")
//                    val articleUrl = state.article?.url
//                    if (articleUrl != null) {
//                        try {
//                            Log.d("TAG", "ArticleScreen: article $articleUrl")
//                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl))
//                            // Optional: Specify package name for explicit intent (e.g., Chrome)
////                            intent.setPackage("com.android.chrome")
//                            browseLauncher.launch(intent)
//                        } catch (e: Exception) {
//                            Log.e("BrowseClick", "Error opening URL", e)
//                            // Show an error message to the user (e.g., using a Snackbar)
//                            scope.launch {
//                                scaffoldState.snackbarHostState.showSnackbar(
//                                    message = "Could not open URL"
//                                )
//                            }
//                        }
//                    } else {
//                        // Handle case where articleUrl is null
//                        scope.launch {
//                            scaffoldState.snackbarHostState.showSnackbar(
//                                message = "No URL available"
//                            )
//                        }
//                    }
//                }
                )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(
                    start = 16.dp, top = 16.dp, end = 16.dp
                )
            ) {
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(context = context)
                            .data(state.article?.urlToImage).build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(248.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.article?.title ?: "title not found",
                        style = MaterialTheme.typography.displaySmall,
                        color = colorResource(id = R.color.black)
                    )
                    Text(
                        text = state.article?.content ?: "content not found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.black)
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp, 12.dp),
                        onClick = {
                            val articleUrl = state.article?.url
                            if (articleUrl != null) {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl))
                                    browseLauncher.launch(intent)
                                } catch (e: Exception) {
                                    Log.e("BrowseClick", "Error opening URL", e)
                                }
                            }
                        },
                    ) {
                        Row() {
                            Icon(
                                painter = painterResource(R.drawable.ic_network),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text("Read full Article")
                        }
                    }

                }
            }
        }
    }

}