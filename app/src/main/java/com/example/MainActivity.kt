package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ProductCategory
import com.example.ui.components.MMBottomNavigationBar
import com.example.ui.screens.CartScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.CheckoutScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderSuccessScreen
import com.example.ui.screens.OrderTrackingScreen
import com.example.ui.screens.OrdersScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ProductListScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.WishlistScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.BottomTab
import com.example.ui.viewmodel.EcommerceViewModel
import com.example.ui.viewmodel.ScreenDestination

class MainActivity : ComponentActivity() {

    private val viewModel: EcommerceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MMKartApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MMKartApp(viewModel: EcommerceViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle snackbar notifications
    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissSnackbar()
        }
    }

    // Android System Back Button Handler
    BackHandler(enabled = uiState.screenStack.size > 1) {
        viewModel.navigateBack()
    }

    // Show bottom bar only on primary top-level tabs or non-modal screens
    val isPrimaryTab = uiState.currentScreen is ScreenDestination.Home ||
            uiState.currentScreen is ScreenDestination.Categories ||
            uiState.currentScreen is ScreenDestination.Cart ||
            uiState.currentScreen is ScreenDestination.Orders ||
            uiState.currentScreen is ScreenDestination.Profile

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isPrimaryTab) {
                MMBottomNavigationBar(
                    currentTab = uiState.currentTab,
                    cartItemCount = uiState.cartItemCount,
                    onTabSelected = { tab -> viewModel.selectTab(tab) }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = uiState.currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { targetScreen ->
                when (targetScreen) {
                    is ScreenDestination.Home -> {
                        HomeScreen(
                            products = uiState.products,
                            cartCount = uiState.cartItemCount,
                            wishlistIds = uiState.wishlistIds,
                            selectedAddress = uiState.selectedAddress,
                            onProductClick = { viewModel.openProductDetail(it) },
                            onCategoryClick = { viewModel.openCategory(it) },
                            onBrandClick = { viewModel.openBrand(it) },
                            onSearchClick = { viewModel.openSearch() },
                            onCartClick = { viewModel.selectTab(BottomTab.CART) },
                            onWishlistClick = { viewModel.openWishlist() },
                            onWishlistToggle = { viewModel.toggleWishlist(it) },
                            onAddToCart = { viewModel.addToCart(it) }
                        )
                    }

                    is ScreenDestination.Categories -> {
                        CategoriesScreen(
                            onCategoryClick = { viewModel.openCategory(it) },
                            onBrandClick = { viewModel.openBrand(it) }
                        )
                    }

                    is ScreenDestination.ProductList -> {
                        val filtered = viewModel.filterProducts(
                            products = uiState.products,
                            filterState = uiState.activeFilterState,
                            sortOption = uiState.activeSortOption,
                            searchQuery = uiState.searchQuery
                        )
                        ProductListScreen(
                            title = targetScreen.screenTitle,
                            products = filtered,
                            wishlistIds = uiState.wishlistIds,
                            cartCount = uiState.cartItemCount,
                            activeSortOption = uiState.activeSortOption,
                            activeFilterState = uiState.activeFilterState,
                            onBackClick = { viewModel.navigateBack() },
                            onSearchClick = { viewModel.openSearch() },
                            onCartClick = { viewModel.selectTab(BottomTab.CART) },
                            onProductClick = { viewModel.openProductDetail(it) },
                            onWishlistToggle = { viewModel.toggleWishlist(it) },
                            onAddToCart = { viewModel.addToCart(it) },
                            onSortOptionChange = { viewModel.setSortOption(it) },
                            onFilterChange = { viewModel.updateFilters(it) },
                            onClearFilters = { viewModel.clearFilters() }
                        )
                    }

                    is ScreenDestination.ProductDetail -> {
                        ProductDetailScreen(
                            product = targetScreen.product,
                            isWishlisted = uiState.wishlistIds.contains(targetScreen.product.id),
                            cartCount = uiState.cartItemCount,
                            pincode = uiState.deliveryPincode,
                            isPincodeChecked = uiState.isPincodeChecked,
                            onBackClick = { viewModel.navigateBack() },
                            onCartClick = { viewModel.selectTab(BottomTab.CART) },
                            onWishlistToggle = { viewModel.toggleWishlist(targetScreen.product.id) },
                            onCheckPincode = { viewModel.checkPincode(it) },
                            onAddToCart = { prod, col, st -> viewModel.addToCart(prod, col, st) },
                            onBuyNow = { prod, col, st ->
                                viewModel.addToCart(prod, col, st)
                                viewModel.proceedToCheckout()
                            }
                        )
                    }

                    is ScreenDestination.Cart -> {
                        CartScreen(
                            cartItems = uiState.cartItems,
                            selectedAddress = uiState.selectedAddress,
                            appliedCoupon = uiState.appliedCoupon,
                            couponDiscount = uiState.couponDiscount,
                            cartTotalMRP = uiState.cartItemsTotal,
                            cartSellingTotal = uiState.cartSellingTotal,
                            deliveryFee = uiState.deliveryCharge,
                            finalAmount = uiState.cartFinalAmount,
                            totalSavings = uiState.cartTotalDiscount,
                            onUpdateQuantity = { item, delta -> viewModel.updateCartQuantity(item, delta) },
                            onRemoveItem = { item -> viewModel.removeFromCart(item) },
                            onApplyCoupon = { code -> viewModel.applyCoupon(code) },
                            onRemoveCoupon = { viewModel.removeCoupon() },
                            onProceedToCheckout = { viewModel.proceedToCheckout() },
                            onExploreShop = { viewModel.selectTab(BottomTab.HOME) }
                        )
                    }

                    is ScreenDestination.Checkout -> {
                        CheckoutScreen(
                            addresses = uiState.addresses,
                            selectedAddress = uiState.selectedAddress,
                            cartItems = uiState.cartItems,
                            finalAmount = uiState.cartFinalAmount,
                            onBackClick = { viewModel.navigateBack() },
                            onSelectAddress = { viewModel.selectAddress(it) },
                            onAddNewAddress = { viewModel.addNewAddress(it) },
                            onPlaceOrder = { method -> viewModel.placeOrder(method) }
                        )
                    }

                    is ScreenDestination.OrderSuccess -> {
                        OrderSuccessScreen(
                            order = targetScreen.order,
                            onTrackOrder = { viewModel.openOrderTracking(it) },
                            onContinueShopping = { viewModel.selectTab(BottomTab.HOME) }
                        )
                    }

                    is ScreenDestination.OrderTracking -> {
                        OrderTrackingScreen(
                            order = targetScreen.order,
                            onBackClick = { viewModel.navigateBack() },
                            onContactSupport = { viewModel.selectTab(BottomTab.PROFILE) }
                        )
                    }

                    is ScreenDestination.Orders -> {
                        OrdersScreen(
                            orders = uiState.orders,
                            onOrderClick = { viewModel.openOrderTracking(it) }
                        )
                    }

                    is ScreenDestination.Profile -> {
                        ProfileScreen(
                            userProfile = uiState.userProfile,
                            addresses = uiState.addresses,
                            onOrdersClick = { viewModel.selectTab(BottomTab.ORDERS) },
                            onWishlistClick = { viewModel.openWishlist() }
                        )
                    }

                    is ScreenDestination.Wishlist -> {
                        val wishlisted = uiState.products.filter { uiState.wishlistIds.contains(it.id) }
                        WishlistScreen(
                            wishlistedProducts = wishlisted,
                            cartCount = uiState.cartItemCount,
                            onBackClick = { viewModel.navigateBack() },
                            onCartClick = { viewModel.selectTab(BottomTab.CART) },
                            onProductClick = { viewModel.openProductDetail(it) },
                            onWishlistToggle = { viewModel.toggleWishlist(it) },
                            onAddToCart = { viewModel.addToCart(it) },
                            onExploreShop = { viewModel.selectTab(BottomTab.HOME) }
                        )
                    }

                    is ScreenDestination.Search -> {
                        SearchScreen(
                            recentSearches = uiState.recentSearches,
                            allProducts = uiState.products,
                            onBackClick = { viewModel.navigateBack() },
                            onSearchSubmit = { viewModel.submitSearch(it) }
                        )
                    }
                }
            }
        }
    }
}
