package com.deepakjetpackcompose.mangaapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.presentation.component.ListTopBar
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.AuthState
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    mangaViewModel: MangaViewModel,
    modifier: Modifier = Modifier
) {
    val authState = mangaViewModel.authState.collectAsState()
    val myUser=mangaViewModel.myUser.collectAsState()
    LaunchedEffect(authState.value) {
        if(authState.value== AuthState.Authenticated){
            mangaViewModel.getUser()
        }
    }


    Scaffold(topBar = {
        ListTopBar(
            title = "Profile",
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 20.dp),
            onSearch = { navController.navigate(NavigationHelper.SearchScreen.route) },
        )
    }) { innerPadding ->
        if (authState.value == AuthState.UnAuthenticated) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1D1D1D))
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(30.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF313131))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {

                        Text(
                            "My Profile",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Sign in to synchronize your manga",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(25.dp))
                        Button(
                            onClick = { navController.navigate(NavigationHelper.Login.route) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Continue")
                        }
                    }
                }
            }
        } else {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1D1D1D))
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(30.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.elevatedCardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF313131))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card (shape = CircleShape, modifier = Modifier.size(80.dp)){
                            Image(painter = painterResource(R.drawable.manga_logo),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds)
                        }
                        Spacer(Modifier.width(20.dp))
                        Column {
                            Text(myUser.value.name, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(5.dp))
                            Text(myUser.value.email,fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Normal)
                        }
                    }


                }

                Spacer(Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(onClick = {mangaViewModel.signOut()})) {
                    Text("Log out" ,fontSize = 14.sp, color = Color.Red, fontWeight = FontWeight.Normal)
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }


}