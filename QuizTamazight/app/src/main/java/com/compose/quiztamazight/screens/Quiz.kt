package com.compose.quiztamazight.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.compose.quiztamazight.R
import com.compose.quiztamazight.viewModels.viewModel
import com.example.testfriends_jetpackcompose.navigation.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Quiz(navController: NavHostController, viewModel: viewModel) {

    var text = false
    val context = LocalContext.current
    val questions = viewModel.questions

    val folder = viewModel.folder

    var clickable = remember {
        mutableStateOf(true)
    }


    fun finish() {
        navController.popBackStack()
        navController.navigate(Screen.Results.route)
    }

    Scaffold(backgroundColor = Color.White) {
        Image(
            modifier = Modifier.fillMaxHeight(),
            painter = painterResource(id = R.drawable.background),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = Unit) {
                visible = true
            }

            AnimatedVisibility(
                visible = visible,
                enter = animations[0],
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(brushes[0])

                ) {
                    Text(
                        text = questions[viewModel.index].question,
                        color = Color.White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                AnimatedVisibility(
                    visible = visible,
                    enter = animations[0],
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    CardAnswer(
                        context = context,
                        image = viewModel.questions[viewModel.index].answer1,
                        folder = folder,
                        realAnswer = viewModel.questions[viewModel.index].answer,
                        clickable = clickable.value,
                        wait = {
                            clickable.value = !it
                        },
                        playSound = {
                            if (it) {
                                viewModel.playSound(context, R.raw.wrong)
                            } else {
                                viewModel.results++
                                viewModel.playSound(context, R.raw.success)
                            }
                        },
                        onClick = {
                            if (!viewModel.next())
                                finish()
                        }
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))
                AnimatedVisibility(
                    visible = visible,
                    enter = animations[0],
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    CardAnswer(
                        context = context,
                        image = viewModel.questions[viewModel.index].answer2,
                        folder = folder,
                        realAnswer = viewModel.questions[viewModel.index].answer,
                        clickable = clickable.value,
                        wait = {
                            clickable.value = !it
                        },
                        playSound = {
                            if (it)
                                viewModel.playSound(context, R.raw.wrong)
                            else {
                                viewModel.results++
                                viewModel.playSound(context, R.raw.success)
                            }
                        },
                        onClick = {
                            if (!viewModel.next())
                                finish()
                        }

                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                AnimatedVisibility(
                    visible = visible,
                    enter = animations[0],
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    CardAnswer(
                        context = context,
                        image = viewModel.questions[viewModel.index].answer3,
                        folder = folder,
                        realAnswer = viewModel.questions[viewModel.index].answer,
                        clickable = clickable.value,
                        wait = {
                            clickable.value = !it
                        },
                        playSound = {
                            if (it)
                                viewModel.playSound(context, R.raw.wrong)
                            else {
                                viewModel.results++
                                viewModel.playSound(context, R.raw.success)
                            }
                        },
                        onClick = {
                            if (!viewModel.next())
                                finish()
                        }
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))
                AnimatedVisibility(
                    visible = visible,
                    enter = animations[0],
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    CardAnswer(
                        context = context,
                        image = viewModel.questions[viewModel.index].answer4,
                        folder = folder,
                        realAnswer = viewModel.questions[viewModel.index].answer,
                        clickable = clickable.value,
                        wait = {
                            clickable.value = !it
                        },
                        playSound = {
                            if (it)
                                viewModel.playSound(context, R.raw.wrong)
                            else {
                                viewModel.results++
                                viewModel.playSound(context, R.raw.success)
                            }
                        },
                        onClick = {
                            if (!viewModel.next())
                                finish()
                        }
                    )
                }

            }
        }
    }

}

@Composable
fun CardAnswer(
    context: Context,
    image: String,
    folder: String,
    realAnswer: String,
    clickable: Boolean,
    wait: (Boolean) -> Unit,
    onClick: () -> Unit,
    playSound: (Boolean) -> Unit
) {
    var cardBackground by remember { mutableStateOf(brushes[3]) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(100.dp)
            .width(170.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(cardBackground)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                if (!clickable)
                    return@clickable
                if (realAnswer != image) {
                    playSound(true)
                    cardBackground = brushes[5]
                } else {
                    playSound(false)
                    cardBackground = brushes[4]
                }
                wait(true)
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        cardBackground = brushes[3]
                        onClick()
                        wait(false)
                    },
                    1000 // value in milliseconds
                )
            }
    ) {
        getFromAssets(context = context, folder = folder, image = image)?.apply {
            Image(
                bitmap = this,
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
        }

    }
}

@Composable
fun getFromAssets(context: Context, folder: String, image: String): ImageBitmap? {
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    try {
        with(context.assets.open("$folder/$image.png")) {
            imageBitmap = BitmapFactory.decodeStream(this).asImageBitmap()
        }
    } catch (ex: Exception) {
    }
    return imageBitmap
}

@OptIn(ExperimentalAnimationApi::class)
val animations = listOf(
    slideInVertically() + fadeIn(
        initialAlpha = 0.3f
    ),
    slideInHorizontally(
        animationSpec = tween(2000)
    ) + expandHorizontally(
        expandFrom = Alignment.Start
    ) + fadeIn(
        initialAlpha = 0.3f,

        ),
    slideInHorizontally(
        animationSpec = tween(2000)
    ) + fadeIn(
        initialAlpha = 0.3f
    ),

    )