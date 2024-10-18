package com.example.newsapp11.presentaion.utils



sealed class Category(val name: String) {
    data object Business : Category("business")
    data object Entertainment : Category("entertainment")
    data object General : Category("general")
    data object Health : Category("health")
    data object Science : Category("science")
    data object Sports : Category("sports")
    data object Technology : Category("technology")
    companion object {
        fun toList(): List<Category> {
            return listOf(
                Business,
                Entertainment,
                General,
                Health,
                Science,
                Sports,
                Technology
            )
        }
    }
}

