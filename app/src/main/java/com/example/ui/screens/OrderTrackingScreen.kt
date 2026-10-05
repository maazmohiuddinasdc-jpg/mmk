package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.ui.components.MMAssuredBadge
import com.example.ui.components.formatCurrency
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMGreenSuccess
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary

@Composable
fun OrderTrackingScreen(
    order: Order,
    onBackClick: () -> Unit,
    onContactSupport: () -> Unit,
    modifier: Modifier = Modifier
) {
    var invoiceDownloaded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        // App Bar
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
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MMTextPrimary
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Order Details & Tracking",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MMTextPrimary
                    )
                    Text(
                        text = "ID: ${order.orderId}",
                        fontSize = 11.sp,
                        color = MMBluePrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                MMAssuredBadge(small = true, modifier = Modifier.padding(end = 12.dp))
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 1. Live Status & Courier Info
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = order.status.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (order.status == OrderStatus.DELIVERED) MMGreenSuccess else MMBluePrimary
                                )
                                Text(
                                    text = order.status.description,
                                    fontSize = 12.sp,
                                    color = MMTextSecondary
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.LocalShipping,
                                contentDescription = null,
                                tint = MMBluePrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFF8FAFC))
                                .border(1.dp, MMBorder, RoundedCornerShape(6.dp))
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("Courier Partner", fontSize = 10.sp, color = MMTextMuted)
                                    Text("MM Express Air Priority", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("AWB Tracking No", fontSize = 10.sp, color = MMTextMuted)
                                    Text("MMX-${order.orderId.replace("MM-", "").replace("-2026", "")}-EXP", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MMBluePrimary)
                                }
                            }
                        }
                    }
                }
            }

            // 2. Step by Step Timeline
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Live Delivery Timeline", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)

                        Spacer(modifier = Modifier.height(16.dp))

                        for ((index, step) in order.trackingSteps.withIndex()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.width(28.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(22.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when {
                                                    step.isCompleted -> MMGreenSuccess
                                                    step.isCurrent -> MMBluePrimary
                                                    else -> Color(0xFFE2E8F0)
                                                }
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (step.isCompleted) {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(13.dp))
                                        } else {
                                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color.White))
                                        }
                                    }

                                    if (index < order.trackingSteps.size - 1) {
                                        Box(
                                            modifier = Modifier
                                                .width(2.dp)
                                                .height(36.dp)
                                                .background(if (step.isCompleted) MMGreenSuccess else Color(0xFFE2E8F0))
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(bottom = if (index < order.trackingSteps.size - 1) 16.dp else 0.dp)
                                ) {
                                    Text(
                                        text = step.status.title,
                                        fontSize = 13.sp,
                                        fontWeight = if (step.isCurrent) FontWeight.Bold else FontWeight.SemiBold,
                                        color = if (step.isCurrent) MMBluePrimary else MMTextPrimary
                                    )
                                    Text(
                                        text = "${step.dateText} • ${step.location}",
                                        fontSize = 11.sp,
                                        color = MMTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 3. Items in this order
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Items in Order (${order.items.size})", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                        Spacer(modifier = Modifier.height(10.dp))

                        for (item in order.items) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
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

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.product.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MMTextPrimary, maxLines = 1)
                                    Text("Qty: ${item.quantity} | ${item.selectedColor}", fontSize = 11.sp, color = MMTextMuted)
                                }

                                Text(formatCurrency(item.totalPrice), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                            }
                        }

                        HorizontalDivider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(vertical = 10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Paid Amount:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                            Text(formatCurrency(order.finalAmount), fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = MMTextPrimary)
                        }
                    }
                }
            }

            // 4. Delivery Address & Actions
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Delivery Address", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${order.deliveryAddress.fullName} • ${order.deliveryAddress.phoneNumber}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MMTextPrimary
                        )
                        Text(
                            text = "${order.deliveryAddress.addressLine}, ${order.deliveryAddress.city}, ${order.deliveryAddress.state} - ${order.deliveryAddress.pincode}",
                            fontSize = 11.sp,
                            color = MMTextSecondary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { invoiceDownloaded = true },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Icon(
                                    imageVector = if (invoiceDownloaded) Icons.Default.Check else Icons.Default.Download,
                                    contentDescription = null,
                                    tint = if (invoiceDownloaded) MMGreenSuccess else MMBluePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (invoiceDownloaded) "Invoice Saved" else "Tax Invoice",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (invoiceDownloaded) MMGreenSuccess else MMBluePrimary
                                )
                            }

                            Button(
                                onClick = onContactSupport,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = MMBluePrimary),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Icon(Icons.Default.HeadsetMic, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Need Help?", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
