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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Address
import com.example.data.model.UserProfile
import com.example.ui.components.MMAssuredBadge
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

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    addresses: List<Address>,
    onOrdersClick: () -> Unit,
    onWishlistClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showHelpDialog by remember { mutableStateOf(false) }
    var showAddressDialog by remember { mutableStateOf(false) }
    var showSavedCardsDialog by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        // App Bar
        Surface(
            color = MMBluePrimary,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                MMAssuredBadge()
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // 1. User Header & SuperCoins Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(MMBlueSubtle),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(32.dp))
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = userProfile.fullName,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MMTextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(MMGoldAccent)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text("PLUS", fontSize = 9.sp, fontWeight = FontWeight.Black, color = Color.Black)
                                    }
                                }
                                Text(userProfile.email, fontSize = 11.sp, color = MMTextSecondary)
                                Text(userProfile.phone, fontSize = 11.sp, color = MMTextMuted)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                        Spacer(modifier = Modifier.height(12.dp))

                        // SuperCoins Pill
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFFFFBEB))
                                .border(1.dp, Color(0xFFFDE68A), RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Stars, contentDescription = null, tint = MMAssuredBadgeYellow, modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text("MM SuperCoins Balance", fontSize = 11.sp, color = Color(0xFF92400E))
                                    Text("${userProfile.superCoins} Coins Available", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
                                }
                            }
                            Text("Redeem >", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
                        }
                    }
                }
            }

            // 2. Quick Navigation Shortcuts
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        ProfileOptionItem(
                            icon = Icons.Default.LocalShipping,
                            title = "My Orders",
                            subtitle = "Track active shipments & view past invoices",
                            onClick = onOrdersClick
                        )
                        HorizontalDivider(color = Color(0xFFF8FAFC), modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileOptionItem(
                            icon = Icons.Default.Favorite,
                            title = "My Wishlist",
                            subtitle = "View saved branded products & price alerts",
                            onClick = onWishlistClick
                        )
                        HorizontalDivider(color = Color(0xFFF8FAFC), modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileOptionItem(
                            icon = Icons.Default.LocationOn,
                            title = "Saved Delivery Addresses",
                            subtitle = "${addresses.size} addresses saved",
                            onClick = { showAddressDialog = true }
                        )
                        HorizontalDivider(color = Color(0xFFF8FAFC), modifier = Modifier.padding(horizontal = 16.dp))
                        ProfileOptionItem(
                            icon = Icons.Default.CreditCard,
                            title = "Saved Cards & Wallets",
                            subtitle = "HDFC Credit Card (**** 8821), Google Pay UPI",
                            onClick = { showSavedCardsDialog = true }
                        )
                    }
                }
            }

            // 3. App Settings & Support
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Notifications, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Order & Deal Notifications", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MMTextPrimary)
                                    Text("Receive updates on shipments and sales", fontSize = 11.sp, color = MMTextMuted)
                                }
                            }
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { notificationsEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = MMBluePrimary)
                            )
                        }

                        HorizontalDivider(color = Color(0xFFF8FAFC), modifier = Modifier.padding(horizontal = 16.dp))

                        ProfileOptionItem(
                            icon = Icons.Default.HeadsetMic,
                            title = "24x7 Customer Help Center",
                            subtitle = "Immediate assistance with returns, refunds & warranty",
                            onClick = { showHelpDialog = true }
                        )

                        HorizontalDivider(color = Color(0xFFF8FAFC), modifier = Modifier.padding(horizontal = 16.dp))

                        ProfileOptionItem(
                            icon = Icons.Default.Security,
                            title = "Privacy, Terms & Security",
                            subtitle = "100% Genuine electronics marketplace policy",
                            onClick = { showHelpDialog = true }
                        )
                    }
                }
            }

            // Version info
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("MMKART.COM Mobile Version 2.4.0", fontSize = 11.sp, color = MMTextMuted)
                    Text("100% Genuine Branded Electronics Marketplace", fontSize = 10.sp, color = MMBluePrimary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    // Help Center Dialog
    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.HeadsetMic, contentDescription = null, tint = MMBluePrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("MMKART Customer Care", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("• Toll-Free Support: 1800-208-9898 (24x7)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text("• Email: care@mmkart.com", fontSize = 12.sp)
                    Text("• 7-Day Replacement Policy: Fast doorstep pickup for defective or damaged electronics.", fontSize = 12.sp)
                    Text("• Brand Warranty: All products covered under official manufacturer warranty across India.", fontSize = 12.sp)
                }
            },
            confirmButton = {
                TextButton(onClick = { showHelpDialog = false }) {
                    Text("Got It", color = MMBluePrimary, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // Saved Addresses Dialog
    if (showAddressDialog) {
        AlertDialog(
            onDismissRequest = { showAddressDialog = false },
            title = { Text("Saved Delivery Addresses", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    addresses.forEach { addr ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFF8FAFC))
                                .border(1.dp, MMBorder, RoundedCornerShape(6.dp))
                                .padding(10.dp)
                        ) {
                            Text("${addr.fullName} (${addr.type})", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("${addr.addressLine}, ${addr.city} ${addr.pincode}", fontSize = 11.sp, color = MMTextSecondary)
                            Text("Phone: ${addr.phoneNumber}", fontSize = 10.sp, color = MMTextMuted)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAddressDialog = false }) {
                    Text("Close", color = MMBluePrimary, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // Saved Cards Dialog
    if (showSavedCardsDialog) {
        AlertDialog(
            onDismissRequest = { showSavedCardsDialog = false },
            title = { Text("Saved Cards & UPI", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("• HDFC Bank Regalia Credit Card (Ending **** 8821)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text("• Axis Bank MMKART Co-Branded Card (Ending **** 1042)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text("• Google Pay UPI ID: alex.j@oksbi", fontSize = 12.sp)
                }
            },
            confirmButton = {
                TextButton(onClick = { showSavedCardsDialog = false }) {
                    Text("Done", color = MMBluePrimary, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
private fun ProfileOptionItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MMTextPrimary)
                Text(text = subtitle, fontSize = 11.sp, color = MMTextMuted)
            }
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MMTextMuted, modifier = Modifier.size(20.dp))
    }
}
