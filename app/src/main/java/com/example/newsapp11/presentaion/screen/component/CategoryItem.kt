package com.example.newsapp11.presentaion.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp11.presentaion.utils.Category

@Composable
fun CategoryItem(category: Category, selected: Boolean = false, onClick: () -> Unit) {
    TextButton(

        onClick = onClick,

        ) {
        Text(
            text = category.name,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .background(
                    color = if (selected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CategoryItemPrev() {
    var selected = true
    Column {
        CategoryItem(category = Category.Health, selected) {
            selected = !selected
        }
        CategoryItem(category = Category.Health, !selected) {
            selected = !selected
        }
    }
}