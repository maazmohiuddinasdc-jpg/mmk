package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.Order
import com.example.ui.components.MMAssuredBadge
import com.example.ui.components.formatCurrency
import com.example.ui.theme.MMAssuredBadgeYellow
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBlueSubtle
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMGreenLight
import com.example.ui.theme.MMGreenSuccess
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary

@Composable
fun OrderSuccessScreen(
    order: Order,
    onTrackOrder: (Order) -> Unit,
    onContinueShopping: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Success Badge
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(MMGreenLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = MMGreenSuccess,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Order Placed Successfully!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MMTextPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Order ID: ${order.orderId}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MMBluePrimary
                )

                Spacer(modifier = Modifier.height(16.dp))
                MMAssuredBadge()
                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(color = Color(0xFFF1F5F9))

                Spacer(modifier = Modifier.height(14.dp))

                // Delivery estimate box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF8FAFC))
                        .border(1.dp, MMBorder, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = MMGreenSuccess, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Estimated Delivery: ${order.estimatedDelivery}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MMGreenSuccess
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Deliver to: ${order.deliveryAddress.fullName}, ${order.deliveryAddress.addressLine}, ${order.deliveryAddress.city}",
                            fontSize = 11.sp,
                            color = MMTextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Payment Mode: ${order.paymentMethod} • ${formatCurrency(order.finalAmount)} Paid",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MMTextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // SuperCoins Earned Box
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFFFFBEB))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Stars, contentDescription = null, tint = MMAssuredBadgeYellow, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "You earned +50 MM SuperCoins on this order!",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB45309)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Primary Action: Track Order
                Button(
                    onClick = { onTrackOrder(order) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("success_track_order_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = MMBluePrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.LocalShipping, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Track Order Progress", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Secondary Action: Continue Shopping
                OutlinedButton(
                    onClick = onContinueShopping,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("success_continue_shopping_btn"),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Continue Shopping", color = MMBluePrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}
