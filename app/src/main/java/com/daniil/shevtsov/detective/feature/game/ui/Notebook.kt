package com.daniil.shevtsov.detective.feature.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.daniil.shevtsov.detective.core.ui.Pallete
import com.daniil.shevtsov.detective.feature.main.view.ScreenContent

@Composable
@Preview(widthDp = 1000, heightDp = 600)
fun NotebookPreview() {
    Box {
        Notebook()
    }
}

@Composable
fun Notebook() {
    Box(
        modifier = Modifier
            .background(Pallete.Cover2)
            .padding(16.dp)
    ) {
        val bindingPadding = 16.dp
        Pages(
            firstPage = {
                ScreenContent(
                    state = gameViewStateCompose(),
                    modifier = Modifier.padding(end = bindingPadding).padding(8.dp),
                    onAction = {},
                )
            },
            secondPage = {
                ScreenContent(
                    state = gameViewStateCompose(),
                    modifier = Modifier.padding(start = bindingPadding).padding(8.dp),
                    onAction = {},
                )
            }
        )
    }
}

@Composable
fun Pages(
    modifier: Modifier = Modifier,
    firstPage: @Composable () -> Unit,
    secondPage: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier,
        ) {
            Page(
                place = PagePlace.First,
                content = firstPage,
                modifier = Modifier.weight(1f)
            )
            Page(
                place = PagePlace.Second,
                content = secondPage,
                modifier = Modifier.weight(1f)
            )
        }
        Binding(modifier.fillMaxHeight())
    }
}

enum class PagePlace {
    First,
    Second,
}

@Composable
fun Page(
    place: PagePlace,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
//    val possibleColors = listOf(Color.Blue, Color.Yellow, Color.Red)
    val possibleColors = listOf(Pallete.Page, Pallete.PageDark, Pallete.PageVeryDark)
    val colors = when (place) {
        PagePlace.First -> possibleColors
        PagePlace.Second -> possibleColors.reversed()
    }
    //val pageSize = DpSize(200.dp, 400.dp)

//    val pageWidth = with(LocalDensity.current) {
//        pageSize.width.toPx()
//    }
    val shadowWidth = with(LocalDensity.current) {
        100.dp.toPx()
    }
    val range = when (place) {
        PagePlace.First -> IntRange(start = 0, endInclusive = shadowWidth.toInt())
        PagePlace.Second -> IntRange(start = 0, endInclusive = shadowWidth.toInt() - 400)
    }
    val colorStops = when(place) {
        PagePlace.First -> arrayOf(
            0.0f to colors[0],
            0.9f to colors[1],
            1f to colors[2],
        )
        PagePlace.Second -> arrayOf(
            0.0f to colors[0],
            0.1f to colors[1],
            1f to colors[2],
        )
    }


    Box(
        modifier = modifier
            .background(Pallete.Page3)
            .padding(top = 1.dp, start = 1.dp, bottom = 1.dp)
            .background(Pallete.Page4)
            .padding(bottom = 1.dp)
            .background(
                Brush.horizontalGradient(
                    colorStops = colorStops,
                )
            )
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min)
    ) {
        content()
    }
}

@Composable
fun Binding(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.height(IntrinsicSize.Min),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFF3E1D1D))
                .width(8.dp)
                .fillMaxHeight()
        )
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = SpaceEvenly
        ) {
            repeat(18) {
                Box(
                    modifier = Modifier.width(IntrinsicSize.Min),
                    contentAlignment = Alignment.Center,
                ) {
                    val holeSize = DpSize(6.dp, 6.dp)
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        PerforatedHole(holeSize)
                        PerforatedHole(holeSize)
                    }
                    Box(
                        Modifier
                            .padding(horizontal = holeSize.width / 2)
                            .background(Color(0xFFcfc07d))
                            .padding(top = 0.5.dp)
                            .background(Color(0xFF131007))
                            .padding(bottom = 0.5.dp)
                            .background(Color(0xFF564C20))
                            .size(width = 28.dp, height = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PerforatedHole(size: DpSize) {
    Box(
        modifier = Modifier
            .background(Pallete.Page4)
            .padding(1.dp)
            .background(Color.Black)
            .size(size = size)
    )
}

