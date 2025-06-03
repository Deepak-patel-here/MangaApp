package com.deepakjetpackcompose.mangaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.deepakjetpackcompose.mangaapp.presentation.screens.HomeScreen
import com.deepakjetpackcompose.mangaapp.presentation.screens.TestingScreen
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import com.deepakjetpackcompose.mangaapp.ui.theme.MangaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false) // ✅ image behind status bar
        window.statusBarColor = android.graphics.Color.TRANSPARENT // ✅ transparent status bar
        setContent {
            val viewModel: MangaViewModel by viewModels()
            MangaAppTheme {
                Scaffold { innerPadding->
                    HomeScreen(mangaViewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
