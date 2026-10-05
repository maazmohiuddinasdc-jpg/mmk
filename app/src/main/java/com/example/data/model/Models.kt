package com.example.data.model

import androidx.annotation.DrawableRes

enum class ProductCategory(val displayName: String, val iconName: String) {
    MOBILES("Mobiles", "phone_android"),
    LAPTOPS("Laptops", "laptop_mac"),
    TABLETS("Tablets", "tablet_android"),
    SMARTWATCHES("Smartwatches", "watch"),
    TVS("TVs", "tv"),
    MONITORS("Monitors", "desktop_windows"),
    HEADPHONES("Headphones", "headphones"),
    ACCESSORIES("Accessories", "cable")
}

data class ProductReview(
    val id: String,
    val userName: String,
    val userCity: String,
    val rating: Float,
    val date: String,
    val title: String,
    val comment: String,
    val verifiedBuyer: Boolean = true,
    val helpfulCount: Int = 12
)

data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val category: ProductCategory,
    val price: Double,
    val originalPrice: Double,
    val discountPercent: Int,
    val rating: Float,
    val ratingCount: Int,
    val reviewCount: Int,
    val imageUrl: String,
    @DrawableRes val localImageRes: Int? = null,
    val shortHighlights: List<String>,
    val specifications: Map<String, List<Pair<String, String>>>,
    val colorVariants: List<String> = emptyList(),
    val storageVariants: List<String> = emptyList(),
    val stockStatus: String = "In Stock",
    val isAssured: Boolean = true,
    val bankOffers: List<String> = emptyList(),
    val reviews: List<ProductReview> = emptyList(),
    val isTrending: Boolean = false,
    val isDealOfTheDay: Boolean = false,
    val warrantyText: String = "1 Year Brand Warranty & 7 Days Replacement Policy"
)

data class CartItem(
    val product: Product,
    var quantity: Int = 1,
    val selectedColor: String = product.colorVariants.firstOrNull() ?: "Default",
    val selectedStorage: String = product.storageVariants.firstOrNull() ?: "Default"
) {
    val totalPrice: Double
        get() = product.price * quantity
}

data class Address(
    val id: String,
    val fullName: String,
    val phoneNumber: String,
    val pincode: String,
    val addressLine: String,
    val city: String,
    val state: String,
    val type: String = "Home", // Home, Work
    val isDefault: Boolean = false
)

enum class OrderStatus(val title: String, val description: String) {
    PLACED("Order Placed", "Your order has been verified and confirmed"),
    PACKED("Packed & Ready", "Packed and handed to MM Express Courier"),
    SHIPPED("Shipped", "Package has left the regional hub"),
    OUT_FOR_DELIVERY("Out for Delivery", "Courier executive is out for delivery in your area"),
    DELIVERED("Delivered", "Package safely handed to you")
}

data class TrackingStep(
    val status: OrderStatus,
    val dateText: String,
    val location: String,
    val isCompleted: Boolean,
    val isCurrent: Boolean
)

data class Order(
    val orderId: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val discountAmount: Double,
    val deliveryFee: Double,
    val finalAmount: Double,
    val orderDate: String,
    val estimatedDelivery: String,
    val deliveryAddress: Address,
    val paymentMethod: String,
    val status: OrderStatus,
    val trackingSteps: List<TrackingStep>
)

data class UserProfile(
    val fullName: String = "Alex Johnson",
    val email: String = "alex.johnson@mmkart.com",
    val phone: String = "+91 98765 43210",
    val isPrimeMember: Boolean = true,
    val superCoins: Int = 540,
    val isLoggedIn: Boolean = true
)

enum class SortOption(val title: String) {
    POPULARITY("Popularity"),
    PRICE_LOW_HIGH("Price: Low to High"),
    PRICE_HIGH_LOW("Price: High to Low"),
    CUSTOMER_RATING("Customer Rating"),
    NEWEST_FIRST("Newest First"),
    DISCOUNT("Biggest Discount")
}

data class FilterState(
    val selectedBrands: Set<String> = emptySet(),
    val selectedCategory: ProductCategory? = null,
    val minPrice: Double = 0.0,
    val maxPrice: Double = 300000.0,
    val minRating: Float = 0.0f,
    val assuredOnly: Boolean = false,
    val inStockOnly: Boolean = false
)
