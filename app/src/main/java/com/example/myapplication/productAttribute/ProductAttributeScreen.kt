package com.example.myapplication.productAttribute

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.model.*
import com.example.myapplication.ui.theme.*
import com.kole.myapplication.cms.nnsettings.NNSettingsString

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductAttributeComposable(
    modifier: Modifier = Modifier,
    viewModel: ProductAttributeViewModel,
    productAttribute: ProductAttribute,
    onButtonClicked: (ButtonAction) -> Unit
) {
    val productAttributesUIState = viewModel.prodAttrUIState.collectAsState().value
    val productAttributeData =
        productAttributesUIState.productAttributesData.productAttributesInfo.find { it.id == productAttribute.id }
    val similarAttrProductsData = productAttributesUIState.similarAttrProductsData

    SetStatusBarColor()
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = PrimaryColor, title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = productAttribute.attributeName, style = Typography.h6
                    )
                }
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
                                onButtonClicked(ButtonAction.CloseButton)
                            })
                }
            })
        }, backgroundColor = PrimaryColor
    ) {
        Column(modifier = Modifier.padding(16.dp, 0.dp)) {
            Text(text = productAttributeData?.title ?: "", style = Typography.h5)
            Text(
                text = productAttributeData?.description ?: "",
                modifier = Modifier.padding(0.dp, 16.dp)
            )
            val prodAttrName = productAttribute.attributeName.lowercase()
            Text(
                text = NNSettingsString(
                    "SimilarAttrProducts",
                    stringResource(R.string.similar_attr_products, prodAttrName),
                    mapOf(Pair("{ATTR_NAME}", prodAttrName)),
                ), style = Typography.h6, modifier = Modifier.padding(0.dp, 16.dp)
            )
            SimilarAttrProducts(similarAttrProductsData = similarAttrProductsData)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SimilarAttrProducts(
    modifier: Modifier = Modifier, similarAttrProductsData: SimilarProductsData
) {
    val pagerState = rememberPagerState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(similarAttrProductsData.similarProducts.size) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(shape = Shapes.medium)
                    .background(Color.White)
                    .fillMaxWidth()
                    .height(272.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    val similarProduct = similarAttrProductsData.similarProducts[it]
                    HorizontalPager(
                        pageCount = similarProduct.productImages.size, state = pagerState
                    ) { page ->
                        AsyncImage(
                            modifier = Modifier
                                .height(128.dp)
                                .fillMaxWidth(),
                            model = similarProduct.productImages[page],
                            contentDescription = null
                        )
                    }
                    Text(
                        text = similarProduct.description,
                        minLines = 3,
                        maxLines = 3,
                        color = Color.Black,
                        modifier = Modifier.padding(0.dp, 8.dp)
                    )
                    PriceItem(Modifier.align(Alignment.Start))
                }
            }
        }
    }
}

@Composable
private fun PriceItem(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clip(shape = Shapes.large)
                .background(PriceBackground)
                .padding(8.dp, 4.dp)
                .then(modifier)
        ) {
            Icon(
                modifier = Modifier.height(16.dp),
                painter = painterResource(R.drawable.ic_carousal_coin),
                contentDescription = null,
                tint = Color.Black
            )
            Text(
                text = NNSettingsString("CoinCount", stringResource(id = R.string.plus_hundred)),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = Typography.body2
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = NNSettingsString("CoinText", stringResource(id = R.string.plus_hundred)),
                color = Color.Black,
                style = Typography.body2
            )
        }
    }
}

@Composable
private fun SetStatusBarColor() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = PrimaryColor.toArgb()
            window.navigationBarColor = PrimaryColor.toArgb()
        }
    }
}
