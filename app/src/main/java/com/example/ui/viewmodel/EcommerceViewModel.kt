package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.Address
import com.example.data.model.CartItem
import com.example.data.model.FilterState
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.data.model.Product
import com.example.data.model.ProductCategory
import com.example.data.model.SortOption
import com.example.data.model.TrackingStep
import com.example.data.model.UserProfile
import com.example.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class BottomTab {
    HOME,
    CATEGORIES,
    CART,
    ORDERS,
    PROFILE
}

sealed class ScreenDestination {
    data object Home : ScreenDestination()
    data object Categories : ScreenDestination()
    data class ProductList(
        val category: ProductCategory? = null,
        val brand: String? = null,
        val query: String = "",
        val screenTitle: String = "All Electronics"
    ) : ScreenDestination()
    data class ProductDetail(val product: Product) : ScreenDestination()
    data object Cart : ScreenDestination()
    data object Checkout : ScreenDestination()
    data class OrderSuccess(val order: Order) : ScreenDestination()
    data class OrderTracking(val order: Order) : ScreenDestination()
    data object Orders : ScreenDestination()
    data object Profile : ScreenDestination()
    data object Wishlist : ScreenDestination()
    data object Search : ScreenDestination()
}

data class UiState(
    val currentTab: BottomTab = BottomTab.HOME,
    val screenStack: List<ScreenDestination> = listOf(ScreenDestination.Home),
    val products: List<Product> = ProductRepository.products,
    val cartItems: List<CartItem> = listOf(
        CartItem(
            product = ProductRepository.products.first { it.id == "mob-1" },
            quantity = 1,
            selectedColor = "Desert Titanium",
            selectedStorage = "256 GB"
        )
    ),
    val wishlistIds: Set<String> = setOf("lap-1", "wat-1"),
    val addresses: List<Address> = ProductRepository.initialAddresses,
    val selectedAddress: Address = ProductRepository.initialAddresses.first(),
    val orders: List<Order> = ProductRepository.initialOrders,
    val userProfile: UserProfile = UserProfile(),
    val searchQuery: String = "",
    val activeSortOption: SortOption = SortOption.POPULARITY,
    val activeFilterState: FilterState = FilterState(),
    val appliedCoupon: String? = null,
    val couponDiscount: Double = 0.0,
    val deliveryPincode: String = "560001",
    val isPincodeChecked: Boolean = true,
    val snackbarMessage: String? = null,
    val recentSearches: List<String> = listOf("iPhone 16 Pro Max", "MacBook Pro M4", "Sony WH-1000XM5", "OLED TV", "Samsung S25")
) {
    val currentScreen: ScreenDestination
        get() = screenStack.lastOrNull() ?: ScreenDestination.Home

    val cartItemCount: Int
        get() = cartItems.sumOf { it.quantity }

    val cartItemsTotal: Double
        get() = cartItems.sumOf { it.product.originalPrice * it.quantity }

    val cartSellingTotal: Double
        get() = cartItems.sumOf { it.product.price * it.quantity }

    val cartTotalDiscount: Double
        get() = (cartItemsTotal - cartSellingTotal) + couponDiscount

    val deliveryCharge: Double
        get() = if (cartSellingTotal > 500) 0.0 else 49.0

    val cartFinalAmount: Double
        get() = (cartSellingTotal - couponDiscount + deliveryCharge).coerceAtLeast(0.0)
}

class EcommerceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun selectTab(tab: BottomTab) {
        _uiState.update { state ->
            val destination = when (tab) {
                BottomTab.HOME -> ScreenDestination.Home
                BottomTab.CATEGORIES -> ScreenDestination.Categories
                BottomTab.CART -> ScreenDestination.Cart
                BottomTab.ORDERS -> ScreenDestination.Orders
                BottomTab.PROFILE -> ScreenDestination.Profile
            }
            state.copy(
                currentTab = tab,
                screenStack = listOf(destination)
            )
        }
    }

    fun navigateTo(destination: ScreenDestination) {
        _uiState.update { state ->
            state.copy(screenStack = state.screenStack + destination)
        }
    }

    fun navigateBack(): Boolean {
        var handled = false
        _uiState.update { state ->
            if (state.screenStack.size > 1) {
                handled = true
                val newStack = state.screenStack.dropLast(1)
                val newScreen = newStack.last()
                val newTab = when (newScreen) {
                    is ScreenDestination.Home -> BottomTab.HOME
                    is ScreenDestination.Categories -> BottomTab.CATEGORIES
                    is ScreenDestination.Cart -> BottomTab.CART
                    is ScreenDestination.Orders -> BottomTab.ORDERS
                    is ScreenDestination.Profile -> BottomTab.PROFILE
                    else -> state.currentTab
                }
                state.copy(screenStack = newStack, currentTab = newTab)
            } else {
                state
            }
        }
        return handled
    }

    fun openProductDetail(product: Product) {
        navigateTo(ScreenDestination.ProductDetail(product))
    }

    fun openCategory(category: ProductCategory) {
        _uiState.update {
            it.copy(
                activeFilterState = FilterState(selectedCategory = category),
                searchQuery = ""
            )
        }
        navigateTo(
            ScreenDestination.ProductList(
                category = category,
                screenTitle = "${category.displayName} Store"
            )
        )
    }

    fun openBrand(brand: String) {
        _uiState.update {
            it.copy(
                activeFilterState = FilterState(selectedBrands = setOf(brand)),
                searchQuery = ""
            )
        }
        navigateTo(
            ScreenDestination.ProductList(
                brand = brand,
                screenTitle = "Official $brand Store"
            )
        )
    }

    fun openSearch() {
        navigateTo(ScreenDestination.Search)
    }

    fun submitSearch(query: String) {
        if (query.isBlank()) return
        _uiState.update { state ->
            val updatedSearches = (listOf(query.trim()) + state.recentSearches.filterNot { it.equals(query.trim(), ignoreCase = true) }).take(8)
            state.copy(
                searchQuery = query.trim(),
                recentSearches = updatedSearches,
                activeFilterState = FilterState()
            )
        }
        navigateTo(
            ScreenDestination.ProductList(
                query = query.trim(),
                screenTitle = "Results for \"${query.trim()}\""
            )
        )
    }

    fun addToCart(product: Product, color: String? = null, storage: String? = null, quantity: Int = 1) {
        _uiState.update { state ->
            val chosenColor = color ?: product.colorVariants.firstOrNull() ?: "Standard"
            val chosenStorage = storage ?: product.storageVariants.firstOrNull() ?: "Standard"
            val existingIndex = state.cartItems.indexOfFirst {
                it.product.id == product.id && it.selectedColor == chosenColor && it.selectedStorage == chosenStorage
            }
            val newItems = if (existingIndex >= 0) {
                state.cartItems.mapIndexed { index, item ->
                    if (index == existingIndex) item.copy(quantity = item.quantity + quantity) else item
                }
            } else {
                state.cartItems + CartItem(
                    product = product,
                    quantity = quantity,
                    selectedColor = chosenColor,
                    selectedStorage = chosenStorage
                )
            }
            state.copy(
                cartItems = newItems,
                snackbarMessage = "${product.name} added to Cart!"
            )
        }
    }

    fun updateCartQuantity(cartItem: CartItem, delta: Int) {
        _uiState.update { state ->
            val updatedItems = state.cartItems.mapNotNull { item ->
                if (item.product.id == cartItem.product.id &&
                    item.selectedColor == cartItem.selectedColor &&
                    item.selectedStorage == cartItem.selectedStorage
                ) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else {
                    item
                }
            }
            state.copy(cartItems = updatedItems)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        _uiState.update { state ->
            state.copy(
                cartItems = state.cartItems.filterNot {
                    it.product.id == cartItem.product.id &&
                    it.selectedColor == cartItem.selectedColor &&
                    it.selectedStorage == cartItem.selectedStorage
                },
                snackbarMessage = "Item removed from Cart"
            )
        }
    }

    fun toggleWishlist(productId: String) {
        _uiState.update { state ->
            val isWishlisted = state.wishlistIds.contains(productId)
            val updated = if (isWishlisted) {
                state.wishlistIds - productId
            } else {
                state.wishlistIds + productId
            }
            val msg = if (isWishlisted) "Removed from Wishlist" else "Added to Wishlist ❤️"
            state.copy(wishlistIds = updated, snackbarMessage = msg)
        }
    }

    fun openWishlist() {
        navigateTo(ScreenDestination.Wishlist)
    }

    fun applyCoupon(code: String) {
        val trimmed = code.trim().uppercase()
        when (trimmed) {
            "MMKART10", "WELCOME10" -> {
                val discount = _uiState.value.cartSellingTotal * 0.10
                _uiState.update {
                    it.copy(
                        appliedCoupon = trimmed,
                        couponDiscount = discount,
                        snackbarMessage = "Coupon $trimmed applied: 10% Extra Off!"
                    )
                }
            }
            "SAVE2000" -> {
                val discount = if (_uiState.value.cartSellingTotal >= 20000) 2000.0 else 500.0
                _uiState.update {
                    it.copy(
                        appliedCoupon = trimmed,
                        couponDiscount = discount,
                        snackbarMessage = "Coupon $trimmed applied: Flat ₹${discount.toInt()} Off!"
                    )
                }
            }
            else -> {
                _uiState.update { it.copy(snackbarMessage = "Invalid coupon code. Try MMKART10 or SAVE2000") }
            }
        }
    }

    fun removeCoupon() {
        _uiState.update {
            it.copy(
                appliedCoupon = null,
                couponDiscount = 0.0,
                snackbarMessage = "Coupon removed"
            )
        }
    }

    fun checkPincode(pincode: String) {
        _uiState.update {
            it.copy(
                deliveryPincode = pincode,
                isPincodeChecked = pincode.length == 6,
                snackbarMessage = if (pincode.length == 6) "Delivery Available: Delivery by Tomorrow 11:00 AM with MM Assured" else "Please enter a valid 6-digit Pincode"
            )
        }
    }

    fun setSortOption(sortOption: SortOption) {
        _uiState.update { it.copy(activeSortOption = sortOption) }
    }

    fun updateFilters(newFilters: FilterState) {
        _uiState.update { it.copy(activeFilterState = newFilters) }
    }

    fun clearFilters() {
        _uiState.update { it.copy(activeFilterState = FilterState()) }
    }

    fun selectAddress(address: Address) {
        _uiState.update { it.copy(selectedAddress = address) }
    }

    fun addNewAddress(address: Address) {
        _uiState.update { state ->
            val updated = state.addresses + address
            state.copy(
                addresses = updated,
                selectedAddress = address,
                snackbarMessage = "New delivery address added successfully"
            )
        }
    }

    fun proceedToCheckout() {
        if (_uiState.value.cartItems.isEmpty()) {
            _uiState.update { it.copy(snackbarMessage = "Your cart is empty! Add items to checkout.") }
            return
        }
        navigateTo(ScreenDestination.Checkout)
    }

    fun placeOrder(paymentMethod: String) {
        val state = _uiState.value
        val newOrderId = "MM-${(10000..99999).random()}-2026"
        val newOrder = Order(
            orderId = newOrderId,
            items = state.cartItems,
            totalAmount = state.cartItemsTotal,
            discountAmount = state.cartTotalDiscount,
            deliveryFee = state.deliveryCharge,
            finalAmount = state.cartFinalAmount,
            orderDate = "Today, Just now",
            estimatedDelivery = "Delivery by Tomorrow 11:00 AM",
            deliveryAddress = state.selectedAddress,
            paymentMethod = paymentMethod,
            status = OrderStatus.PLACED,
            trackingSteps = listOf(
                TrackingStep(OrderStatus.PLACED, "Today, Just now", "Order confirmed & verified", isCompleted = true, isCurrent = true),
                TrackingStep(OrderStatus.PACKED, "Expected in 2 hours", "MMKART High-Security Electronics Hub", isCompleted = false, isCurrent = false),
                TrackingStep(OrderStatus.SHIPPED, "Expected tonight", "MM Express Air Logistics", isCompleted = false, isCurrent = false),
                TrackingStep(OrderStatus.OUT_FOR_DELIVERY, "Tomorrow morning", "Local delivery partner", isCompleted = false, isCurrent = false),
                TrackingStep(OrderStatus.DELIVERED, "Tomorrow by 11:00 AM", state.selectedAddress.addressLine, isCompleted = false, isCurrent = false)
            )
        )

        _uiState.update {
            it.copy(
                orders = listOf(newOrder) + it.orders,
                cartItems = emptyList(),
                appliedCoupon = null,
                couponDiscount = 0.0,
                userProfile = it.userProfile.copy(superCoins = it.userProfile.superCoins + 50),
                screenStack = listOf(ScreenDestination.Home, ScreenDestination.OrderSuccess(newOrder))
            )
        }
    }

    fun openOrderTracking(order: Order) {
        navigateTo(ScreenDestination.OrderTracking(order))
    }

    fun dismissSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun filterProducts(products: List<Product>, filterState: FilterState, sortOption: SortOption, searchQuery: String): List<Product> {
        return products.filter { product ->
            val matchQuery = searchQuery.isBlank() ||
                    product.name.contains(searchQuery, ignoreCase = true) ||
                    product.brand.contains(searchQuery, ignoreCase = true) ||
                    product.category.displayName.contains(searchQuery, ignoreCase = true)

            val matchCategory = filterState.selectedCategory == null || product.category == filterState.selectedCategory
            val matchBrand = filterState.selectedBrands.isEmpty() || filterState.selectedBrands.contains(product.brand)
            val matchPrice = product.price in filterState.minPrice..filterState.maxPrice
            val matchRating = product.rating >= filterState.minRating
            val matchAssured = !filterState.assuredOnly || product.isAssured

            matchQuery && matchCategory && matchBrand && matchPrice && matchRating && matchAssured
        }.let { list ->
            when (sortOption) {
                SortOption.POPULARITY -> list.sortedByDescending { it.ratingCount }
                SortOption.PRICE_LOW_HIGH -> list.sortedBy { it.price }
                SortOption.PRICE_HIGH_LOW -> list.sortedByDescending { it.price }
                SortOption.CUSTOMER_RATING -> list.sortedByDescending { it.rating }
                SortOption.NEWEST_FIRST -> list.sortedByDescending { it.id }
                SortOption.DISCOUNT -> list.sortedByDescending { it.discountPercent }
            }
        }
    }
}
