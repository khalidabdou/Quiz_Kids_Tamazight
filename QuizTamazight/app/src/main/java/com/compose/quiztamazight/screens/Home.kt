package com.compose.quiztamazight.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.compose.quiztamazight.R
import com.compose.quiztamazight.ui.theme.*
import com.compose.quiztamazight.viewModels.viewModel
import com.example.testfriends_jetpackcompose.navigation.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Home(navController: NavHostController, viewModel: viewModel) {
    viewModel.results = 0
    Scaffold(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            modifier = Modifier.fillMaxHeight(),
            painter = painterResource(id = R.drawable.background),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
        Column(modifier = Modifier.padding(10.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                Text(
                    text = "ⵙⵙⵏⵜⵉ ⵜⵓⵔⴰⵔⵜ",
                    fontSize = 25.sp,
                    color = color6,
                    modifier = Modifier.weight(
                        1f
                    )
                )
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(
                            CircleShape
                        )
                        .background(Color.White)
                        .clickable {
                            navController.navigate(Screen.Profile.route)
                        }
                )
            }
            var visible by remember { mutableStateOf(false) }
            val density = LocalDensity.current

            var scale by remember { mutableStateOf(1f) }
            val scope = rememberCoroutineScope()
            LaunchedEffect(key1 = Unit) {
                visible = true
            }


            //Text(text = "Be the first!",fontSize = 16.sp, color = Color.DarkGray)
            repeat(4) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(
                        animationSpec = tween(it * 1000)
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    CardLevel(
                        it, brush = brushes[it],
                        images[it],
                        onClick = {
                            when (it) {
                                0 -> {
                                    viewModel.folder = "body"
                                    viewModel.index = 0
                                    viewModel.endList = 10
                                }
                                1 -> {
                                    viewModel.folder = "animals"
                                    viewModel.index = 10
                                    viewModel.endList = 20
                                }
                                2 -> {
                                    viewModel.folder = "colors"
                                    viewModel.index = 20
                                    viewModel.endList = 30
                                }
                                3 -> {
                                    viewModel.folder = "fruits"
                                    viewModel.index = 30
                                    viewModel.endList = 40
                                }
                            }
                            navController.navigate(Screen.Quiz.route)
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun CardLevel(index: Int, brush: Brush, image: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = brush
                )
                .align(Alignment.BottomEnd)
                .clickable {
                    onClick()
                }
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "ⴰⵙⵡⵉⵔ ${index + 1}", fontSize = 12.sp, color = Color.White.copy(0.7f))
                Text(text = titles[index], fontSize = 20.sp, color = Color.White)
            }
        }
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier
                .padding(end = 30.dp, top = 10.dp)
                .size(60.dp)
                .align(Alignment.TopEnd)
        )
    }
}

val brushes = listOf(
    Brush.horizontalGradient(
        colors = listOf(
            color7,
            color8
        )
    ),
    Brush.horizontalGradient(
        colors = listOf(
            color5,
            color6
        )
    ),
    Brush.horizontalGradient(
        colors = listOf(
            color3,
            color4
        )
    ),
    Brush.horizontalGradient(
        colors = listOf(
            color1,
            color2
        )
    ),
    Brush.radialGradient(
        colors = listOf(
            success1,
            success2
        )
    ),
    Brush.radialGradient(
        colors = listOf(
            wrong1,
            wrong2
        )
    ),
)

val images = listOf(R.drawable.body, R.drawable.rooster, R.drawable.colors, R.drawable.fruits)

val titles = listOf(
    "ⵜⴰⴼⴳⴳⴰ", "ⵉⵎⵓⴷⴰⵔ", "ⵓⴽⵍⴰⵏ", "ⵉⴳⵓⵎⵎⴰ ⴷ ⵉⵛⴰⴽⴰⵏ"
)