package com.example.ui.screens

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProductCategory
import com.example.ui.components.CategoryVectorIcon
import com.example.ui.components.MMAssuredBadge
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBlueSubtle
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary

@Composable
fun CategoriesScreen(
    onCategoryClick: (ProductCategory) -> Unit,
    onBrandClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryDetails = mapOf(
        ProductCategory.MOBILES to ("5G Flagships, Foldables & AI Phones" to "Apple, Samsung, OnePlus, Xiaomi"),
        ProductCategory.LAPTOPS to ("Ultra-slim, Pro MacBooks & Gaming Laptops" to "Apple, Dell, HP, Lenovo"),
        ProductCategory.TABLETS to ("OLED Creative Tablets & S-Pen Devices" to "Apple iPad, Samsung Tab"),
        ProductCategory.SMARTWATCHES to ("Titanium Rugged & AI Health Trackers" to "Apple Watch, Galaxy Watch"),
        ProductCategory.TVS to ("4K OLED, Mini-LED & QLED Smart TVs" to "Sony, LG, Samsung, Xiaomi"),
        ProductCategory.MONITORS to ("4K Thunderbolt Hubs & 240Hz Curved Gaming" to "Dell UltraSharp, Samsung Odyssey"),
        ProductCategory.HEADPHONES to ("Over-ear ANC, Lossless Hi-Fi & True Wireless" to "Sony WH-1000, AirPods Max"),
        ProductCategory.ACCESSORIES to ("GaN 140W Chargers, Wireless Pads & Docks" to "Apple, Samsung, HP")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        // Categories Top Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Electronics Departments",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MMTextPrimary
                    )
                    Text(
                        text = "Explore 100% Genuine Branded Electronics",
                        fontSize = 12.sp,
                        color = MMTextMuted
                    )
                }
                MMAssuredBadge()
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(ProductCategory.values()) { category ->
                val (desc, brands) = categoryDetails[category] ?: ("" to "")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategoryClick(category) }
                        .testTag("category_item_${category.name}"),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = CardDefaults.outlinedCardBorder().copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(MMBorder)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
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

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = category.displayName,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MMTextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = desc,
                                fontSize = 11.sp,
                                color = MMTextSecondary,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Brands: $brands",
                                fontSize = 10.sp,
                                color = MMBluePrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Open",
                            tint = MMTextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
