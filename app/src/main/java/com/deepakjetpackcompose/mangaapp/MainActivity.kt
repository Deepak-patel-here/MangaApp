package com.deepakjetpackcompose.mangaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.deepakjetpackcompose.mangaapp.data.navigation.Navigation
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import com.deepakjetpackcompose.mangaapp.ui.theme.MangaAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        setContent {
            val viewModel: MangaViewModel by viewModels()
            val systemController = rememberSystemUiController()


            SideEffect {
                systemController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = false
                )

                systemController.setNavigationBarColor(
                    color = Color(0xFF1D1D1D),
                    darkIcons = false
                )
            }
            MangaAppTheme {
                Scaffold { innerPadding->
                    Navigation(mangaViewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
