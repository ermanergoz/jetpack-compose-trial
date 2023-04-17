package com.example.myapplication

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.*
import com.smarttoolfactory.ratingbar.RatingBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    isScrolled: Boolean = false,
    productName: String,
    onButtonClick: () -> Unit
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
                    .clickable {
                        onButtonClick()
                    })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductCarousel(modifier: Modifier = Modifier, imageUrls: List<String>) {
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
                AsyncImage(
                    model = imageUrls[page],
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .clip(shape = Shapes.large)
                .background(PriceBackground)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_carousal_coin), contentDescription = null
            )
            Text(text = stringResource(id = R.string.plus_hundred), fontWeight = FontWeight.Bold)
            Divider(Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.coins))
        }
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
fun ProductInfo(modifier: Modifier = Modifier, product: Product, onAttributeClick: () -> Unit) {
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
                text = product.description,
                style = Typography.h6,
                modifier = Modifier.padding(0.dp, vertical = 8.dp)
            )
            Text(
                text = stringResource(R.string.description),
                modifier = Modifier.padding(0.dp, vertical = 8.dp)
            )
            Text(
                text = stringResource(R.string.more),
                color = ColorAccent,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(0.dp, vertical = 8.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.product_attributes),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(0.dp, vertical = 8.dp)
            )
            Text(text = stringResource(R.string.product_attribute))

            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                product.leapAttributes.forEach { attribute ->
                    when (attribute) {
                        "isBrandWithPurpose" -> {
                            AttributeItem(
                                R.drawable.icn_is_brand_with_purpose_pdp,
                                stringResource(R.string.brand_with_purpose),
                                onAttributeClick
                            )
                        }
                        "isPetaLeapingBunnyAccredited" -> {
                            AttributeItem(
                                R.drawable.icn_is_peta_leaping_bunny_accredited_pdp,
                                stringResource(R.string.peta_accredited),
                                onAttributeClick
                            )
                        }
                        // TODO: Add the rest
                    }
                }
            }
        }
    }
}

@Composable
private fun AttributeItem(
    backgroundResId: Int, attributeName: String, onAttributeClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp, 16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.clickable {
            onAttributeClick()
        }) {
            Image(
                painter = painterResource(R.drawable.bg_polygon), contentDescription = null
            )
            Icon(
                painter = painterResource(backgroundResId),
                contentDescription = null,
                tint = ProductAttributeIconColor,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(TextBackground)
                .padding(8.dp)
        ) {
            Text(
                text = attributeName,
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
            text = stringResource(R.string.product_rating),
            style = Typography.h6,
            modifier = Modifier.padding(0.dp, 8.dp)
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
                        Text(text = stringResource(R.string.out_of_full_rating))
                    } else {
                        Text(text = stringResource(R.string.no_reviews), style = Typography.h6)
                    }
                }
                var basedOnReviewCountText =
                    stringResource(R.string.based_on_review_count, product.reviewCount)
                if (product.reviewCount == 0) {
                    basedOnReviewCountText = stringResource(R.string.check_back_soon)
                }
                Text(text = basedOnReviewCountText, color = Color.LightGray)
                Spacer(modifier = Modifier.height(24.dp))
                RatingBar(
                    rating = product.ratingValue.toFloat(),
                    painterEmpty = painterResource(id = R.drawable.icn_star_unfilled),
                    painterFilled = painterResource(id = R.drawable.icn_star_filled),
                    tintEmpty = ColorAccent,
                    gestureEnabled = false,
                    space = 8.dp
                )
            }
        }
    }
}

@Composable
fun SimilarProducts(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .padding(8.dp, 8.dp)
            .then(modifier)
    ) {
        Text(
            text = stringResource(R.string.similar_products),
            style = Typography.h6,
            modifier = Modifier.padding(8.dp, 8.dp)
        )
        LazyRow() {
            items(5) {
                Box(
                    modifier = Modifier
                        .padding(8.dp, 0.dp)
                        .clip(shape = Shapes.medium)
                        .background(Color.White)
                        .size(160.dp, 240.dp)
                )
            }
        }
    }
}

@Composable
fun AddFavorites(modifier: Modifier = Modifier, onButtonClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp)
            .then(modifier)
    ) {
        Button(
            onClick = { onButtonClick() },
            shape = Shapes.large,
            colors = ButtonDefaults.buttonColors(
                ColorAccent
            ),
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.add_to_favorites).uppercase())
        }
    }
}