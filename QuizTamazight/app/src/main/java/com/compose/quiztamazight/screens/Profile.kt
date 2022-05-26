package com.compose.quiztamazight.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.quiztamazight.R

@Composable
fun Profile() {

    Image(
        modifier = Modifier.fillMaxHeight(),
        painter = painterResource(id = R.drawable.background),
        contentScale = ContentScale.Crop,
        contentDescription = ""
    )
    CollapsingEffectScreen()

}

@Composable
fun BoxProfile() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.tamazight),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(top = 180.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                .background(Color.White)
                .padding(top = 70.dp, start = 10.dp)

        ) {
            Text(text = "ⴰⵙⵍⵎⴰⴷ : ⵅⴰⵍⵉⴷ ⵍⵎⵓⵎⵏ", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "ⴰⵙⵍⵎⴰⴷ ⴰⵎⴰⵏⵓⵏ ⴳ ⵡⴰⵎⵎⴰⵙ ⴰⵏⵎⵏⴰⴹ ⵏ ⵜⵣⵣⵓⵍⵉⵏ ⵏ ⵓⵙⵙⵍⵎⴷ ⴷ ⵓⵙⵎⵓⵜⵜⴳ, ⵜⴰⵙⴳⴰ ⵏ ⵜⵉⴳⵎⵎⵉ ⵜⵓⵎⵍⵉⵍⵜ ⵚⵟⵟⴰⵟ.")
            Text(text = " ⵜⴰⵥⵍⴰⵢⵜ ⵏ ⵜⵎⴰⵣⵉⵖⵜ.")
            Text(text = "ⴰⵙⵙⵍⵎⴷ ⴰⵎⵏⵣⵓ.")
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 140.dp, start = 20.dp),

            ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color.White)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.khalid),
                    contentDescription = "",
                    modifier = Modifier,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun CollapsingEffectScreen() {
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0

    LazyColumn(
        Modifier.fillMaxSize(),
        lazyListState,

        ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.khalid),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.5f
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
                    .height(240.dp)
                    .fillMaxWidth()
            )
        }
        item {

            Text(
                text = "ⴰⵙⵍⵎⴰⴷ : ⵅⴰⵍⵉⴷ ⵍⵎⵓⵎⵏ",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            Text(
                text = "ⴰⵙⵍⵎⴰⴷ ⴰⵎⴰⵏⵓⵏ ⴳ ⵡⴰⵎⵎⴰⵙ ⴰⵏⵎⵏⴰⴹ ⵏ ⵜⵣⵣⵓⵍⵉⵏ ⵏ ⵓⵙⵙⵍⵎⴷ ⴷ ⵓⵙⵎⵓⵜⵜⴳ, ⵜⴰⵙⴳⴰ ⵏ ⵜⵉⴳⵎⵎⵉ ⵜⵓⵎⵍⵉⵍⵜ ⵚⵟⵟⴰⵟ.",
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = " ⵜⴰⵥⵍⴰⵢⵜ ⵏ ⵜⵎⴰⵣⵉⵖⵜ.",
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "ⴰⵙⵙⵍⵎⴷ ⴰⵎⵏⵣⵓ.",
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}
