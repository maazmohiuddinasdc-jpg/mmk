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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.data.model.CartItem
import com.example.ui.components.formatCurrency
import com.example.ui.theme.MMBlueDark
import com.example.ui.theme.MMBluePrimary
import com.example.ui.theme.MMBlueSubtle
import com.example.ui.theme.MMBorder
import com.example.ui.theme.MMGreenLight
import com.example.ui.theme.MMGreenSuccess
import com.example.ui.theme.MMTextMuted
import com.example.ui.theme.MMTextPrimary
import com.example.ui.theme.MMTextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CheckoutScreen(
    addresses: List<Address>,
    selectedAddress: Address,
    cartItems: List<CartItem>,
    finalAmount: Double,
    onBackClick: () -> Unit,
    onSelectAddress: (Address) -> Unit,
    onAddNewAddress: (Address) -> Unit,
    onPlaceOrder: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPaymentMethod by remember { mutableStateOf("UPI (Google Pay)") }
    var upiIdInput by remember { mutableStateOf("") }
    var cardNumberInput by remember { mutableStateOf("") }
    var cardExpiryInput by remember { mutableStateOf("") }
    var cardCvvInput by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    var showAddAddressDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

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

                Text(
                    text = "Order Checkout & Payment",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MMTextPrimary,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = MMGreenSuccess, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("100% Secure", fontSize = 11.sp, color = MMGreenSuccess, fontWeight = FontWeight.Bold)
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Delivery Address Card
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("1. Delivery Address", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                            }
                            TextButton(onClick = { showAddAddressDialog = true }) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("Add New", fontSize = 12.sp, color = MMBluePrimary, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        for (addr in addresses) {
                            val isSelected = addr.id == selectedAddress.id
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) MMBlueSubtle else Color(0xFFF8FAFC))
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) MMBluePrimary else MMBorder,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .clickable { onSelectAddress(addr) }
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { onSelectAddress(addr) },
                                    colors = RadioButtonDefaults.colors(selectedColor = MMBluePrimary)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(addr.fullName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFFE2E8F0))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(addr.type.uppercase(), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MMTextSecondary)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${addr.addressLine}, ${addr.city}, ${addr.state} - ${addr.pincode}",
                                        fontSize = 11.sp,
                                        color = MMTextSecondary,
                                        lineHeight = 15.sp
                                    )
                                    Text(
                                        text = "Phone: ${addr.phoneNumber}",
                                        fontSize = 11.sp,
                                        color = MMTextMuted
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            // 2. Order Items Review Summary
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("2. Order Summary (${cartItems.size} items)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        for (item in cartItems) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.product.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MMTextPrimary, maxLines = 1)
                                    Text("Qty: ${item.quantity} | ${item.selectedColor}", fontSize = 11.sp, color = MMTextMuted)
                                }
                                Text(formatCurrency(item.totalPrice), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                            }
                        }

                        HorizontalDivider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(vertical = 8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Delivery Speed:", fontSize = 12.sp, color = MMTextSecondary)
                            Text("FREE Standard Delivery (Tomorrow 11 AM)", fontSize = 12.sp, color = MMGreenSuccess, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // 3. Payment Methods Selector
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CreditCard, contentDescription = null, tint = MMBluePrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("3. Choose Payment Method", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val paymentOptions = listOf(
                            PaymentOption("UPI (Google Pay)", "Pay instantly via Google Pay UPI", Icons.Default.PhoneAndroid),
                            PaymentOption("UPI (PhonePe)", "Fast UPI checkout with PhonePe", Icons.Default.PhoneAndroid),
                            PaymentOption("Credit / Debit Card", "Visa, Mastercard, RuPay & American Express", Icons.Default.CreditCard),
                            PaymentOption("Net Banking", "All Indian banks supported (HDFC, SBI, ICICI)", Icons.Default.AccountBalance),
                            PaymentOption("EMI / Pay Later", "Easy installments starting ₹2,499/month", Icons.Default.Schedule),
                            PaymentOption("Cash on Delivery", "Pay in cash or UPI at delivery", Icons.Default.CurrencyRupee)
                        )

                        for (opt in paymentOptions) {
                            val isSelected = selectedPaymentMethod == opt.title
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) MMBlueSubtle else Color(0xFFF8FAFC))
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) MMBluePrimary else MMBorder,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedPaymentMethod = opt.title }
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedPaymentMethod = opt.title },
                                        colors = RadioButtonDefaults.colors(selectedColor = MMBluePrimary)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(opt.icon, contentDescription = null, tint = if (isSelected) MMBluePrimary else MMTextSecondary, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(opt.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MMTextPrimary)
                                        Text(opt.desc, fontSize = 11.sp, color = MMTextSecondary)
                                    }
                                }

                                // Expandable sub-fields for Card or Custom UPI
                                if (isSelected && opt.title == "Credit / Debit Card") {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedTextField(
                                        value = cardNumberInput,
                                        onValueChange = { cardNumberInput = it },
                                        placeholder = { Text("Card Number (e.g. 4532 •••• •••• 8821)", fontSize = 12.sp) },
                                        modifier = Modifier.fillMaxWidth().height(50.dp),
                                        singleLine = true,
                                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MMBluePrimary)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedTextField(
                                            value = cardExpiryInput,
                                            onValueChange = { cardExpiryInput = it },
                                            placeholder = { Text("MM/YY", fontSize = 12.sp) },
                                            modifier = Modifier.weight(1f).height(50.dp),
                                            singleLine = true,
                                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MMBluePrimary)
                                        )
                                        OutlinedTextField(
                                            value = cardCvvInput,
                                            onValueChange = { cardCvvInput = it },
                                            placeholder = { Text("CVV", fontSize = 12.sp) },
                                            modifier = Modifier.weight(1f).height(50.dp),
                                            singleLine = true,
                                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MMBluePrimary)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Trust note
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Security, contentDescription = null, tint = MMGreenSuccess, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Encrypted with 256-bit bank-grade SSL security",
                        fontSize = 11.sp,
                        color = MMTextMuted
                    )
                }
            }
        }

        // Sticky Bottom Complete Payment Bar
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
                    Text("Amount Payable", fontSize = 11.sp, color = MMTextMuted)
                    Text(
                        text = formatCurrency(finalAmount),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MMTextPrimary
                    )
                }

                Button(
                    onClick = {
                        isProcessing = true
                        scope.launch {
                            delay(1200) // realistic smooth verification delay
                            isProcessing = false
                            onPlaceOrder(selectedPaymentMethod)
                        }
                    },
                    enabled = !isProcessing,
                    modifier = Modifier
                        .width(200.dp)
                        .height(48.dp)
                        .testTag("checkout_pay_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9F00),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Verifying...", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    } else {
                        Text("Pay & Confirm Order", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // Add New Address Dialog
    if (showAddAddressDialog) {
        var newName by remember { mutableStateOf("") }
        var newPhone by remember { mutableStateOf("") }
        var newPincode by remember { mutableStateOf("") }
        var newAddressLine by remember { mutableStateOf("") }
        var newCity by remember { mutableStateOf("") }
        var newState by remember { mutableStateOf("") }
        var newType by remember { mutableStateOf("Home") }

        AlertDialog(
            onDismissRequest = { showAddAddressDialog = false },
            title = { Text("Add Delivery Address", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Full Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newPhone,
                        onValueChange = { newPhone = it },
                        label = { Text("Phone Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newPincode,
                        onValueChange = { newPincode = it },
                        label = { Text("6-digit Pincode") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newAddressLine,
                        onValueChange = { newAddressLine = it },
                        label = { Text("Flat, House No, Street Area") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newCity,
                            onValueChange = { newCity = it },
                            label = { Text("City") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = newState,
                            onValueChange = { newState = it },
                            label = { Text("State") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newName.isNotBlank() && newAddressLine.isNotBlank()) {
                            val newAddr = Address(
                                id = "addr-${System.currentTimeMillis()}",
                                fullName = newName,
                                phoneNumber = newPhone.ifBlank { "+91 98765 43210" },
                                pincode = newPincode.ifBlank { "560001" },
                                addressLine = newAddressLine,
                                city = newCity.ifBlank { "Bengaluru" },
                                state = newState.ifBlank { "Karnataka" },
                                type = newType
                            )
                            onAddNewAddress(newAddr)
                            showAddAddressDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MMBluePrimary)
                ) {
                    Text("Save Address")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddAddressDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

private data class PaymentOption(
    val title: String,
    val desc: String,
    val icon: ImageVector
)
