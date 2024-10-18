package com.example.newsapp11.presentaion.navigatoin

sealed class Screen(val route: String) {
    data object HomeScreen :Screen("homeScreen")
    data object SearchScreen :Screen("searchScreen")
    data object BookmarkScreen :Screen("bookmarkScreen")
    data object ArticleScreen :Screen("articleScreen")
    data object TestScreen :Screen("testScreen")
}