package com.deepakjetpackcompose.mangaapp.data.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.deepakjetpackcompose.mangaapp.presentation.screens.HomeScreen
import com.deepakjetpackcompose.mangaapp.presentation.screens.MangaChapterScreen
import com.deepakjetpackcompose.mangaapp.presentation.screens.MyApp
import com.deepakjetpackcompose.mangaapp.presentation.screens.ReadScreen
import com.deepakjetpackcompose.mangaapp.presentation.screens.SearchScreen
import com.deepakjetpackcompose.mangaapp.presentation.screens.SeeAllScreen
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun Navigation(modifier: Modifier = Modifier, mangaViewModel: MangaViewModel) {
    val navController= rememberNavController()

    NavHost(navController = navController, startDestination = NavigationHelper.MyApp.route) {

        composable (route = NavigationHelper.HomeScreen.route){
            HomeScreen(mangaViewModel = mangaViewModel, navController = navController)
        }

        composable (route= "${NavigationHelper.ChapterScreen.route}/{imgUrl}/{title}/{description}/{mangaId}",
            arguments = listOf(
                navArgument ("imgUrl"){type= NavType.StringType  },
                navArgument ("title"){type= NavType.StringType  },
                navArgument ("description"){type= NavType.StringType  },
                navArgument ("mangaId"){type= NavType.StringType  }
            ) ){backStackEntry->
            val imgUrl=backStackEntry.arguments?.get("imgUrl").toString()?:""
            val title=backStackEntry.arguments?.get("title").toString()?:""
            val desc=backStackEntry.arguments?.get("description").toString()?:""
            val mangaId=backStackEntry.arguments?.get("mangaId").toString()?:""

            val decodedImage= URLDecoder.decode(imgUrl,StandardCharsets.UTF_8.toString())
            val decodedTitle= URLDecoder.decode(title,StandardCharsets.UTF_8.toString())
            val decodedDesc= URLDecoder.decode(desc,StandardCharsets.UTF_8.toString())
            val decodedId= URLDecoder.decode(mangaId,StandardCharsets.UTF_8.toString())
            MangaChapterScreen(mangaViewModel = mangaViewModel, navController=navController, title = decodedTitle, imgUrl = decodedImage, desc = decodedDesc, mangaId = decodedId)
        }

        composable (route = "${NavigationHelper.ReadScreen.route}/{id}",
            arguments = listOf(
                navArgument("id") {type= NavType.StringType  }
            )){backStackEntry->
            val id=backStackEntry.arguments?.get("id").toString()?:""
            val decodedId= URLDecoder.decode(id, StandardCharsets.UTF_8.toString())
            ReadScreen(mangaViewModel = mangaViewModel, navController = navController, chapterId = id, modifier = modifier)
        }

        composable (route = NavigationHelper.SearchScreen.route){
            SearchScreen(mangaViewModel = mangaViewModel, navController = navController, modifier = modifier)
        }

        composable (route = NavigationHelper.MyApp.route){
            MyApp(mangaViewModel = mangaViewModel, navController = navController, modifier = modifier)
        }

        composable (route = "${NavigationHelper.SeeAllScreen.route}/{title}/{id}",
            arguments = listOf(
                navArgument ("title"){
                    type= NavType.StringType
                },
                navArgument ("id"){
                    type= NavType.IntType
                }
            )
        ){backStackEntry->
            val title=backStackEntry.arguments?.getString("title")?:"Unknown"
            val code=backStackEntry.arguments?.getInt("id")?:0
            SeeAllScreen(mangaViewModel = mangaViewModel, navController = navController, title = title, code = code, modifier = modifier)
        }


    }

}