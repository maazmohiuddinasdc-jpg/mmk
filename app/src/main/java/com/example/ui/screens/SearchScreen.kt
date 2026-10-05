package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBlueSubtle
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    recentSearches: List<String>,
    allProducts: List<Product>,
    onBackClick: () -> Unit,
    onSearchSubmit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val trendingKeywords = listOf(
        "iPhone 16 Pro Max", "Samsung S25 Ultra", "MacBook Pro M4",
        "Sony WH-1000XM5", "OLED TV 65 inch", "OnePlus 13",
        "Legion Gaming Laptop", "Apple Watch Ultra 2", "4K Monitor"
    )

    val matchingProducts = remember(query) {
        if (query.isBlank()) emptyList()
        else allProducts.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.brand.contains(query, ignoreCase = true) ||
            it.category.displayName.contains(query, ignoreCase = true)
        }.take(6)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // App Bar with Search Box
        Surface(
            color = MMBluePrimary,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Search branded electronics...", fontSize = 13.sp, color = MMTextMuted) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .focusRequester(focusRequester)
                        .testTag("search_text_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.dp),
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray, modifier = Modifier.size(18.dp))
                            }
                        } else {
                            Icon(Icons.Default.Mic, contentDescription = "Voice", tint = MMBluePrimary, modifier = Modifier.size(18.dp))
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearchSubmit(query) })
                )

                Spacer(modifier = Modifier.width(6.dp))
            }
        }

        // Search Content: suggestions or history
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            if (query.isNotBlank()) {
                // Live Suggestions List
                items(matchingProducts) { product ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSearchSubmit(product.name) }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = MMTextMuted, modifier = Modifier.size(18.dp))
                            Column {
                                Text(product.name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MMTextPrimary)
                                Text("in ${product.category.displayName} • ${product.brand}", fontSize = 11.sp, color = MMTextMuted)
                            }
                        }
                        Icon(Icons.Default.NorthWest, contentDescription = null, tint = MMTextMuted, modifier = Modifier.size(16.dp))
                    }
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                }
            } else {
                // Recent Searches
                if (recentSearches.isNotEmpty()) {
                    item {
                        Text(
                            text = "RECENT SEARCHES",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MMTextSecondary,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            recentSearches.forEach { search ->
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color(0xFFF1F5F9))
                                        .clickable { onSearchSubmit(search) }
                                        .padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.History, contentDescription = null, tint = MMTextSecondary, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(search, fontSize = 12.sp, color = MMTextPrimary)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                // Trending Keywords
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "POPULAR IN ELECTRONICS",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MMBluePrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    trendingKeywords.forEach { keyword ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSearchSubmit(keyword) }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = MMTextMuted, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(keyword, fontSize = 13.sp, color = MMTextPrimary, fontWeight = FontWeight.Medium)
                        }
                        HorizontalDivider(color = Color(0xFFF8FAFC))
                    }
                }
            }
        }
    }
}
