package com.mars.jetpacklearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mars.jetpacklearn.ui.theme.JetpackLearnTheme

/**
 * Created by Mars on 2022/6/29 09:47.
 */
class ComposeLayoutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackLearnTheme {
                LayoutsCodelab()
            }
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
            LayoutsCodelab()
        }
    }
}