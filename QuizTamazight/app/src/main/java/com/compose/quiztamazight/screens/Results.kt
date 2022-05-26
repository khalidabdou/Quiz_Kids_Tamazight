package com.compose.quiztamazight.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.compose.quiztamazight.R
import com.compose.quiztamazight.ui.CustomComponent
import com.compose.quiztamazight.ui.theme.Teal200
import com.compose.quiztamazight.viewModels.viewModel

@Composable
fun Results(navController: NavHostController, viewModel: viewModel) {


    val context = LocalContext.current
    val result = viewModel.results * 10
    val textResults: String = if (result >= 40) "Ayouz" else "retry"

    LaunchedEffect(key1 = Unit) {
        if (result > 4)
            viewModel.playSound(context, R.raw.win)
    }

    Image(
        modifier = Modifier.fillMaxHeight(),
        painter = painterResource(id = R.drawable.background),
        contentScale = ContentScale.Crop,
        contentDescription = ""
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CustomComponent(
            smallText = "Results",
            maxIndicatorValue = 100,
            indicatorValue = result,
            foregroundIndicatorColor = Teal200.copy(0.7f),
            bigTextFontSize = 30.sp
        )

        Text(
            text = "${viewModel.results}/10",
            fontSize = 30.sp,
            fontWeight = Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "$textResults",
            fontSize = 30.sp,
            fontWeight = Bold,
            color = DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }


}