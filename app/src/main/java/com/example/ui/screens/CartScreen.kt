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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Address
import com.example.data.model.CartItem
import com.example.ui.components.MMAssuredBadge
import com.example.ui.components.PriceDisplay
import com.example.ui.components.formatCurrency
import com.example.ui.theme.MMAssuredBadgeYellow
import com.example.ui.theme.MMBlueDark
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBlueSubtle
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMGoldAccent
import com.example.ui.theme.MMGreenLight
import com.example.ui.theme.MMGreenSuccess
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    selectedAddress: Address,
    appliedCoupon: String?,
    couponDiscount: Double,
    cartTotalMRP: Double,
    cartSellingTotal: Double,
    deliveryFee: Double,
    finalAmount: Double,
    totalSavings: Double,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onRemoveItem: (CartItem) -> Unit,
    onApplyCoupon: (String) -> Unit,
    onRemoveCoupon: () -> Unit,
    onProceedToCheckout: () -> Unit,
    onExploreShop: () -> Unit,
    modifier: Modifier = Modifier
) {
    var couponInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        // Top Header
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Shopping Cart (${cartItems.sumOf { it.quantity }})",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MMTextPrimary
                    )
                    MMAssuredBadge()
                }

                // Delivery address strip
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF8FAFC))
                        .border(1.dp, MMBorder)
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Deliver to: ${selectedAddress.fullName}, ${selectedAddress.pincode}",
                        fontSize = 12.sp,
                        color = MMTextPrimary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        if (cartItems.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MMBlueSubtle),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = MMBluePrimary,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your Cart is Empty!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MMTextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Explore branded electronics deals and fill it up",
                        fontSize = 13.sp,
                        color = MMTextMuted
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onExploreShop,
                        colors = ButtonDefaults.buttonColors(containerColor = MMBluePrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Explore Deals", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // Cart items and Bill details
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                // Cart Items
                items(cartItems) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                // Thumbnail
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(0xFFF8FAFC)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AsyncImage(
                                        model = item.product.imageUrl,
                                        contentDescription = item.product.name,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                // Details
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.product.brand.uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MMBluePrimary
                                    )
                                    Text(
                                        text = item.product.name,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MMTextPrimary,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Variant: ${item.selectedColor} | ${item.selectedStorage}",
                                        fontSize = 11.sp,
                                        color = MMTextSecondary
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    PriceDisplay(
                                        price = item.product.price,
                                        originalPrice = item.product.originalPrice,
                                        discountPercent = item.product.discountPercent,
                                        priceFontSize = 15.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Delivery by Tomorrow 11 AM | FREE",
                                        fontSize = 10.sp,
                                        color = MMGreenSuccess,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            HorizontalDivider(
                                color = Color(0xFFF1F5F9),
                                modifier = Modifier.padding(vertical = 10.dp)
                            )

                            // Quantity controls & Remove
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Stepper
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .border(1.dp, MMBorder, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    IconButton(
                                        onClick = { onUpdateQuantity(item, -1) },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                                    }
                                    Text(
                                        text = "${item.quantity}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                    IconButton(
                                        onClick = { onUpdateQuantity(item, 1) },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    TextButton(onClick = { onRemoveItem(item) }) {
                                        Icon(Icons.Default.DeleteOutline, contentDescription = null, tint = MMTextMuted, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Remove", color = MMTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }
                        }
                    }
                }

                // Coupons Section
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocalOffer, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Apply Coupons & Offers", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (appliedCoupon != null) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(MMGreenLight)
                                        .border(1.dp, MMGreenSuccess, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = MMGreenSuccess, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "$appliedCoupon applied (-${formatCurrency(couponDiscount)})",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MMGreenSuccess
                                        )
                                    }
                                    IconButton(onClick = onRemoveCoupon, modifier = Modifier.size(24.dp)) {
                                        Icon(Icons.Default.Close, contentDescription = "Remove coupon", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                    }
                                }
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = couponInput,
                                        onValueChange = { couponInput = it },
                                        placeholder = { Text("Enter coupon code", fontSize = 12.sp) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp),
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = MMBluePrimary,
                                            unfocusedBorderColor = MMBorder
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            onApplyCoupon(couponInput)
                                            couponInput = ""
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MMBluePrimary),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.height(48.dp)
                                    ) {
                                        Text("Apply", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(
                                        text = "Try: MMKART10",
                                        fontSize = 11.sp,
                                        color = MMBluePrimary,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MMBlueSubtle)
                                            .clickable { onApplyCoupon("MMKART10") }
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                    Text(
                                        text = "Try: SAVE2000",
                                        fontSize = 11.sp,
                                        color = MMBluePrimary,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MMBlueSubtle)
                                            .clickable { onApplyCoupon("SAVE2000") }
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Price Breakdown Card
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("PRICE DETAILS", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MMTextSecondary)
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total MRP (${cartItems.sumOf { it.quantity }} items)", fontSize = 13.sp, color = MMTextPrimary)
                                Text(formatCurrency(cartTotalMRP), fontSize = 13.sp, color = MMTextPrimary)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Discount on MRP", fontSize = 13.sp, color = MMTextPrimary)
                                Text("-${formatCurrency(cartTotalMRP - cartSellingTotal)}", fontSize = 13.sp, color = MMGreenSuccess, fontWeight = FontWeight.SemiBold)
                            }

                            if (couponDiscount > 0) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Coupon Discount", fontSize = 13.sp, color = MMTextPrimary)
                                    Text("-${formatCurrency(couponDiscount)}", fontSize = 13.sp, color = MMGreenSuccess, fontWeight = FontWeight.SemiBold)
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Delivery Charges", fontSize = 13.sp, color = MMTextPrimary)
                                Text("FREE", fontSize = 13.sp, color = MMGreenSuccess, fontWeight = FontWeight.Bold)
                            }

                            HorizontalDivider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Total Amount", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = MMTextPrimary)
                                Text(formatCurrency(finalAmount), fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = MMTextPrimary)
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MMGreenLight)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "You will save ${formatCurrency(totalSavings)} on this order",
                                    color = MMGreenSuccess,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Security Note
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = MMTextMuted, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Safe & Secure Payments. 100% Authentic Branded Electronics.",
                            fontSize = 11.sp,
                            color = MMTextMuted
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Sticky Bottom Checkout Bar
            Surface(
                color = Color.White,
                shadowElevation = 10.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Total Amount", fontSize = 11.sp, color = MMTextMuted)
                        Text(
                            text = formatCurrency(finalAmount),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MMTextPrimary
                        )
                    }

                    Button(
                        onClick = onProceedToCheckout,
                        modifier = Modifier
                            .width(180.dp)
                            .height(48.dp)
                            .testTag("cart_place_order_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9F00),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Place Order", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
