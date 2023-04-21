package com.example.myapplication.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.ButtonAction
import com.example.myapplication.model.Product
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MoreInfoComposable(
    modifier: Modifier = Modifier, product: Product, onButtonClicked: (ButtonAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Background, title = {
                Text(
                    text = product.name, style = Typography.h6
                )
            }, actions = {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.DarkGray)
                        .size(32.dp)
                ) {
                    Icon(imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                onButtonClicked(
                                    ButtonAction.NavigateButton(
                                        NavigationDestination.MainScreen()
                                    )
                                )
                            })
                }
            })
        }, backgroundColor = Background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.description)
        }
    }
}