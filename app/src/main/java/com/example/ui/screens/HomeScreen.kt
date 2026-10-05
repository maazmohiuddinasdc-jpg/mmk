package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Address
import com.example.data.model.Product
import com.example.data.model.ProductCategory
import com.example.data.repository.ProductRepository
import com.example.ui.components.CategoryVectorIcon
import com.example.ui.components.MMAssuredBadge
import com.example.ui.components.ProductGridCard
import com.example.ui.components.TopMarketHeader
import com.example.ui.theme.MMAssuredBadgeYellow
import com.example.ui.theme.MMBlueDark
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBlueSubtle
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMGoldAccent
import com.example.ui.theme.MMGreenSuccess
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    products: List<Product>,
    cartCount: Int,
    wishlistIds: Set<String>,
    selectedAddress: Address,
    onProductClick: (Product) -> Unit,
    onCategoryClick: (ProductCategory) -> Unit,
    onBrandClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onWishlistToggle: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    // Deal of the day countdown simulation
    var secondsLeft by remember { mutableIntStateOf(15420) }
    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft -= 1
        }
    }
    val hours = secondsLeft / 3600
    val minutes = (secondsLeft % 3600) / 60
    val seconds = secondsLeft % 60
    val timeString = String.format("%02dh : %02dm : %02ds", hours, minutes, seconds)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        // Sticky Flipkart Blue Header
        TopMarketHeader(
            cartCount = cartCount,
            wishlistCount = wishlistIds.size,
            onSearchClick = onSearchClick,
            onCartClick = onCartClick,
            onWishlistClick = onWishlistClick
        )

        // Location & Pincode Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE2EDFE))
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MMBluePrimary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Deliver to ${selectedAddress.fullName.split(" ").firstOrNull() ?: "Alex"} - ${selectedAddress.city} ${selectedAddress.pincode}",
                fontSize = 11.sp,
                color = MMTextPrimary,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Change",
                fontSize = 11.sp,
                color = MMBluePrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /* Address select */ }
            )
        }

        // Main Scrollable Body
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // 1. Categories Horizontal Row
            item {
                Surface(
                    color = Color.White,
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(ProductCategory.values()) { category ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clickable { onCategoryClick(category) }
                                    .testTag("home_category_${category.name}")
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(MMBlueSubtle),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = CategoryVectorIcon(category),
                                        contentDescription = category.displayName,
                                        tint = MMBluePrimary,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = category.displayName,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MMTextPrimary
                                )
                            }
                        }
                    }
                }
            }

            // 2. Hero Offer Banners
            item {
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Banner 1: Flagship Sale
                    item {
                        Card(
                            modifier = Modifier
                                .width(330.dp)
                                .height(160.dp)
                                .clickable { onCategoryClick(ProductCategory.MOBILES) },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.banner_flagship),
                                    contentDescription = "Flagship Smartphones",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                // Gradient Overlay with text
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.horizontalGradient(
                                                colors = listOf(
                                                    Color(0xEE083C94),
                                                    Color(0x990A58CA),
                                                    Color.Transparent
                                                )
                                            )
                                        )
                                        .padding(16.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(0.7f),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(MMGoldAccent)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "MEGA FLAGSHIP FESTIVAL",
                                                color = Color.Black,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "iPhone 16 Pro & Galaxy S25",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            lineHeight = 20.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Up to ₹15,000 Bank & Exchange Bonus",
                                            color = Color(0xFFD4E5FF),
                                            fontSize = 11.sp
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color.White)
                                                .padding(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Shop Now >",
                                                color = MMBluePrimary,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Banner 2: Pro Laptops & Monitors
                    item {
                        Card(
                            modifier = Modifier
                                .width(330.dp)
                                .height(160.dp)
                                .clickable { onCategoryClick(ProductCategory.LAPTOPS) },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.banner_laptops),
                                    contentDescription = "Pro Laptops & Monitors",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.horizontalGradient(
                                                colors = listOf(
                                                    Color(0xEE0A192F),
                                                    Color(0x880C5ADB),
                                                    Color.Transparent
                                                )
                                            )
                                        )
                                        .padding(16.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(0.7f),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFF22C55E))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "PRO WORKSTATIONS",
                                                color = Color.White,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "MacBook M4 & OLED Monitors",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            lineHeight = 20.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "No Cost EMI from ₹4,999/mo",
                                            color = Color(0xFFE2E8F0),
                                            fontSize = 11.sp
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(MMGoldAccent)
                                                .padding(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "Explore Deals >",
                                                color = Color.Black,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 3. Trust Badges Strip (Flipkart / MMKART Assurances)
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, MMBorder, RoundedCornerShape(8.dp))
                        .padding(vertical = 10.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("100% Genuine", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MMTextPrimary)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = MMGreenSuccess, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Brand Warranty", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MMTextPrimary)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Speed, contentDescription = null, tint = MMGoldAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("7-Day Replacement", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MMTextPrimary)
                    }
                }
            }

            // 4. Official Brand Stores
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(vertical = 12.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "OFFICIAL BRAND STORES",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MMTextPrimary
                                )
                                Text(
                                    text = "Direct from authorized brand partners",
                                    fontSize = 11.sp,
                                    color = MMTextMuted
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 14.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(ProductRepository.brands) { brand ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFF8FAFC))
                                        .border(1.dp, MMBorder, RoundedCornerShape(8.dp))
                                        .clickable { onBrandClick(brand) }
                                        .padding(horizontal = 16.dp, vertical = 10.dp)
                                        .testTag("brand_chip_$brand"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = brand,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = MMBlueDark
                                        )
                                        Text(
                                            text = "Official Store",
                                            fontSize = 9.sp,
                                            color = MMGreenSuccess,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 5. Deal of the Day with Countdown
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFFECE5)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ElectricBolt,
                                        contentDescription = null,
                                        tint = Color(0xFFEA580C),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Deals of the Day",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MMTextPrimary
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Timer,
                                            contentDescription = null,
                                            tint = MMTextSecondary,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Text(
                                            text = timeString,
                                            fontSize = 11.sp,
                                            color = Color(0xFFEA580C),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MMBluePrimary)
                                    .clickable { onCategoryClick(ProductCategory.MOBILES) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "View All",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Deal products horizontal scroll
                        val dealProducts = products.filter { it.isDealOfTheDay || it.discountPercent > 10 }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(dealProducts) { product ->
                                Box(modifier = Modifier.width(180.dp)) {
                                    ProductGridCard(
                                        product = product,
                                        isWishlisted = wishlistIds.contains(product.id),
                                        onProductClick = { onProductClick(product) },
                                        onWishlistToggle = { onWishlistToggle(product.id) },
                                        onAddToCart = { onAddToCart(product) }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 6. Trending Electronics (2 Column Flipkart Grid)
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Trending in Electronics",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MMTextPrimary
                            )
                            Text(
                                text = "Top-selling branded gadgets this week",
                                fontSize = 11.sp,
                                color = MMTextMuted
                            )
                        }
                        MMAssuredBadge()
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 2-column paired layout
                    val trending = products.take(6)
                    val chunked = trending.chunked(2)
                    for (rowItems in chunked) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            for (product in rowItems) {
                                Box(modifier = Modifier.weight(1f)) {
                                    ProductGridCard(
                                        product = product,
                                        isWishlisted = wishlistIds.contains(product.id),
                                        onProductClick = { onProductClick(product) },
                                        onWishlistToggle = { onWishlistToggle(product.id) },
                                        onAddToCart = { onAddToCart(product) }
                                    )
                                }
                            }
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // 7. Exchange & SuperCoins Savings Banner
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MMBlueDark)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocalOffer, contentDescription = null, tint = MMGoldAccent, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("MM EXCHANGE BONUS", color = MMGoldAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Exchange Your Old Phone or Laptop",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Instant evaluation & doorstep pickup. Get up to ₹25,000 off.",
                                color = Color(0xFFCBD5E1),
                                fontSize = 11.sp
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Exchange",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // 8. Top Audio & Entertainment
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Premium Audio & 4K OLED TVs",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MMTextPrimary
                        )
                        Text(
                            text = "Sony, LG, Apple & Samsung Home Entertainment",
                            fontSize = 11.sp,
                            color = MMTextMuted
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        val audioTvs = products.filter { it.category == ProductCategory.HEADPHONES || it.category == ProductCategory.TVS }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(audioTvs) { product ->
                                Box(modifier = Modifier.width(180.dp)) {
                                    ProductGridCard(
                                        product = product,
                                        isWishlisted = wishlistIds.contains(product.id),
                                        onProductClick = { onProductClick(product) },
                                        onWishlistToggle = { onWishlistToggle(product.id) },
                                        onAddToCart = { onAddToCart(product) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
