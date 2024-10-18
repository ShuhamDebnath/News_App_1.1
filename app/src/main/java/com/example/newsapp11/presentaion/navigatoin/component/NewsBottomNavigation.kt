package com.example.newsapp11.presentaion.navigatoin.component

import android.content.res.Configuration
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp11.R

@Composable
fun NewsBottomNavigation(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClicked: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = index == selected,
                onClick = {
                    Log.d("TAG", "NewsBottomNavigation:  index $index")
                    onItemClicked(index)
                          },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = bottomNavigationItem.icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = bottomNavigationItem.text,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = colorResource(R.color.teal_700),
                    unselectedTextColor = colorResource(R.color.teal_700),
                    indicatorColor = MaterialTheme.colorScheme.background,
                )

            )
        }

    }

}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NewsBottomNavigationPreview() {
    val items = listOf(
        BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
        BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
        BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"),
        BottomNavigationItem(icon = R.drawable.ic_launcher_foreground, text = "Test")
    )
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            NewsBottomNavigation(items= items, selected = 0, onItemClicked = {})
        }


}