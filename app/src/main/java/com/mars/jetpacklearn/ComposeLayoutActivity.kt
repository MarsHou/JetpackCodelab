package com.mars.jetpacklearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mars.jetpacklearn.ui.theme.JetpackLearnTheme
import kotlinx.coroutines.launch

/**
 * Created by Mars on 2022/6/29 09:47.
 */
class ComposeLayoutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackLearnTheme {
                ChipBodyContent()
            }
        }
    }

    private val topics = listOf(
        "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
        "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
        "Religion", "Social sciences", "Technology", "TV", "Writing"
    )


    @Composable
    fun ChipBodyContent(modifier: Modifier = Modifier) {
        val state = rememberScrollState()
        Row(modifier = modifier.horizontalScroll(state)) {
            StaggeredGrid(rows = 5) {
                topics.forEach {
                    Chip(modifier = Modifier.padding(8.dp), text = it)
                }
            }
        }

    }


    @Composable
    fun Chip(modifier: Modifier = Modifier, text: String) {
        Card(
            modifier = modifier,
            border = BorderStroke(color = Color.Black, width = Dp.Hairline),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp, 16.dp)
                        .background(color = MaterialTheme.colors.secondary)
                )
                Spacer(Modifier.width(4.dp))
                Text(text = text)
            }
        }
    }

    @Composable
    fun StaggeredGrid(modifier: Modifier = Modifier, rows: Int, content: @Composable () -> Unit) {
        Layout(modifier = modifier, content = content) { measurables, constrains ->
            val rowsWidths = IntArray(rows) { 0 }
            val rowsHeights = IntArray(rows) { 0 }
            val placeables = measurables.mapIndexed { index, measurable ->
                val placeable = measurable.measure(constrains)
                val row = index % rows
                rowsWidths[row] += placeable.width
                rowsHeights[row] = rowsHeights[row].coerceAtLeast(placeable.height)
                placeable
            }

            val width =
                rowsWidths.maxOrNull()?.coerceIn(constrains.minWidth.rangeTo(constrains.maxWidth))
                    ?: constrains.minWidth
            val height =
                rowsHeights.sum().coerceIn(constrains.minHeight.rangeTo(constrains.maxHeight))

            val rowY = IntArray(rows) { 0 }
            for (i in 1 until rows) {
                rowY[i] = rowY[i - 1] + rowsHeights[i - 1]
            }
            layout(width, height) {

                val rowX = IntArray(rows) { 0 }
                placeables.forEachIndexed { index, placeable ->
                    val row = index % rows
                    placeable.placeRelative(rowX[row], rowY[row])
                    rowX[row] += placeable.width
                }
            }
        }
    }


    @Composable
    fun MyBodyContent(modifier: Modifier = Modifier) {
        MyOwnColumn(modifier.padding(8.dp)) {
            Text("MyOwnColumn")
            Text("places items")
            Text("vertically.")
            Text("We've done it by hand!")
        }
    }

    @Composable
    fun MyOwnColumn(modifier: Modifier, content: @Composable () -> Unit) {
        Layout(modifier = modifier, content = content) { measurables, constrains ->
            val placeables = measurables.map {
                it.measure(constrains)
            }

            var yPosition = 0
            layout(constrains.maxWidth, constrains.maxHeight) {
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }
    }

    private fun Modifier.firstBaselineToTop(firstBaseLineToTop: Dp) =
        this.then(layout() { measurable, constraints ->
            val placeable = measurable.measure(constraints)
            check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
            val firstBaseLine = placeable[FirstBaseline]

            val placeableY = firstBaseLineToTop.roundToPx() - firstBaseLine
            val height = placeable.height + placeableY
            layout(placeable.width, height) {
                placeable.placeRelative(0, placeableY)
            }
        })


    @Composable
    fun SimpleList() {
        val listSize = 100
        val scrollState = rememberLazyListState()

        val coroutineScope = rememberCoroutineScope()
        Column() {
            Row() {
                Button(onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }) {
                    Text(text = "Scroll to the top")
                }

                Button(onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }) {
                    Text("Scroll to the end")
                }
            }
            LazyColumn(state = scrollState) {
                items(listSize) {
                    ImageListItem(it)
                }
            }
        }
    }

    @Composable
    fun ImageListItem(index: Int) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"),
                contentDescription = "Android Logo",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
        }

    }

    @Composable
    fun LayoutsCodelab() {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = "LayoutsCodelab")
            }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                }
            })
        }, bottomBar = {
            BottomNavigation() {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        }) {
            BodyContent(Modifier.padding(it))
        }
    }

    @Composable
    fun BodyContent(modifier: Modifier = Modifier) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(text = "Hi there!")
            Text(text = "Thanks for going through the Layouts codelab")
        }
    }

    @Composable
    fun PhotographerCard(modifier: Modifier = Modifier) {
        Row(
            modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colors.surface)
                .clickable { }
                .padding(16.dp)) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.background.copy(0.2f)
            ) {

            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = "3 minutes age", style = MaterialTheme.typography.body2)
                }
            }
        }
    }

    @Preview
    @Composable
    fun PhotographerCardPreview() {
        JetpackLearnTheme {
            ChipBodyContent()
        }
    }

    @Preview
    @Composable
    fun TextWithPaddingToBaselinePreview() {
        JetpackLearnTheme {
            Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
        }
    }

    @Preview
    @Composable
    fun TextWithNormalPaddingPreview() {
        JetpackLearnTheme {
            Text("Hi there!", Modifier.padding(top = 32.dp))
        }
    }
}