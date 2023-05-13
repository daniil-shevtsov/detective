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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.daniil.shevtsov.detective.core.ui.Pallete

@Composable
@Preview(widthDp = 1000, heightDp = 600)
fun Notebook() {
    Box {
        val kek = Color(0xFF3E1D1D)
        Box(contentAlignment = Alignment.Center, modifier = Modifier.height(IntrinsicSize.Min)) {
            Row(
                modifier = Modifier
                    .background(Pallete.Cover2)
                    .padding(16.dp)
            ) {
                val pageSize = DpSize(200.dp, 400.dp)
                Box(modifier = Modifier.background(Pallete.Page).size(pageSize))
                Box(
                    modifier = Modifier
                        .background(kek)
                        .size(width = 8.dp, height = pageSize.height)
                )

                Box(modifier = Modifier.background(Pallete.Page).size(pageSize))
            }
            Column(
                modifier = Modifier.fillMaxHeight().padding(vertical = 8.dp),
                verticalArrangement = SpaceEvenly
            ) {
                repeat(16) {
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

