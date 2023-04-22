package com.example.myapplication.ui

import android.annotation.SuppressLint
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.model.ButtonAction
import com.example.myapplication.model.ProductAttributeData
import com.example.myapplication.ui.theme.PriceBackground
import com.example.myapplication.ui.theme.PrimaryColor
import com.example.myapplication.ui.theme.Shapes
import com.example.myapplication.ui.theme.Typography
import com.kole.myapplication.cms.nnsettings.NNSettingsString

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductAttributeComposable(
    modifier: Modifier = Modifier,
    productAttributeData: ProductAttributeData,
    onButtonClicked: (ButtonAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = PrimaryColor, title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = productAttributeData.name, style = Typography.h6
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
                                onButtonClicked(
                                    ButtonAction.NavigateButton(
                                        NavigationDestination.MainScreen()
                                    )
                                )
                            })
                }
            })
        }, backgroundColor = PrimaryColor
    ) {
        Column(modifier = Modifier.padding(16.dp, 0.dp)) {
            Text(text = productAttributeData.title, style = Typography.h5)
            Text(
                text = productAttributeData.description, modifier = Modifier.padding(0.dp, 16.dp)
            )
            val prodAttrName = productAttributeData.name.lowercase()
            Text(
                text = NNSettingsString(
                    "SimilarAttrProducts",
                    stringResource(R.string.similar_attr_products, prodAttrName),
                    mapOf(Pair("{ATTR_NAME}", prodAttrName)),
                ), style = Typography.h6, modifier = Modifier.padding(0.dp, 16.dp)
            )
            SimilarProducts(productAttributeData)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SimilarProducts(productAttributeData: ProductAttributeData) {
    val pagerState = rememberPagerState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(productAttributeData.similarProductsData.similarProducts.size) {
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
                    val similarProduct =
                        productAttributeData.similarProductsData.similarProducts[it]
                    HorizontalPager(
                        pageCount = similarProduct.productImages.size, state = pagerState
                    ) { page ->
                        AsyncImage(
                            modifier = Modifier.height(128.dp).fillMaxWidth(),
                            model = similarProduct.productImages[page], contentDescription = null
                        )
                    }
                    Text(
                        text = similarProduct.description,
                        minLines = 3,
                        maxLines = 3,
                        color = Color.Black,
                        modifier = Modifier.padding(0.dp, 8.dp)
                    )
                    PriceItem()
                }
            }
        }
    }
}

@Composable
private fun PriceItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clip(shape = Shapes.large)
                .background(PriceBackground)
                .padding(8.dp, 4.dp)
                .align(Alignment.Start)
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
