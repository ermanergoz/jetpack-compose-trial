package com.example.myapplication.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

// Set of Material typography styles to start with
val UnileverShilling = FontFamily(
    Font(R.font.unilever_shilling_regular, FontWeight.Normal),
    Font(R.font.unilever_shilling_medium, FontWeight.Medium),
    Font(R.font.unilever_shilling_bold, FontWeight.Bold)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = UnileverShilling,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = UnileverShilling,
        fontWeight = FontWeight.W900,
        fontSize = 18.sp,
        color = Color.White
    ),
    h5 = TextStyle(
        fontFamily = UnileverShilling,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = UnileverShilling,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = UnileverShilling,
        fontSize = 12.sp
    )
)
