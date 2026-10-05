package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Product
import com.example.ui.components.MMAssuredBadge
import com.example.ui.components.PriceDisplay
import com.example.ui.components.StarRatingBadge
import com.example.ui.components.formatCurrency
import com.example.ui.theme.MMAssuredBadgeYellow
import com.example.ui.theme.MMBlueDark
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBlueSubtle
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMGoldAccent
import com.example.ui.theme.MMGreenLight
import com.example.ui.theme.MMGreenSuccess
import com.example.ui.theme.MMRatingHigh
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductDetailScreen(
    product: Product,
    isWishlisted: Boolean,
    cartCount: Int,
    pincode: String,
    isPincodeChecked: Boolean,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onWishlistToggle: () -> Unit,
    onCheckPincode: (String) -> Unit,
    onAddToCart: (Product, String, String) -> Unit,
    onBuyNow: (Product, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedColor by remember { mutableStateOf(product.colorVariants.firstOrNull() ?: "Standard") }
    var selectedStorage by remember { mutableStateOf(product.storageVariants.firstOrNull() ?: "Standard") }
    var enteredPincode by remember { mutableStateOf(pincode) }
    var showAllSpecs by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        // App Bar with White Surface & Navigation Controls
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("detail_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MMTextPrimary
                    )
                }

                Text(
                    text = product.name,
                    color = MMTextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = onWishlistToggle) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) Color(0xFFDC2626) else MMTextSecondary
                    )
                }

                IconButton(
                    onClick = onCartClick,
                    modifier = Modifier.testTag("detail_cart_btn")
                ) {
                    BadgedBox(
                        badge = {
                            if (cartCount > 0) {
                                Badge(containerColor = MMBluePrimary, contentColor = Color.White) {
                                    Text(text = cartCount.toString(), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = MMTextPrimary
                        )
                    }
                }
            }
        }

        // Scrollable Body
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // 1. Product Image Showcase
            item {
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF8FAFC)),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = product.imageUrl,
                                contentDescription = product.name,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp)
                            )

                            if (product.isAssured) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(8.dp)
                                ) {
                                    MMAssuredBadge()
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MMBluePrimary))
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color.LightGray))
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color.LightGray))
                        }
                    }
                }
            }

            // 2. Title, Ratings & Price Block
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = product.brand.uppercase(),
                            color = MMBluePrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = product.name,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = MMTextPrimary,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Ratings & Reviews count row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            StarRatingBadge(rating = product.rating)
                            val ratStr = NumberFormat.getNumberInstance(Locale.US).format(product.ratingCount)
                            val revStr = NumberFormat.getNumberInstance(Locale.US).format(product.reviewCount)
                            Text(
                                text = "$ratStr Ratings & $revStr Reviews",
                                fontSize = 12.sp,
                                color = MMTextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Pricing block
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = formatCurrency(product.price),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MMTextPrimary
                            )
                            if (product.originalPrice > product.price) {
                                Text(
                                    text = formatCurrency(product.originalPrice),
                                    fontSize = 16.sp,
                                    color = MMTextMuted,
                                    textDecoration = TextDecoration.LineThrough
                                )
                                Text(
                                    text = "${product.discountPercent}% off",
                                    fontSize = 16.sp,
                                    color = MMGreenSuccess,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // EMI Hint
                        val emiMonthly = (product.price / 12).toInt()
                        Text(
                            text = "Or ${formatCurrency(emiMonthly.toDouble())}/month for 12 months with No Cost EMI",
                            fontSize = 12.sp,
                            color = MMBluePrimary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Inclusive of all taxes & free shipping",
                            fontSize = 11.sp,
                            color = MMTextMuted
                        )
                    }
                }
            }

            // 3. Variant Selector (Color & Storage)
            if (product.colorVariants.isNotEmpty() || product.storageVariants.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (product.colorVariants.isNotEmpty()) {
                                Text(
                                    text = "Color: $selectedColor",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MMTextPrimary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(product.colorVariants) { color ->
                                        val isSelected = color == selectedColor
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (isSelected) MMBlueSubtle else Color(0xFFF8FAFC))
                                                .border(
                                                    width = if (isSelected) 1.5.dp else 1.dp,
                                                    color = if (isSelected) MMBluePrimary else MMBorder,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .clickable { selectedColor = color }
                                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                        ) {
                                            Text(
                                                text = color,
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) MMBluePrimary else MMTextPrimary
                                            )
                                        }
                                    }
                                }
                            }

                            if (product.storageVariants.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = "Storage / Configuration: $selectedStorage",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MMTextPrimary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(product.storageVariants) { storage ->
                                        val isSelected = storage == selectedStorage
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (isSelected) MMBlueSubtle else Color(0xFFF8FAFC))
                                                .border(
                                                    width = if (isSelected) 1.5.dp else 1.dp,
                                                    color = if (isSelected) MMBluePrimary else MMBorder,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .clickable { selectedStorage = storage }
                                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                        ) {
                                            Text(
                                                text = storage,
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) MMBluePrimary else MMTextPrimary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. Delivery & Pincode Checker
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(20.dp))
                            Text("Delivery Options", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = enteredPincode,
                                onValueChange = { if (it.length <= 6) enteredPincode = it },
                                placeholder = { Text("Enter 6-digit Pincode", fontSize = 12.sp) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(52.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MMBluePrimary,
                                    unfocusedBorderColor = MMBorder
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { onCheckPincode(enteredPincode) },
                                colors = ButtonDefaults.buttonColors(containerColor = MMBluePrimary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(52.dp)
                            ) {
                                Text("Check", fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (isPincodeChecked) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MMGreenSuccess, modifier = Modifier.size(16.dp))
                                Text(
                                    text = "FREE Delivery by Tomorrow 11:00 AM with MM Assured",
                                    fontSize = 12.sp,
                                    color = MMGreenSuccess,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = "7 Days Replacement Policy & Cash on Delivery Available",
                                fontSize = 11.sp,
                                color = MMTextSecondary,
                                modifier = Modifier.padding(start = 22.dp, top = 2.dp)
                            )
                        }
                    }
                }
            }

            // 5. Available Bank Offers
            if (product.bankOffers.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocalOffer, contentDescription = null, tint = MMGoldAccent, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Available Bank & Card Offers", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            for (offer in product.bankOffers) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = MMGreenSuccess,
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(top = 2.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = offer,
                                        fontSize = 12.sp,
                                        color = MMTextSecondary,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 6. Highlights Bullet Points
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Product Highlights", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                        Spacer(modifier = Modifier.height(8.dp))
                        for (highlight in product.shortHighlights) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 6.dp)
                                        .size(5.dp)
                                        .clip(CircleShape)
                                        .background(MMBluePrimary)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = highlight,
                                    fontSize = 12.sp,
                                    color = MMTextPrimary,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }
                }
            }

            // 7. Technical Specifications Table
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("All Specifications", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                            Text(
                                text = if (showAllSpecs) "Show Less" else "View All",
                                fontSize = 12.sp,
                                color = MMBluePrimary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { showAllSpecs = !showAllSpecs }
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        val displaySpecs = if (showAllSpecs) product.specifications else product.specifications.toList().take(2).toMap()
                        for ((section, items) in displaySpecs) {
                            Text(
                                text = section.uppercase(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MMBlueDark,
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.padding(top = 6.dp, bottom = 4.dp)
                            )
                            for ((key, value) in items) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(
                                        text = key,
                                        fontSize = 12.sp,
                                        color = MMTextMuted,
                                        modifier = Modifier.weight(0.4f)
                                    )
                                    Text(
                                        text = value,
                                        fontSize = 12.sp,
                                        color = MMTextPrimary,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(0.6f)
                                    )
                                }
                                HorizontalDivider(color = Color(0xFFF1F5F9))
                            }
                        }
                    }
                }
            }

            // 8. Ratings & Customer Reviews
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Ratings & Reviews", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)

                        Spacer(modifier = Modifier.height(12.dp))

                        // Rating summary bar chart
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(0.35f)
                            ) {
                                Text(
                                    text = String.format(Locale.US, "%.1f", product.rating),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MMTextPrimary
                                )
                                Row {
                                    repeat(5) {
                                        Icon(Icons.Default.Star, contentDescription = null, tint = MMGoldAccent, modifier = Modifier.size(14.dp))
                                    }
                                }
                                Text(
                                    text = "${NumberFormat.getNumberInstance(Locale.US).format(product.ratingCount)} ratings",
                                    fontSize = 11.sp,
                                    color = MMTextMuted
                                )
                            }

                            Column(
                                modifier = Modifier.weight(0.65f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                val distribution = listOf(0.75f, 0.15f, 0.05f, 0.03f, 0.02f)
                                for ((idx, frac) in distribution.withIndex()) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("${5 - idx}★", fontSize = 10.sp, color = MMTextSecondary, modifier = Modifier.width(20.dp))
                                        LinearProgressIndicator(
                                            progress = { frac },
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(6.dp)
                                                .clip(RoundedCornerShape(3.dp)),
                                            color = if (idx == 0) MMRatingHigh else if (idx == 1) MMGreenSuccess else MMGoldAccent,
                                            trackColor = Color(0xFFE2E8F0),
                                            strokeCap = StrokeCap.Round
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                        Spacer(modifier = Modifier.height(12.dp))

                        // Customer review cards
                        for (review in product.reviews) {
                            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    StarRatingBadge(rating = review.rating)
                                    Text(
                                        text = review.title,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MMTextPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = review.comment,
                                    fontSize = 12.sp,
                                    color = MMTextSecondary,
                                    lineHeight = 17.sp
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "${review.userName}, ${review.userCity}",
                                        fontSize = 11.sp,
                                        color = MMTextMuted,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text("•", color = MMTextMuted, fontSize = 10.sp)
                                    Text(
                                        text = review.date,
                                        fontSize = 11.sp,
                                        color = MMTextMuted
                                    )
                                    if (review.verifiedBuyer) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MMGreenSuccess, modifier = Modifier.size(12.dp))
                                        Text(
                                            text = "Verified Buyer",
                                            fontSize = 10.sp,
                                            color = MMGreenSuccess,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                            HorizontalDivider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }

        // Sticky Bottom Action Bar (Add to Cart & Buy Now)
        Surface(
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Add to Cart Button (White with blue border)
                Button(
                    onClick = { onAddToCart(product, selectedColor, selectedStorage) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("detail_add_to_cart_btn"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MMBlueSubtle,
                        contentColor = MMBluePrimary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add to Cart", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }

                // Buy Now Button (Flipkart Gold / Vibrant Orange)
                Button(
                    onClick = { onBuyNow(product, selectedColor, selectedStorage) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("detail_buy_now_btn"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9F00),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FlashOn,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Buy Now", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}
