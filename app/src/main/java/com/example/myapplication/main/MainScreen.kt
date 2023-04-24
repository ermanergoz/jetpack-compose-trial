package com.example.myapplication.main

import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.model.*
import com.example.myapplication.NavigationDestination
import com.example.myapplication.ui.theme.*
import com.kole.myapplication.cms.nnsettings.NNSettingsString
import com.smarttoolfactory.ratingbar.RatingBar

@Composable
fun MainScreen(
    modifier: Modifier = Modifier, viewModel: MainViewModel, onButtonClicked: (ButtonAction) -> Unit
) {
    val mainUIState = viewModel.mainUIState.collectAsState()
    val product = mainUIState.value.product
    val similarProductsData = mainUIState.value.similarProductsData
    val scrollState = rememberScrollState(0)

    SetStatusBarColor()
    Column(modifier = Modifier.background(Background)) {
        NavigationBar(isScrolled = scrollState.value != 0,
            productName = product.name,
            onButtonClicked = { action -> onButtonClicked(action) })
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(weight = 1f, fill = false)
        ) {
            ProductCarousel(imageUrls = product.productImages)
            ProductInfo(
                product = product
            ) { action -> onButtonClicked(action) }
            ProductRating(product = product)
            SimilarProducts(similarProductsData = similarProductsData)
        }
        AddFavorites { action -> onButtonClicked(action) }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NavigationBar(
    modifier: Modifier = Modifier,
    isScrolled: Boolean = false,
    productName: String,
    onButtonClicked: (ButtonAction) -> Unit
) {
    val backgroundColor = if (isScrolled) Background else Color.White
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(8.dp, 16.dp)
            .then(modifier)
    ) {
        Text(
            text = if (isScrolled) productName else "",
            maxLines = 1,
            style = Typography.h6,
            modifier = Modifier
                .widthIn(0.dp, 320.dp)
                .basicMarquee()
        )
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
                    .clickable { onButtonClicked(ButtonAction.CloseButton) })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductCarousel(modifier: Modifier = Modifier, imageUrls: List<String>) {
    val pageCount = imageUrls.size
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .then(modifier),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(pageCount = pageCount, state = pagerState) { page ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imageUrls.isNotEmpty()) {
                    AsyncImage(
                        model = imageUrls[page],
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        PriceItem(Modifier.align(Alignment.CenterHorizontally))
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}

@Composable
private fun ProductInfo(
    modifier: Modifier = Modifier,
    product: Product,
    onButtonClicked: (ButtonAction) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(shape = Shapes.medium)
            .background(Color.White)
            .fillMaxSize()
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.name,
                style = Typography.h6,
                modifier = Modifier.padding(0.dp, vertical = 8.dp)
            )
            Text(
                text = NNSettingsString("BrandDescription", stringResource(R.string.description)),
                modifier = Modifier.padding(0.dp, vertical = 8.dp)
            )
            Text(text = NNSettingsString("MoreButtonText", stringResource(R.string.more)),
                color = PrimaryColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(0.dp, vertical = 8.dp)
                    .clickable {
                        onButtonClicked(ButtonAction.NavigateButton(NavigationDestination.MoreScreen()))
                    })
            Spacer(Modifier.height(8.dp))
            Text(
                text = NNSettingsString(
                    "ProductAttributeTitle", stringResource(R.string.product_attributes)
                ),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(0.dp, vertical = 8.dp)
            )
            Text(
                text = NNSettingsString(
                    "ProductAttributeExp", stringResource(R.string.product_attribute)
                )
            )

            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                product.leapAttributes.forEach { attribute ->
                    val attributeIconId = when (attribute) {
                        ProductAttribute.BRAND_WITH_PURPOSE.id -> {
                            R.drawable.icn_is_brand_with_purpose_pdp
                        }
                        ProductAttribute.PETA_ACCREDITED.id -> {
                            R.drawable.icn_is_peta_leaping_bunny_accredited_pdp
                        }
                        // Todo: Add the rest
                        else -> 0
                    }
                    ProductAttribute.getAttributeById(attribute)?.let {
                            AttributeItem(
                                attributeIconId = attributeIconId,
                                productAttribute = it,
                                onButtonClicked = onButtonClicked
                            )
                        }
                }
            }
        }
    }
}

@Composable
private fun AttributeItem(
    modifier: Modifier = Modifier,
    attributeIconId: Int,
    productAttribute: ProductAttribute,
    onButtonClicked: (ButtonAction) -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp, 16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.clickable {
            onButtonClicked(
                ButtonAction.NavigateButton(
                    NavigationDestination.ProductAttributeScreen(
                        productAttribute = productAttribute
                    )
                )
            )
        }) {
            Image(
                painter = painterResource(R.drawable.bg_polygon), contentDescription = null
            )
            Icon(
                painter = painterResource(attributeIconId),
                contentDescription = null,
                tint = PrimaryColorVariant,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(AttributeTextBackground)
                .padding(8.dp)
        ) {
            Text(
                text = productAttribute.attributeName,
                modifier = Modifier
                    .widthIn(max = 128.dp)
                    .wrapContentHeight(),
                softWrap = true,
                maxLines = 2,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProductRating(modifier: Modifier = Modifier, product: Product) {
    Column(
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .then(modifier)
    ) {
        Text(
            text = NNSettingsString(
                "ProdRatingNoReviewPts", stringResource(R.string.product_rating)
            ), style = Typography.h6, modifier = Modifier.padding(0.dp, 8.dp)
        )
        Box(
            modifier = Modifier
                .clip(shape = Shapes.medium)
                .background(Color.White)
                .fillMaxSize()
                .padding(0.dp, 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(0.dp, 8.dp)
                ) {
                    if (product.reviewCount > 0) {
                        Text(text = product.ratingValue.toString(), style = Typography.h5)
                        Text(
                            text = NNSettingsString(
                                "OutOfFullRating", stringResource(R.string.out_of_full_rating)
                            )
                        )
                    } else {
                        Text(
                            text = NNSettingsString(
                                "ProdRatingNoReview", stringResource(R.string.out_of_full_rating)
                            ), style = Typography.h6
                        )
                    }
                }
                var basedOnReviewCountText = NNSettingsString(
                    "ReviewCount",
                    stringResource(R.string.based_on_review_count, product.reviewCount),
                    mapOf(Pair("{COUNT}", product.reviewCount.toString()))
                )
                if (product.reviewCount == 0) {
                    basedOnReviewCountText = NNSettingsString(
                        "ProdRatingNoReviewCtr", stringResource(R.string.check_back_soon)
                    )
                }
                Text(text = basedOnReviewCountText, color = Color.LightGray)
                Spacer(modifier = Modifier.height(24.dp))
                RatingBar(
                    rating = product.ratingValue.toFloat(),
                    painterEmpty = painterResource(id = R.drawable.icn_star_unfilled),
                    painterFilled = painterResource(id = R.drawable.icn_star_filled),
                    tintEmpty = PrimaryColor,
                    gestureEnabled = false,
                    space = 8.dp
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimilarProducts(modifier: Modifier = Modifier, similarProductsData: SimilarProductsData) {
    val pagerState = rememberPagerState()

    Column(modifier = Modifier.padding(8.dp, 8.dp)) {
        Text(
            text = NNSettingsString(
                "SimilarProductsTitle", stringResource(R.string.similar_products)
            ), style = Typography.h6, modifier = Modifier.padding(8.dp, 16.dp)
        )
        LazyRow {
            items(similarProductsData.similarProducts.size) {
                Box(
                    modifier = Modifier
                        .padding(8.dp, 0.dp)
                        .clip(shape = Shapes.medium)
                        .background(Color.White)
                        .width(280.dp)
                        .heightIn(400.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        val similarProduct = similarProductsData.similarProducts[it]
                        HorizontalPager(
                            pageCount = similarProduct.productImages.size, state = pagerState
                        ) { page ->
                            AsyncImage(
                                model = similarProduct.productImages[page],
                                contentDescription = null
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = similarProduct.description,
                            minLines = 2,
                            modifier = Modifier.padding(8.dp, 0.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Row {
                            LeapAttributeItem(attribute = similarProduct.leapAttributes.first())
                            if (similarProduct.leapAttributes.size > 1) {
                                Spacer(Modifier.width(8.dp))
                                LeapAttributeItem(attribute = "+${similarProduct.leapAttributes.size}")
                            }
                        }
                        PriceItem(Modifier.align(Alignment.Start))
                    }
                }
            }
        }
    }
}

@Composable
private fun PriceItem(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp)
    ) {
        Row(
            modifier = Modifier
                .clip(shape = Shapes.large)
                .background(PriceBackground)
                .padding(8.dp)
                .then(modifier)
        ) {
            Icon(painter = painterResource(R.drawable.ic_carousal_coin), contentDescription = null)
            Text(
                text = NNSettingsString("CoinCount", stringResource(id = R.string.plus_hundred)),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(8.dp))
            Text(text = NNSettingsString("CoinText", stringResource(id = R.string.plus_hundred)))
        }
    }
}

@Composable
private fun LeapAttributeItem(modifier: Modifier = Modifier, attribute: String) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(AttributeTextBackground)
            .padding(8.dp)
    ) {
        Text(text = attribute)
    }
}


@Composable
private fun AddFavorites(modifier: Modifier = Modifier, onButtonClicked: (ButtonAction) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp)
            .then(modifier)
    ) {
        Button(
            onClick = { onButtonClicked(ButtonAction.FavoriteButton) }, shape = Shapes.large,

            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = NNSettingsString(
                    "AddToFavoriteButtonText", stringResource(id = R.string.plus_hundred)
                ).uppercase()
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
            window.statusBarColor = Background.toArgb()
            window.navigationBarColor = Background.toArgb()
        }
    }
}
