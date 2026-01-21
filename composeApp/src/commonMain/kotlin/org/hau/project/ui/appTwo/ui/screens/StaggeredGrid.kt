package org.hau.project.ui.appTwo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

@Composable
fun StaggeredScreen(){

    val items = (1..100).map {
        StaggeredGrid(
            height = Random.nextInt(200,300).dp,
            color = Color(
                Random.nextLong(0xFFFFFFFF)
            )
        )
    }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ){
        items(items){item->
            StaggeredBox(item)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun StaggeredScreenPreview(){
    StaggeredScreen()
}
data class StaggeredGrid(
    val height: Dp,
    val color: Color
)

@Composable
fun StaggeredBox(grid: StaggeredGrid){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{}
            .height(grid.height)
            .background(grid.color, RoundedCornerShape(16.dp))
    )
}