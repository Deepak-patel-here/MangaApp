package com.deepakjetpackcompose.mangaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.deepakjetpackcompose.mangaapp.data.navigation.Navigation
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
                    Navigation(mangaViewModel = viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
