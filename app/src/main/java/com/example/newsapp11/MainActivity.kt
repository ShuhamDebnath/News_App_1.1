package com.example.newsapp11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp11.presentaion.navigatoin.NewsNavHost
import com.example.newsapp11.ui.theme.NewsApp11Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsApp11Theme {
                Scaffold{ paddingValues ->
                    val topPadding = paddingValues.calculateTopPadding()
                    NewsNavHost(modifier = Modifier
//                        .padding(top =  topPadding)
                    )
                }
            }
        }
    }
}