package com.example.newsapp11.presentaion.utils

sealed class Language(val code: String) {
    data object Arabic : Language("ar")
    data object German : Language("de")
    data object English : Language("en")
    data object Spanish : Language("es")
    data object French : Language("fr")
    data object Hebrew : Language("he")
    data object Italian : Language("it")
    data object Dutch : Language("nl")
    data object Norwegian : Language("no")
    data object Portuguese : Language("pt")
    data object Russian : Language("ru")
    data object Swedish : Language("sv")
    data object Ukrainian : Language("ud")
    data object Chinese : Language("zh")
}
