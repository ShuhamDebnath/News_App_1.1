package com.example.newsapp11.presentaion.navigatoin


import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp11.R
import com.example.newsapp11.presentaion.navigatoin.component.BottomNavigationItem
import com.example.newsapp11.presentaion.navigatoin.component.NewsBottomNavigation
import com.example.newsapp11.presentaion.screen.ArticleScreen
import com.example.newsapp11.presentaion.screen.BookmarkScreen
import com.example.newsapp11.presentaion.screen.HomeScreen
import com.example.newsapp11.presentaion.screen.SearchScreen
import com.example.newsapp11.presentaion.screen.TestScreen
import com.example.newsapp11.presentaion.viewmodel.MainViewModel

@Composable
fun NewsNavHost() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"),
//            BottomNavigationItem(icon = R.drawable.ic_launcher_foreground, text = "Test")
        )
    }
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Screen.HomeScreen.route -> 0
            Screen.SearchScreen.route -> 1
            Screen.BookmarkScreen.route -> 2
            Screen.TestScreen.route -> 3
            else -> 0
        }
    }


    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Screen.HomeScreen.route ||
                backStackState?.destination?.route == Screen.SearchScreen.route ||
                backStackState?.destination?.route == Screen.BookmarkScreen.route ||
                backStackState?.destination?.route == Screen.TestScreen.route
    }


    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClicked = { index: Int ->
                        when (index) {
                            0 -> navigateToTap(
                                navController = navController,
                                route = Screen.HomeScreen.route
                            )

                            1 -> navigateToTap(
                                navController = navController,
                                route = Screen.SearchScreen.route
                            )

                            2 -> navigateToTap(
                                navController = navController,
                                route = Screen.BookmarkScreen.route
                            )
                            3 -> navigateToTap(
                                navController = navController,
                                route = Screen.TestScreen.route
                            )
                        }

                    }
                )
            }
        },

        content = {
            val bottomPadding = it.calculateBottomPadding()
            NavHost(
                navController = navController,
                startDestination = Screen.HomeScreen.route,
                modifier = Modifier.padding(bottom = bottomPadding)
            ) {
                composable(route = Screen.HomeScreen.route) {
                    val state by viewModel.homeState.collectAsState()
                    HomeScreen(
                        state = state,
                        onEvent = viewModel::onHomeEvent,
                        moveToArticleScreen = {
                            Log.d("TAG", "NewsNavHost: moveToArticleScreen")
                            navController.navigate(route = Screen.ArticleScreen.route)
                        },
                        moveToSearchScreen = {
                            navController.navigate(route = Screen.SearchScreen.route)
                        }
                    )
                }

                composable(route = Screen.SearchScreen.route) {
                    val state by viewModel.searchState.collectAsState()
                    SearchScreen(
                        state = state,
                        onEvent = viewModel::onSearchEvent,
                        moveToArticleScreen = {
                            Log.d("TAG", "NewsNavHost: moveToArticleScreen")
                            navController.navigate(route = Screen.ArticleScreen.route)
                        },
                    )
                }

                composable(route = Screen.ArticleScreen.route) {
                    val state by viewModel.articleState.collectAsState()
                    ArticleScreen(
                        state = state,
                        onEvent = viewModel::onArticleEvent,
                        navigateUp = {
                            navController.navigateUp()
                        }
                    )
                }

                composable(route = Screen.BookmarkScreen.route) {
                    val state by viewModel.bookmarkState.collectAsState()
                    BookmarkScreen(
                        state = state,
                        onEvent = viewModel::onBookmarkEvent,
                        moveToArticleScreen = {
                            navController.navigate(route = Screen.ArticleScreen.route)
                        },
                        moveToSearchScreen = {
                            navController.navigate(route = Screen.SearchScreen.route)
                        }
                    )
                }
                composable(route = Screen.TestScreen.route) {
                    TestScreen()
                }
            }
        },
    )
}

private fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}
