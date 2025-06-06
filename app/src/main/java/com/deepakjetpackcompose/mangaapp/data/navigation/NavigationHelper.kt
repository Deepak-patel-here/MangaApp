package com.deepakjetpackcompose.mangaapp.data.navigation

sealed class NavigationHelper(val route:String) {
    object HomeScreen: NavigationHelper(route = "home")
    object ChapterScreen: NavigationHelper(route = "chapter")
    object ReadScreen: NavigationHelper(route = "read")
    object SearchScreen: NavigationHelper(route = "search")
    object MyApp: NavigationHelper(route = "app")
}