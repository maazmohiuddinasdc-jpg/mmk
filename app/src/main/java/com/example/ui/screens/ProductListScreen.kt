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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FilterState
import com.example.data.model.Product
import com.example.data.model.SortOption
import com.example.data.repository.ProductRepository
import com.example.ui.components.ProductGridCard
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBlueSubtle
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMGoldAccent
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    title: String,
    products: List<Product>,
    wishlistIds: Set<String>,
    cartCount: Int,
    activeSortOption: SortOption,
    activeFilterState: FilterState,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onWishlistToggle: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    onSortOptionChange: (SortOption) -> Unit,
    onFilterChange: (FilterState) -> Unit,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSortSheet by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        // App Bar with Flipkart Blue
        Surface(
            color = MMBluePrimary,
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
                    modifier = Modifier.testTag("product_list_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${products.size} items available",
                        color = Color(0xFFD4E5FF),
                        fontSize = 11.sp
                    )
                }

                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = onCartClick,
                    modifier = Modifier.testTag("product_list_cart_btn")
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
                            contentDescription = "Cart",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        // Sort & Filter Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, MMBorder)
        ) {
            // Sort Button
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { showSortSheet = true }
                    .padding(vertical = 12.dp)
                    .testTag("sort_button"),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Sort,
                    contentDescription = "Sort",
                    tint = MMBluePrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Sort: ${activeSortOption.title}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MMTextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(30.dp)
                    .background(MMBorder)
                    .align(Alignment.CenterVertically)
            )

            // Filter Button
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable { showFilterSheet = true }
                    .padding(vertical = 12.dp)
                    .testTag("filter_button"),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = MMBluePrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                val filterCount = activeFilterState.selectedBrands.size +
                        (if (activeFilterState.assuredOnly) 1 else 0) +
                        (if (activeFilterState.minRating > 0f) 1 else 0)
                Text(
                    text = if (filterCount > 0) "Filter ($filterCount)" else "Filter",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MMTextPrimary
                )
            }
        }

        // Active Filter Chips Horizontal Row
        val hasActiveFilters = activeFilterState.selectedBrands.isNotEmpty() ||
                activeFilterState.assuredOnly ||
                activeFilterState.minRating > 0f
        if (hasActiveFilters) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8FAFC))
            ) {
                items(activeFilterState.selectedBrands.toList()) { brand ->
                    FilterChip(
                        selected = true,
                        onClick = {
                            onFilterChange(
                                activeFilterState.copy(selectedBrands = activeFilterState.selectedBrands - brand)
                            )
                        },
                        label = { Text("Brand: $brand", fontSize = 11.sp) },
                        trailingIcon = { Icon(Icons.Default.Close, contentDescription = "Remove", modifier = Modifier.size(14.dp)) },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MMBlueSubtle, selectedLabelColor = MMBluePrimary)
                    )
                }
                if (activeFilterState.assuredOnly) {
                    item {
                        FilterChip(
                            selected = true,
                            onClick = { onFilterChange(activeFilterState.copy(assuredOnly = false)) },
                            label = { Text("MM Assured", fontSize = 11.sp) },
                            trailingIcon = { Icon(Icons.Default.Close, contentDescription = "Remove", modifier = Modifier.size(14.dp)) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MMBlueSubtle, selectedLabelColor = MMBluePrimary)
                        )
                    }
                }
                if (activeFilterState.minRating > 0f) {
                    item {
                        FilterChip(
                            selected = true,
                            onClick = { onFilterChange(activeFilterState.copy(minRating = 0f)) },
                            label = { Text("${activeFilterState.minRating}★ & above", fontSize = 11.sp) },
                            trailingIcon = { Icon(Icons.Default.Close, contentDescription = "Remove", modifier = Modifier.size(14.dp)) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MMBlueSubtle, selectedLabelColor = MMBluePrimary)
                        )
                    }
                }
                item {
                    TextButton(onClick = onClearFilters) {
                        Text("Clear All", fontSize = 11.sp, color = Color(0xFFDC2626), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Product Grid or Empty State
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No Electronics Found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MMTextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Try clearing filters or searching for other brands",
                        fontSize = 13.sp,
                        color = MMTextMuted
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onClearFilters,
                        colors = ButtonDefaults.buttonColors(containerColor = MMBluePrimary)
                    ) {
                        Text("Reset All Filters")
                    }
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products, key = { it.id }) { product ->
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

    // Sort Bottom Sheet
    if (showSortSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSortSheet = false },
            sheetState = rememberModalBottomSheetState(),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "SORT BY",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MMTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
                for (option in SortOption.values()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSortOptionChange(option)
                                showSortSheet = false
                            }
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = option.title,
                            fontSize = 14.sp,
                            fontWeight = if (option == activeSortOption) FontWeight.Bold else FontWeight.Normal,
                            color = if (option == activeSortOption) MMBluePrimary else MMTextPrimary
                        )
                        if (option == activeSortOption) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(MMBluePrimary)
                            )
                        }
                    }
                }
            }
        }
    }

    // Filter Bottom Sheet
    if (showFilterSheet) {
        var tempBrands by remember { mutableStateOf(activeFilterState.selectedBrands) }
        var tempAssured by remember { mutableStateOf(activeFilterState.assuredOnly) }
        var tempMinRating by remember { mutableFloatStateOf(activeFilterState.minRating) }

        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            sheetState = rememberModalBottomSheetState(),
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .padding(bottom = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "FILTERS",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MMTextPrimary
                    )
                    TextButton(onClick = {
                        tempBrands = emptySet()
                        tempAssured = false
                        tempMinRating = 0f
                    }) {
                        Text("Reset", color = Color(0xFFDC2626), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Brands
                Text(
                    text = "BRANDS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MMTextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(ProductRepository.brands) { brand ->
                        val isSelected = tempBrands.contains(brand)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                tempBrands = if (isSelected) tempBrands - brand else tempBrands + brand
                            },
                            label = { Text(brand, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MMBluePrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // MM Assured Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MM Assured Only",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MMTextPrimary
                        )
                        Text(
                            text = "6-level quality checked with fast delivery",
                            fontSize = 11.sp,
                            color = MMTextMuted
                        )
                    }
                    Switch(
                        checked = tempAssured,
                        onCheckedChange = { tempAssured = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = MMBluePrimary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Minimum Rating
                Text(
                    text = "MINIMUM RATING: ${if (tempMinRating > 0f) "${tempMinRating.toInt()}★ & above" else "Any"}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MMTextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (rating in listOf(0f, 4.0f, 4.5f, 4.8f)) {
                        val isSelected = tempMinRating == rating
                        FilterChip(
                            selected = isSelected,
                            onClick = { tempMinRating = rating },
                            label = { Text(if (rating == 0f) "All" else "${rating}★+", fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MMBluePrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Apply button
                Button(
                    onClick = {
                        onFilterChange(
                            activeFilterState.copy(
                                selectedBrands = tempBrands,
                                assuredOnly = tempAssured,
                                minRating = tempMinRating
                            )
                        )
                        showFilterSheet = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MMBluePrimary)
                ) {
                    Text("Apply Filters", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}
