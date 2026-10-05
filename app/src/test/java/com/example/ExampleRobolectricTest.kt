package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.CartItem
import com.example.data.model.FilterState
import com.example.data.model.ProductCategory
import com.example.data.model.SortOption
import com.example.data.repository.ProductRepository
import com.example.ui.viewmodel.EcommerceViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read app_name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("MMKART", appName)
    }

    @Test
    fun `verify branded electronics catalogue coverage`() {
        val brands = ProductRepository.brands
        assertTrue(brands.contains("Apple"))
        assertTrue(brands.contains("Samsung"))
        assertTrue(brands.contains("OnePlus"))
        assertTrue(brands.contains("Sony"))
        assertTrue(brands.contains("Dell"))

        val categories = ProductCategory.values().map { it.name }
        assertTrue(categories.contains("MOBILES"))
        assertTrue(categories.contains("LAPTOPS"))
        assertTrue(categories.contains("TVS"))
        assertTrue(categories.contains("HEADPHONES"))
    }

    @Test
    fun `verify coupon discount logic`() {
        val vm = EcommerceViewModel()
        vm.applyCoupon("MMKART10")
        val state = vm.uiState.value
        assertEquals("MMKART10", state.appliedCoupon)
        assertTrue(state.couponDiscount > 0.0)

        vm.removeCoupon()
        val clearedState = vm.uiState.value
        assertEquals(null, clearedState.appliedCoupon)
        assertEquals(0.0, clearedState.couponDiscount, 0.01)
    }

    @Test
    fun `verify brand and category filtering`() {
        val vm = EcommerceViewModel()
        val appleProducts = vm.filterProducts(
            products = ProductRepository.products,
            filterState = FilterState(selectedBrands = setOf("Apple")),
            sortOption = SortOption.POPULARITY,
            searchQuery = ""
        )
        assertTrue(appleProducts.isNotEmpty())
        assertTrue(appleProducts.all { it.brand == "Apple" })
    }
}
