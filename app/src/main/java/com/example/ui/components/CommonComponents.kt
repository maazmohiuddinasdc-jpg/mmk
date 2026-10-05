package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cable
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LaptopMac
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TabletAndroid
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Product
import com.example.data.model.ProductCategory
import com.example.ui.theme.MMAssuredBadgeYellow
import com.example.ui.theme.MMBlueDark
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBlueSubtle
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMGoldAccent
import com.example.ui.theme.MMGreenSuccess
import com.example.ui.theme.MMRatingHigh
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary
import com.example.ui.viewmodel.BottomTab
import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(amount: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    formatter.maximumFractionDigits = 0
    return formatter.format(amount)
}

@Composable
fun MMAssuredBadge(modifier: Modifier = Modifier, small: Boolean = false) {
    val h = if (small) 16.dp else 20.dp
    val textSp = if (small) 9.sp else 11.sp
    val starSize = if (small) 10.dp else 12.dp

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF1E88E5))
                )
            )
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = MMAssuredBadgeYellow,
                modifier = Modifier.size(starSize)
            )
            Text(
                text = "MM",
                color = MMAssuredBadgeYellow,
                fontSize = textSp,
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = "Assured",
                color = Color.White,
                fontSize = textSp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StarRatingBadge(
    rating: Float,
    ratingCount: Int? = null,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MMRatingHigh)
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = String.format(Locale.US, "%.1f", rating),
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(10.dp)
                )
            }
        }
        if (ratingCount != null) {
            val formattedCount = NumberFormat.getNumberInstance(Locale.US).format(ratingCount)
            Text(
                text = "($formattedCount)",
                fontSize = 11.sp,
                color = MMTextMuted,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PriceDisplay(
    price: Double,
    originalPrice: Double,
    discountPercent: Int,
    modifier: Modifier = Modifier,
    priceFontSize: TextUnit = 16.sp,
    showDiscountPercent: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        Text(
            text = formatCurrency(price),
            color = MMTextPrimary,
            fontSize = priceFontSize,
            fontWeight = FontWeight.ExtraBold
        )
        if (originalPrice > price) {
            Text(
                text = formatCurrency(originalPrice),
                color = MMTextMuted,
                fontSize = (priceFontSize.value * 0.78).sp,
                textDecoration = TextDecoration.LineThrough,
                fontWeight = FontWeight.Normal
            )
        }
        if (showDiscountPercent && discountPercent > 0) {
            Text(
                text = "$discountPercent% off",
                color = MMGreenSuccess,
                fontSize = (priceFontSize.value * 0.78).sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CategoryVectorIcon(category: ProductCategory): ImageVector {
    return when (category) {
        ProductCategory.MOBILES -> Icons.Default.PhoneAndroid
        ProductCategory.LAPTOPS -> Icons.Default.LaptopMac
        ProductCategory.TABLETS -> Icons.Default.TabletAndroid
        ProductCategory.SMARTWATCHES -> Icons.Default.Watch
        ProductCategory.TVS -> Icons.Default.Tv
        ProductCategory.MONITORS -> Icons.Default.DesktopWindows
        ProductCategory.HEADPHONES -> Icons.Default.Headphones
        ProductCategory.ACCESSORIES -> Icons.Default.Cable
    }
}

@Composable
fun TopMarketHeader(
    cartCount: Int,
    wishlistCount: Int,
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit,
    onWishlistClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MMBlueDark, MMBluePrimary)
                )
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        // Logo and action row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // MMKART brand logo text
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "MM",
                            color = MMGoldAccent,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            fontStyle = FontStyle.Italic
                        )
                        Text(
                            text = "KART",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            fontStyle = FontStyle.Italic
                        )
                        Text(
                            text = ".COM",
                            color = Color(0xFFB0D0FF),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                    Text(
                        text = "100% Branded Electronics",
                        color = Color(0xFFD4E5FF),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Wishlist icon
                IconButton(
                    onClick = onWishlistClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("header_wishlist_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (wishlistCount > 0) {
                                Badge(
                                    containerColor = MMGoldAccent,
                                    contentColor = Color.Black
                                ) {
                                    Text(
                                        text = wishlistCount.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Wishlist",
                            tint = Color.White
                        )
                    }
                }

                // Cart icon with count
                IconButton(
                    onClick = onCartClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("header_cart_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (cartCount > 0) {
                                Badge(
                                    containerColor = MMGoldAccent,
                                    contentColor = Color.Black
                                ) {
                                    Text(
                                        text = cartCount.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Shopping Cart",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Search bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { onSearchClick() }
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .testTag("home_search_bar")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MMBluePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Search Apple, Samsung, Laptops, TVs...",
                        color = MMTextMuted,
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Search",
                    tint = MMTextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun ProductGridCard(
    product: Product,
    isWishlisted: Boolean,
    onProductClick: () -> Unit,
    onWishlistToggle: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onProductClick() }
            .testTag("product_card_${product.id}"),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(MMBorder, MMBorder)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            // Product image + wishlist button + MM Assured badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF8FAFC)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Wishlist button top-right
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.9f))
                        .clickable { onWishlistToggle() }
                        .testTag("wishlist_btn_${product.id}"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) Color(0xFFE11D48) else MMTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Assured badge top-left
                if (product.isAssured) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                    ) {
                        MMAssuredBadge(small = true)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Brand tag
            Text(
                text = product.brand.uppercase(),
                color = MMBluePrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )

            // Product Title
            Text(
                text = product.name,
                color = MMTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 17.sp,
                modifier = Modifier.height(34.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Rating
            StarRatingBadge(rating = product.rating, ratingCount = product.ratingCount)

            Spacer(modifier = Modifier.height(6.dp))

            // Prices
            PriceDisplay(
                price = product.price,
                originalPrice = product.originalPrice,
                discountPercent = product.discountPercent,
                priceFontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Delivery notice
            Text(
                text = "Free delivery tomorrow",
                color = MMGreenSuccess,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Add to cart mini CTA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(MMBlueSubtle)
                    .clickable { onAddToCart() }
                    .padding(vertical = 6.dp)
                    .testTag("add_cart_btn_${product.id}"),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = MMBluePrimary,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "Add to Cart",
                        color = MMBluePrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun MMBottomNavigationBar(
    currentTab: BottomTab,
    cartItemCount: Int,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp),
        containerColor = Color.White,
        tonalElevation = 6.dp
    ) {
        NavigationBarItem(
            selected = currentTab == BottomTab.HOME,
            onClick = { onTabSelected(BottomTab.HOME) },
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MMBluePrimary,
                selectedTextColor = MMBluePrimary,
                indicatorColor = MMBlueSubtle,
                unselectedIconColor = MMTextSecondary,
                unselectedTextColor = MMTextSecondary
            ),
            modifier = Modifier.testTag("bottom_nav_home")
        )

        NavigationBarItem(
            selected = currentTab == BottomTab.CATEGORIES,
            onClick = { onTabSelected(BottomTab.CATEGORIES) },
            icon = { Icon(Icons.Outlined.Category, contentDescription = "Categories") },
            label = { Text("Categories", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MMBluePrimary,
                selectedTextColor = MMBluePrimary,
                indicatorColor = MMBlueSubtle,
                unselectedIconColor = MMTextSecondary,
                unselectedTextColor = MMTextSecondary
            ),
            modifier = Modifier.testTag("bottom_nav_categories")
        )

        NavigationBarItem(
            selected = currentTab == BottomTab.CART,
            onClick = { onTabSelected(BottomTab.CART) },
            icon = {
                BadgedBox(
                    badge = {
                        if (cartItemCount > 0) {
                            Badge(
                                containerColor = MMGoldAccent,
                                contentColor = Color.Black
                            ) {
                                Text(
                                    text = cartItemCount.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                ) {
                    Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart")
                }
            },
            label = { Text("Cart", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MMBluePrimary,
                selectedTextColor = MMBluePrimary,
                indicatorColor = MMBlueSubtle,
                unselectedIconColor = MMTextSecondary,
                unselectedTextColor = MMTextSecondary
            ),
            modifier = Modifier.testTag("bottom_nav_cart")
        )

        NavigationBarItem(
            selected = currentTab == BottomTab.ORDERS,
            onClick = { onTabSelected(BottomTab.ORDERS) },
            icon = { Icon(Icons.Outlined.LocalShipping, contentDescription = "Orders") },
            label = { Text("Orders", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MMBluePrimary,
                selectedTextColor = MMBluePrimary,
                indicatorColor = MMBlueSubtle,
                unselectedIconColor = MMTextSecondary,
                unselectedTextColor = MMTextSecondary
            ),
            modifier = Modifier.testTag("bottom_nav_orders")
        )

        NavigationBarItem(
            selected = currentTab == BottomTab.PROFILE,
            onClick = { onTabSelected(BottomTab.PROFILE) },
            icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = "Profile") },
            label = { Text("Profile", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MMBluePrimary,
                selectedTextColor = MMBluePrimary,
                indicatorColor = MMBlueSubtle,
                unselectedIconColor = MMTextSecondary,
                unselectedTextColor = MMTextSecondary
            ),
            modifier = Modifier.testTag("bottom_nav_profile")
        )
    }
}
