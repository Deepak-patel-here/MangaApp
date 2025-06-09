package com.deepakjetpackcompose.mangaapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.deepakjetpackcompose.mangaapp.R
import com.deepakjetpackcompose.mangaapp.data.navigation.NavigationHelper
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.AuthState
import com.deepakjetpackcompose.mangaapp.presentation.viewmodel.MangaViewModel
import kotlin.math.sign


@Composable
fun SignUpScreen(mangaViewModel: MangaViewModel,navController: NavController,modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isShow by remember { mutableStateOf(false) }

    val emailReq = remember { FocusRequester() }
    val passwordReq = remember { FocusRequester() }
    val confirmPassReq = remember { FocusRequester() }

    val keyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val authState=mangaViewModel.authState.collectAsState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    LaunchedEffect(authState.value) {
        if(authState.value== AuthState.Authenticated){
            navController.navigate(NavigationHelper.MyApp.route){
                popUpTo(NavigationHelper.SignUp.route){inclusive=true}
            }
        }
    }



        Scaffold(
            topBar = {
                Row(modifier= Modifier.statusBarsPadding().padding(horizontal = 20.dp)) {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            painter = painterResource(R.drawable.left_arrow),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1D1D1D))
                    .padding(horizontal = 20.dp)
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (authState.value == AuthState.Loading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieAnimation(
                            composition = composition,
                            progress = { progress },
                            modifier = Modifier.size(150.dp)
                        )
                    }

                }
                Text(
                    "Sign Up",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(3.dp)
                )
                Text(
                    "Over 10000+ Mangas are waiting for you",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(Modifier.height(30.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null, tint = Color.Red
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Red,
                        focusedContainerColor = Color(0xFF323232),
                        unfocusedContainerColor = Color(0xFF323232),
                        unfocusedLabelColor = Color.LightGray,
                        focusedTextColor = Color.White,
                        focusedLabelColor = Color.Red,
                        unfocusedTextColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            emailReq.requestFocus()
                        }
                    )
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(emailReq),
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null, tint = Color.Red
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Red,
                        focusedContainerColor = Color(0xFF323232),
                        unfocusedContainerColor = Color(0xFF323232),
                        unfocusedLabelColor = Color.LightGray,
                        focusedTextColor = Color.White,
                        focusedLabelColor = Color.Red,
                        unfocusedTextColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            passwordReq.requestFocus()
                        }
                    )
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordReq),
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null, tint = Color.Red
                        )
                    },

                    trailingIcon = {
                        Icon(
                            painter = painterResource(if (isShow) R.drawable.show else R.drawable.hide),
                            contentDescription = null, tint = Color.Red,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(onClick = { isShow = !isShow })
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Red,
                        focusedContainerColor = Color(0xFF323232),
                        unfocusedContainerColor = Color(0xFF323232),
                        unfocusedLabelColor = Color.LightGray,
                        focusedTextColor = Color.White,
                        focusedLabelColor = Color.Red,
                        unfocusedTextColor = Color.White
                    ),
                    visualTransformation = if (isShow) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            confirmPassReq.requestFocus()
                        }
                    )
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(confirmPassReq),
                    label = { Text("Confirm password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null, tint = Color.Red
                        )
                    },

                    trailingIcon = {
                        Icon(
                            painter = painterResource(if (isShow) R.drawable.show else R.drawable.hide),
                            contentDescription = null, tint = Color.Red,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(onClick = { isShow = !isShow })
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Red,
                        focusedContainerColor = Color(0xFF323232),
                        unfocusedContainerColor = Color(0xFF323232),
                        unfocusedLabelColor = Color.LightGray,
                        focusedTextColor = Color.White,
                        focusedLabelColor = Color.Red,
                        unfocusedTextColor = Color.White
                    ),
                    visualTransformation = if (isShow) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboard?.hide()
                        }
                    )
                )

                Spacer(Modifier.height(40.dp))

                Button(
                    onClick = {
                        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                            if (password.equals(confirmPassword)) {
                                mangaViewModel.signUp(
                                    name = name,
                                    email = email,
                                    password = password
                                ) { success, msg ->
                                    if (success) {
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    } else Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }else Toast.makeText(context, "fill all the fields", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Sign up")
                }

                Spacer(Modifier.height(30.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center

                ) {
                    Text("Already have an account?", fontSize = 16.sp, color = Color.White)
                    Text(
                        "Login",
                        fontSize = 16.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(NavigationHelper.Login.route)
                        })
                    )
                }
            }
        }


}