package com.example.data.repository

import com.example.R
import com.example.data.model.Address
import com.example.data.model.CartItem
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.data.model.Product
import com.example.data.model.ProductCategory
import com.example.data.model.ProductReview
import com.example.data.model.TrackingStep

object ProductRepository {

    val brands = listOf("Apple", "Samsung", "OnePlus", "Xiaomi", "HP", "Dell", "Lenovo", "Sony", "LG")

    val sampleReviews = listOf(
        ProductReview(
            id = "rev-1",
            userName = "Vikram Sharma",
            userCity = "Bengaluru",
            rating = 5.0f,
            date = "3 days ago",
            title = "Absolute beast of a machine!",
            comment = "Delivered within 24 hours via MM Assured. Packaging was pristine. Display and battery life exceed expectations.",
            verifiedBuyer = true,
            helpfulCount = 48
        ),
        ProductReview(
            id = "rev-2",
            userName = "Sneha Patel",
            userCity = "Mumbai",
            rating = 4.5f,
            date = "1 week ago",
            title = "Superb premium build & blazing performance",
            comment = "Build quality is top tier. Switched from my 3-year-old device and the difference in camera and display refresh rate is phenomenal.",
            verifiedBuyer = true,
            helpfulCount = 29
        ),
        ProductReview(
            id = "rev-3",
            userName = "Rohit Verma",
            userCity = "New Delhi",
            rating = 5.0f,
            date = "2 weeks ago",
            title = "100% Genuine product with original manufacturer warranty",
            comment = "Registered on brand website immediately without any hassle. MMKART customer support was very helpful with invoice generation.",
            verifiedBuyer = true,
            helpfulCount = 17
        )
    )

    val products: List<Product> = listOf(
        // === MOBILES ===
        Product(
            id = "mob-1",
            name = "Apple iPhone 16 Pro Max",
            brand = "Apple",
            category = ProductCategory.MOBILES,
            price = 144900.0,
            originalPrice = 159900.0,
            discountPercent = 9,
            rating = 4.8f,
            ratingCount = 28410,
            reviewCount = 3890,
            imageUrl = "https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=600&auto=format&fit=crop&q=80",
            localImageRes = R.drawable.banner_flagship,
            shortHighlights = listOf(
                "256 GB ROM | Grade 5 Titanium Finish",
                "17.53 cm (6.9 inch) Super Retina XDR ProMotion 120Hz",
                "48MP Fusion + 48MP Ultra Wide + 12MP 5x Telephoto",
                "A18 Pro Bionic Chip with 6-core GPU & Apple Intelligence",
                "All-day Battery with MagSafe Wireless Charging"
            ),
            specifications = mapOf(
                "General" to listOf("Model" to "iPhone 16 Pro Max", "SIM Type" to "Dual SIM (Nano + eSIM)", "OS" to "iOS 18"),
                "Display" to listOf("Size" to "6.9 inch OLED", "Resolution" to "2868 x 1320 Pixels", "Brightness" to "2000 nits Peak"),
                "Performance" to listOf("Processor" to "Apple A18 Pro", "Neural Engine" to "16-core Next-Gen", "GPU" to "6-core Pro Class"),
                "Camera" to listOf("Primary" to "48MP Fusion Camera", "Telephoto" to "12MP 5x Optical Zoom", "Video" to "4K Dolby Vision 120fps"),
                "Warranty" to listOf("Coverage" to "1 Year Apple India Limited Warranty", "Service" to "Available across authorized Apple Care")
            ),
            colorVariants = listOf("Desert Titanium", "Natural Titanium", "White Titanium", "Black Titanium"),
            storageVariants = listOf("256 GB", "512 GB", "1 TB"),
            stockStatus = "In Stock (Fast Delivery Tomorrow)",
            isAssured = true,
            isTrending = true,
            isDealOfTheDay = true,
            bankOffers = listOf(
                "Flat ₹5,000 Instant Discount on HDFC & ICICI Credit Cards",
                "5% Unlimited Cashback on MMKART Axis Bank Credit Card",
                "No Cost EMI starting from ₹12,075/month for up to 12 months",
                "Special Price: Get Extra ₹15,000 off on Exchange of old device"
            ),
            reviews = sampleReviews
        ),
        Product(
            id = "mob-2",
            name = "Samsung Galaxy S25 Ultra 5G",
            brand = "Samsung",
            category = ProductCategory.MOBILES,
            price = 129999.0,
            originalPrice = 141999.0,
            discountPercent = 8,
            rating = 4.7f,
            ratingCount = 19250,
            reviewCount = 2140,
            imageUrl = "https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "12 GB RAM | 256 GB ROM | Built-in S-Pen",
                "17.27 cm (6.8 inch) Dynamic AMOLED 2X Flat Display",
                "200MP + 50MP + 50MP + 12MP Quad Telephoto Camera",
                "Snapdragon 8 Elite for Galaxy (3nm)",
                "5000 mAh Battery with 45W Super Fast Charging 2.0"
            ),
            specifications = mapOf(
                "General" to listOf("Model" to "Galaxy S25 Ultra", "OS" to "Android 15, One UI 7", "Stylus" to "Embedded Bluetooth S-Pen"),
                "Display" to listOf("Panel" to "Dynamic AMOLED 2X", "Refresh Rate" to "1-120Hz Adaptive", "Glass" to "Corning Gorilla Armor"),
                "Camera" to listOf("Main" to "200MP OIS Wide", "Zoom" to "50MP 5x Periscope & 10MP 3x", "AI Zoom" to "Up to 100x Space Zoom"),
                "Battery" to listOf("Capacity" to "5000 mAh", "Fast Charge" to "45W Wired, 15W Wireless")
            ),
            colorVariants = listOf("Titanium Gray", "Titanium Black", "Titanium Violet", "Titanium Yellow"),
            storageVariants = listOf("256 GB / 12GB RAM", "512 GB / 12GB RAM", "1 TB / 16GB RAM"),
            stockStatus = "In Stock",
            isAssured = true,
            isTrending = true,
            bankOffers = listOf(
                "₹7,000 Instant Discount with Samsung Shop Card or SBI Credit Card",
                "Free Galaxy Watch6 on select exchange combos",
                "No Cost EMI starting from ₹10,833/month"
            ),
            reviews = sampleReviews
        ),
        Product(
            id = "mob-3",
            name = "OnePlus 13 5G",
            brand = "OnePlus",
            category = ProductCategory.MOBILES,
            price = 64999.0,
            originalPrice = 69999.0,
            discountPercent = 7,
            rating = 4.6f,
            ratingCount = 14320,
            reviewCount = 1860,
            imageUrl = "https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "16 GB LPDDR5X RAM | 512 GB UFS 4.0 Storage",
                "6.82 inch 2K 120Hz ProXDR Display with Dolby Vision",
                "50MP Sony LYT-808 Hasselblad Camera System",
                "Snapdragon 8 Elite Processor with Cryo-Velocity VC Cooling",
                "6000 mAh Glacier Battery with 100W SUPERVOOC Charging"
            ),
            specifications = mapOf(
                "General" to listOf("Model" to "OnePlus 13", "OS" to "OxygenOS 15 based on Android 15"),
                "Display" to listOf("Resolution" to "3168 x 1440 Pixels", "Brightness" to "4500 nits Peak"),
                "Charging" to listOf("Wired" to "100W (0-100% in 28 mins)", "Wireless" to "50W AIRVOOC")
            ),
            colorVariants = listOf("Midnight Ocean", "Black Eclipse", "Arctic Dawn"),
            storageVariants = listOf("256 GB / 12GB RAM", "512 GB / 16GB RAM"),
            stockStatus = "In Stock",
            isAssured = true,
            isTrending = true,
            bankOffers = listOf("₹3,000 Instant Bank Discount on ICICI Cards", "6 Months No Cost EMI"),
            reviews = sampleReviews
        ),
        Product(
            id = "mob-4",
            name = "Xiaomi 15 Ultra 5G",
            brand = "Xiaomi",
            category = ProductCategory.MOBILES,
            price = 79999.0,
            originalPrice = 89999.0,
            discountPercent = 11,
            rating = 4.6f,
            ratingCount = 8900,
            reviewCount = 980,
            imageUrl = "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "16 GB RAM | 512 GB ROM | Ceramic Body",
                "Leica Summilux Optical Quad Lens with 1-inch Sony Sensor",
                "6.73 inch 120Hz WQHD+ AMOLED Display",
                "Snapdragon 8 Elite High Performance Flagship",
                "90W HyperCharge + 80W Wireless Charging"
            ),
            specifications = mapOf(
                "Optics" to listOf("Leica Tuned" to "Leica Authentic & Vibrant modes", "Main Sensor" to "1-inch 50MP Sony LYT-900"),
                "Performance" to listOf("SoC" to "Snapdragon 8 Elite", "Battery" to "5300 mAh Silicon-Carbon")
            ),
            colorVariants = listOf("Ceramic Black", "Ceramic White"),
            storageVariants = listOf("512 GB / 16GB RAM"),
            stockStatus = "Only 4 left in stock!",
            isAssured = true,
            bankOffers = listOf("Flat ₹5,000 off with Xiaomi Exchange", "No Cost EMI available"),
            reviews = sampleReviews
        ),

        // === LAPTOPS ===
        Product(
            id = "lap-1",
            name = "Apple MacBook Pro 16\" (M4 Pro)",
            brand = "Apple",
            category = ProductCategory.LAPTOPS,
            price = 249900.0,
            originalPrice = 269900.0,
            discountPercent = 7,
            rating = 4.9f,
            ratingCount = 6210,
            reviewCount = 840,
            imageUrl = "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=600&auto=format&fit=crop&q=80",
            localImageRes = R.drawable.banner_laptops,
            shortHighlights = listOf(
                "Apple M4 Pro chip (14-core CPU, 20-core GPU)",
                "24 GB Unified Memory | 512 GB SSD Storage",
                "41.05 cm (16.2 inch) Liquid Retina XDR Mini-LED Display",
                "Up to 24 Hours Battery Life | MagSafe 3 Charging",
                "Three Thunderbolt 5 Ports, HDMI, SDXC card slot"
            ),
            specifications = mapOf(
                "Processor" to listOf("Chip" to "Apple M4 Pro", "CPU Cores" to "14 (10 Performance + 4 Efficiency)", "GPU" to "20-Core with Hardware Ray Tracing"),
                "Display" to listOf("Size" to "16.2 inch", "Resolution" to "3456 x 2234", "Peak Brightness" to "1600 nits HDR"),
                "Memory & Storage" to listOf("RAM" to "24 GB Unified Memory (Up to 128GB)", "Storage" to "512 GB NVMe SSD")
            ),
            colorVariants = listOf("Space Black", "Silver"),
            storageVariants = listOf("512 GB SSD", "1 TB SSD", "2 TB SSD"),
            stockStatus = "In Stock",
            isAssured = true,
            isDealOfTheDay = true,
            bankOffers = listOf("₹10,000 Instant Discount on HDFC Credit Card", "No Cost EMI up to 12 months"),
            reviews = sampleReviews
        ),
        Product(
            id = "lap-2",
            name = "Dell XPS 16 OLED Laptop",
            brand = "Dell",
            category = ProductCategory.LAPTOPS,
            price = 214990.0,
            originalPrice = 239990.0,
            discountPercent = 10,
            rating = 4.7f,
            ratingCount = 4120,
            reviewCount = 530,
            imageUrl = "https://images.unsplash.com/photo-1588872657578-7efd1f1555ed?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Intel Core Ultra 7 155H (16 Cores, NPU AI Engine)",
                "32 GB LPDDR5X RAM | 1 TB PCIe 4.0 NVMe SSD",
                "NVIDIA GeForce RTX 4060 8GB GDDR6 Graphics",
                "40.64 cm (16 inch) 4K+ OLED InfinityEdge Touchscreen",
                "CNC Machined Aluminum & Gorilla Glass 3 Palmrest"
            ),
            specifications = mapOf(
                "Hardware" to listOf("CPU" to "Intel Core Ultra 7 155H", "GPU" to "NVIDIA RTX 4060 8GB", "RAM" to "32 GB 7467MHz"),
                "Display" to listOf("Resolution" to "3840 x 2400 UHD+", "Color Gamut" to "100% DCI-P3", "Touch" to "Yes, 10-point Multi-touch")
            ),
            colorVariants = listOf("Platinum Silver", "Graphite"),
            storageVariants = listOf("1 TB SSD / 32GB RAM", "2 TB SSD / 64GB RAM"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹7,500 Instant Discount on Axis Bank Cards"),
            reviews = sampleReviews
        ),
        Product(
            id = "lap-3",
            name = "HP Spectre x360 2-in-1 OLED",
            brand = "HP",
            category = ProductCategory.LAPTOPS,
            price = 159990.0,
            originalPrice = 179990.0,
            discountPercent = 11,
            rating = 4.6f,
            ratingCount = 3890,
            reviewCount = 420,
            imageUrl = "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Intel Core Ultra 7 155H with Intel Arc Graphics",
                "16 GB LPDDR5x RAM | 1 TB Gen4 NVMe SSD",
                "35.56 cm (14 inch) 2.8K 120Hz OLED Touch Display",
                "360-degree convertible with HP Rechargeable MPP2.0 Tilt Pen",
                "9MP IR AI Camera with Privacy Shutter & Poly Studio Audio"
            ),
            specifications = mapOf(
                "Design" to listOf("Form Factor" to "2-in-1 Convertible", "Weight" to "1.44 kg", "Pen" to "HP Tilt Stylus included"),
                "Screen" to listOf("Type" to "2.8K OLED (2880 x 1800)", "Refresh" to "120Hz VRR", "IMAX Enhanced" to "Yes")
            ),
            colorVariants = listOf("Nightfall Black", "Slate Blue"),
            storageVariants = listOf("1 TB SSD / 16GB RAM"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("Flat ₹6,000 off on ICICI Bank Cards", "Complimentary Laptop Backpack & Mouse"),
            reviews = sampleReviews
        ),
        Product(
            id = "lap-4",
            name = "Lenovo Legion Pro 7i Gen 9",
            brand = "Lenovo",
            category = ProductCategory.LAPTOPS,
            price = 234990.0,
            originalPrice = 259990.0,
            discountPercent = 9,
            rating = 4.8f,
            ratingCount = 5120,
            reviewCount = 680,
            imageUrl = "https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Intel Core i9-14900HX (24 Cores, 32 Threads, up to 5.8 GHz)",
                "32 GB DDR5 5600MHz RAM | 1 TB SSD",
                "NVIDIA GeForce RTX 4080 12GB GDDR6 (175W TGP)",
                "40.64 cm (16 inch) WQXGA 240Hz 500 nits PureSight Gaming Display",
                "Legion Coldfront 5.0 Vapor Chamber Cooling with AI Engine+"
            ),
            specifications = mapOf(
                "Gaming Specs" to listOf("Graphics" to "NVIDIA RTX 4080 12GB 175W", "CPU" to "Intel Core i9-14900HX", "Display" to "16\" 240Hz G-SYNC 100% sRGB"),
                "Audio & Key" to listOf("Keyboard" to "Legion TrueStrike Per-Key RGB", "Speakers" to "Nahimic by SteelSeries")
            ),
            colorVariants = listOf("Eclipse Black"),
            storageVariants = listOf("1 TB SSD / 32GB RAM", "2 TB SSD / 32GB RAM"),
            stockStatus = "In Stock",
            isAssured = true,
            isTrending = true,
            bankOffers = listOf("₹8,000 Instant Discount on SBI Cards", "Free Legion Gaming Headset"),
            reviews = sampleReviews
        ),

        // === TABLETS ===
        Product(
            id = "tab-1",
            name = "Apple iPad Pro 13\" (M4 OLED)",
            brand = "Apple",
            category = ProductCategory.TABLETS,
            price = 129900.0,
            originalPrice = 139900.0,
            discountPercent = 7,
            rating = 4.9f,
            ratingCount = 7450,
            reviewCount = 920,
            imageUrl = "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Ultra Retina XDR Tandem OLED Display (1000 nits full screen)",
                "Apple M4 Chip with 9-core CPU & 10-core GPU",
                "256 GB Storage | Supports Apple Pencil Pro & Magic Keyboard",
                "Incredibly thin 5.1 mm chassis - Thinnest Apple product ever",
                "12MP Wide camera with LiDAR Scanner & Landscape Front Camera"
            ),
            specifications = mapOf(
                "Screen" to listOf("Panel" to "Tandem OLED ProMotion", "Resolution" to "2752 x 2064", "Pencil" to "Apple Pencil Pro with Haptic feedback"),
                "Performance" to listOf("Chip" to "Apple M4", "RAM" to "8GB Unified Memory", "OS" to "iPadOS 18")
            ),
            colorVariants = listOf("Space Black", "Silver"),
            storageVariants = listOf("256 GB", "512 GB", "1 TB"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹4,000 Instant Discount with HDFC Cards"),
            reviews = sampleReviews
        ),
        Product(
            id = "tab-2",
            name = "Samsung Galaxy Tab S10 Ultra",
            brand = "Samsung",
            category = ProductCategory.TABLETS,
            price = 108999.0,
            originalPrice = 119999.0,
            discountPercent = 9,
            rating = 4.8f,
            ratingCount = 4890,
            reviewCount = 610,
            imageUrl = "https://images.unsplash.com/photo-1561154464-82e9adf32764?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "14.6 inch Dynamic AMOLED 2X Anti-Reflection Display",
                "12 GB RAM | 256 GB ROM | S-Pen Included in Box",
                "MediaTek Dimensity 9300+ Flagship 4nm Processor",
                "IP68 Water & Dust Resistance with Armor Aluminum Frame",
                "11,200 mAh Giant Battery with 45W Fast Charging"
            ),
            specifications = mapOf(
                "Display" to listOf("Size" to "14.6 inch Huge Canvas", "Rate" to "120Hz Smooth Display", "Glare" to "Anti-Reflective Coating"),
                "Productivity" to listOf("Dex Mode" to "Wireless & Wired PC experience", "Pen" to "Ultra-low latency S Pen included")
            ),
            colorVariants = listOf("Moonstone Gray", "Platinum Silver"),
            storageVariants = listOf("256 GB / 12GB RAM", "512 GB / 12GB RAM"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹6,000 Instant Cashback on Samsung Axis Card"),
            reviews = sampleReviews
        ),

        // === SMARTWATCHES ===
        Product(
            id = "wat-1",
            name = "Apple Watch Ultra 2 (Titanium)",
            brand = "Apple",
            category = ProductCategory.SMARTWATCHES,
            price = 89900.0,
            originalPrice = 94900.0,
            discountPercent = 5,
            rating = 4.9f,
            ratingCount = 8900,
            reviewCount = 1120,
            imageUrl = "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "49 mm Aerospace-grade Titanium Case with Sapphire Crystal",
                "3000 nits Peak Brightness Retina Display",
                "Up to 36 Hours Normal Use & 72 Hours in Low Power Mode",
                "Precision Dual-frequency GPS, ECG & Oceanic+ Dive Computer",
                "Customizable Action Button & 86-decibel Emergency Siren"
            ),
            specifications = mapOf(
                "Build" to listOf("Case" to "49mm Natural Titanium", "Water" to "100m Water Resistant, 40m Dive Certified"),
                "Sensors" to listOf("Health" to "ECG, Blood Oxygen, Heart Rate, Temp Sensing", "GPS" to "L1 and L5 Precision Dual GPS")
            ),
            colorVariants = listOf("Natural Titanium", "Black Titanium"),
            storageVariants = listOf("49mm GPS + Cellular"),
            stockStatus = "In Stock",
            isAssured = true,
            isTrending = true,
            bankOffers = listOf("₹3,500 Instant Discount on Credit Cards"),
            reviews = sampleReviews
        ),
        Product(
            id = "wat-2",
            name = "Samsung Galaxy Watch Ultra",
            brand = "Samsung",
            category = ProductCategory.SMARTWATCHES,
            price = 59999.0,
            originalPrice = 64999.0,
            discountPercent = 7,
            rating = 4.7f,
            ratingCount = 5410,
            reviewCount = 680,
            imageUrl = "https://images.unsplash.com/photo-1508685096489-7aacd43bd3b1?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "47 mm Cushion Design Titanium Case with Quick Button",
                "Dual-Frequency GPS (L1+L5) with Track Back & Emergency Siren",
                "10ATM + IP68 Water Resistance (Ocean & Altitude Ready)",
                "Galaxy AI Energy Score & Sleep Apnea Detection",
                "Up to 100 Hours Battery Life in Power Saving Mode"
            ),
            specifications = mapOf(
                "Design" to listOf("Size" to "47mm Grade 4 Titanium", "Display" to "1.5 inch Super AMOLED 3000 nits"),
                "Features" to listOf("Battery" to "590 mAh", "OS" to "Wear OS Powered by Samsung")
            ),
            colorVariants = listOf("Titanium Silver", "Titanium Gray", "Titanium White"),
            storageVariants = listOf("47mm LTE + Bluetooth"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹4,000 Instant Cashback on HDFC/ICICI Cards"),
            reviews = sampleReviews
        ),

        // === TVS ===
        Product(
            id = "tv-1",
            name = "Sony BRAVIA 8 OLED 65\" 4K TV",
            brand = "Sony",
            category = ProductCategory.TVS,
            price = 219990.0,
            originalPrice = 249990.0,
            discountPercent = 12,
            rating = 4.8f,
            ratingCount = 3450,
            reviewCount = 490,
            imageUrl = "https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "65 inch (164 cm) 4K Ultra HD Self-illuminating OLED",
                "XR Processor with XR OLED Contrast Pro & Triluminos Max",
                "Acoustic Surface Audio+ (Screen vibrates to emit sound)",
                "Google TV with Hands-free Voice Search & Apple AirPlay 2",
                "HDMI 2.1 4K 120Hz, VRR, ALLM & Auto HDR Tone Mapping for PS5"
            ),
            specifications = mapOf(
                "Display" to listOf("Screen Type" to "Pure OLED Panel", "Refresh Rate" to "120Hz Native", "HDR" to "Dolby Vision, HDR10, HLG"),
                "Audio" to listOf("Output" to "50W Acoustic Surface Audio+", "Codec" to "Dolby Atmos, DTS:X")
            ),
            colorVariants = listOf("Dark Silver Metal Slim"),
            storageVariants = listOf("65 inch", "55 inch", "77 inch"),
            stockStatus = "In Stock (Free Wall Mount Installation Included)",
            isAssured = true,
            isDealOfTheDay = true,
            bankOffers = listOf("Flat ₹10,000 Instant Discount on Leading Bank Cards", "Free 2 Year Extended Comprehensive Warranty"),
            reviews = sampleReviews
        ),
        Product(
            id = "tv-2",
            name = "LG evo G4 65\" OLED Gallery Edition",
            brand = "LG",
            category = ProductCategory.TVS,
            price = 234990.0,
            originalPrice = 269990.0,
            discountPercent = 13,
            rating = 4.9f,
            ratingCount = 2890,
            reviewCount = 410,
            imageUrl = "https://images.unsplash.com/photo-1461151304267-38535e780c79?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Brightness Booster Max with Micro Lens Array (MLA) Plus",
                "Alpha 11 AI Processor 4K with 4x AI Graphics boost",
                "Zero Gap Flush Wall Mount Design (Gallery Edition)",
                "144Hz Gaming Refresh Rate with NVIDIA G-Sync & AMD FreeSync",
                "5-Year LG OLED Panel Warranty"
            ),
            specifications = mapOf(
                "Display" to listOf("Technology" to "LG OLED evo G4 MLA+", "Refresh Rate" to "144Hz VRR", "Mount" to "Flush-mount bracket included"),
                "Smart Features" to listOf("OS" to "webOS 24 with 5 years guaranteed OS upgrades", "Remote" to "Magic Remote with Air Mouse")
            ),
            colorVariants = listOf("Titanium Gallery Silver"),
            storageVariants = listOf("65 inch", "55 inch"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹12,000 Instant Discount with Bank of Baroda/HDFC Cards"),
            reviews = sampleReviews
        ),
        Product(
            id = "tv-3",
            name = "Samsung Neo QLED 65\" 4K QN90D",
            brand = "Samsung",
            category = ProductCategory.TVS,
            price = 179990.0,
            originalPrice = 209990.0,
            discountPercent = 14,
            rating = 4.7f,
            ratingCount = 4320,
            reviewCount = 570,
            imageUrl = "https://images.unsplash.com/photo-1577979749830-f1d742b96791?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Quantum Matrix Mini-LED with NQ4 AI Gen2 Processor",
                "Neo Quantum HDR+ with Real Depth Enhancer Pro",
                "Anti-Glare Ultra Viewing Angle screen",
                "OTS+ (Object Tracking Sound Plus) with Dolby Atmos 60W 4.2.2Ch",
                "SolarCell Remote with built-in mic and RF charging"
            ),
            specifications = mapOf(
                "Visuals" to listOf("Backlight" to "Quantum Mini LED", "Resolution" to "4K (3840 x 2160)", "AI Upscaling" to "4K AI Gen2"),
                "Gaming" to listOf("Motion" to "144Hz Motion Xcelerator", "Hub" to "Samsung Gaming Hub with Cloud Gaming")
            ),
            colorVariants = listOf("Titan Black Neo Slim"),
            storageVariants = listOf("65 inch", "75 inch"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹8,000 Instant Discount on ICICI Bank Cards"),
            reviews = sampleReviews
        ),

        // === MONITORS ===
        Product(
            id = "mon-1",
            name = "Dell UltraSharp 32\" 4K Thunderbolt Hub",
            brand = "Dell",
            category = ProductCategory.MONITORS,
            price = 84990.0,
            originalPrice = 99990.0,
            discountPercent = 15,
            rating = 4.8f,
            ratingCount = 3120,
            reviewCount = 420,
            imageUrl = "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "31.5 inch 4K IPS Black Panel with 2000:1 Contrast Ratio",
                "Thunderbolt 4 Hub with 90W Power Delivery & Daisy Chaining",
                "98% DCI-P3 & DisplayHDR 400 Color Accuracy for Creators",
                "Built-in 2.5GbE RJ45 Ethernet, KVM Switch & PiP/PbP",
                "ComfortView Plus Hardware Low Blue Light Filter"
            ),
            specifications = mapOf(
                "Panel" to listOf("Technology" to "IPS Black 4K", "Aspect" to "16:9", "Resolution" to "3840 x 2160 at 60Hz"),
                "Connectivity" to listOf("Thunderbolt 4" to "Up to 40Gbps & 90W laptop charge", "Ports" to "HDMI 2.1, DP 1.4, USB-C 10Gbps")
            ),
            colorVariants = listOf("Platinum Silver"),
            storageVariants = listOf("32 inch 4K Hub"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹4,000 Bank Cashback", "3 Years Advanced Exchange Warranty"),
            reviews = sampleReviews
        ),
        Product(
            id = "mon-2",
            name = "Samsung Odyssey OLED G9 49\" Curved",
            brand = "Samsung",
            category = ProductCategory.MONITORS,
            price = 129999.0,
            originalPrice = 149999.0,
            discountPercent = 13,
            rating = 4.9f,
            ratingCount = 2190,
            reviewCount = 350,
            imageUrl = "https://images.unsplash.com/photo-1547119957-637f8679db1e?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "49 inch Dual QHD (5120 x 1440) 32:9 Ultra-Wide OLED",
                "Blazing 240Hz Refresh Rate with 0.03ms Response Time",
                "1800R Curvature for Absolute Gaming Immersion",
                "Neo Quantum Processor Pro with CoreSync & Core Lighting+",
                "AMD FreeSync Premium Pro & VESA DisplayHDR True Black 400"
            ),
            specifications = mapOf(
                "Display" to listOf("Aspect Ratio" to "32:9 Super Ultra-Wide", "Panel" to "QD-OLED 240Hz", "Response" to "0.03ms GtG"),
                "Features" to listOf("Smart TV" to "Built-in Tizen OS with Gaming Hub", "Speakers" to "Built-in 5W x 2 Stereo")
            ),
            colorVariants = listOf("Silver Metal"),
            storageVariants = listOf("49 inch Dual QHD"),
            stockStatus = "Only 2 left in stock!",
            isAssured = true,
            isTrending = true,
            bankOffers = listOf("₹7,500 Instant Discount with EMI transactions"),
            reviews = sampleReviews
        ),

        // === HEADPHONES ===
        Product(
            id = "aud-1",
            name = "Sony WH-1000XM5 Wireless ANC",
            brand = "Sony",
            category = ProductCategory.HEADPHONES,
            price = 26990.0,
            originalPrice = 34990.0,
            discountPercent = 23,
            rating = 4.8f,
            ratingCount = 38400,
            reviewCount = 5400,
            imageUrl = "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Industry Leading Noise Cancellation with 8 Mics & 2 Processors",
                "Precision-engineered 30mm carbon fiber drivers with Hi-Res LDAC",
                "30 Hours Battery Life with Quick Charge (3 min = 3 hours)",
                "Multipoint Connection: Switch seamlessly between phone & laptop",
                "Crystal Clear Hands-Free Calling with 4 Beamforming Mics & AI"
            ),
            specifications = mapOf(
                "Acoustics" to listOf("Driver" to "30mm Neodymium", "Frequency" to "4 Hz - 40,000 Hz", "Codecs" to "LDAC, AAC, SBC"),
                "Battery" to listOf("Playtime" to "30 hours (ANC On), 40 hours (ANC Off)", "Port" to "USB-C")
            ),
            colorVariants = listOf("Black", "Silver", "Midnight Blue"),
            storageVariants = listOf("Standard Edition"),
            stockStatus = "In Stock (Fast Delivery Tomorrow)",
            isAssured = true,
            isTrending = true,
            isDealOfTheDay = true,
            bankOffers = listOf("₹2,500 Instant Discount with Bank Cards", "Extra ₹1,000 Off on SuperCoins"),
            reviews = sampleReviews
        ),
        Product(
            id = "aud-2",
            name = "Apple AirPods Max (USB-C)",
            brand = "Apple",
            category = ProductCategory.HEADPHONES,
            price = 59900.0,
            originalPrice = 59900.0,
            discountPercent = 0,
            rating = 4.7f,
            ratingCount = 12400,
            reviewCount = 1840,
            imageUrl = "https://images.unsplash.com/photo-1546435770-a3e426bf472b?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Apple-designed 40mm Dynamic Driver with Dual Neodymium Motors",
                "Pro-level Active Noise Cancellation with Transparency Mode",
                "Personalized Spatial Audio with Dynamic Head Tracking",
                "Anodized Aluminum Ear Cups with Breathable Mesh Canopy",
                "Now with universal USB-C Charging and High-Res Lossless Audio"
            ),
            specifications = mapOf(
                "Hardware" to listOf("Chip" to "Apple H1 in each ear cup", "Sensors" to "Optical, Position, Case, Accelerometer, Gyroscope"),
                "Battery" to listOf("Time" to "Up to 20 hours listening with ANC enabled", "Charging" to "USB-C")
            ),
            colorVariants = listOf("Midnight", "Starlight", "Blue", "Purple", "Orange"),
            storageVariants = listOf("USB-C Edition"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹3,000 Instant Discount on ICICI Cards", "No Cost EMI Available"),
            reviews = sampleReviews
        ),
        Product(
            id = "aud-3",
            name = "Samsung Galaxy Buds3 Pro",
            brand = "Samsung",
            category = ProductCategory.HEADPHONES,
            price = 17999.0,
            originalPrice = 19999.0,
            discountPercent = 10,
            rating = 4.6f,
            ratingCount = 7620,
            reviewCount = 890,
            imageUrl = "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Blade Design with LED Blade Lights & Pinch Gestures",
                "Enhanced 2-Way Speaker with Planar Tweeter & Dual Amp",
                "Adaptive Noise Control with Real-time Galaxy AI Interpreter",
                "24-bit / 96kHz Seamless Hi-Fi Audio Codec",
                "IP57 Water and Sweat Resistance"
            ),
            specifications = mapOf(
                "Sound" to listOf("Drivers" to "10.5mm Dynamic + 6.1mm Planar", "Audio" to "24-bit 96kHz Samsung Seamless"),
                "Durability" to listOf("Rating" to "IP57 Certified", "Battery" to "Up to 30 hours with Case")
            ),
            colorVariants = listOf("Silver", "White"),
            storageVariants = listOf("Standard Edition"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹2,000 Instant Discount on Select Cards"),
            reviews = sampleReviews
        ),

        // === ACCESSORIES ===
        Product(
            id = "acc-1",
            name = "Apple 140W USB-C Fast Charger & Cable",
            brand = "Apple",
            category = ProductCategory.ACCESSORIES,
            price = 9500.0,
            originalPrice = 10900.0,
            discountPercent = 13,
            rating = 4.8f,
            ratingCount = 6420,
            reviewCount = 580,
            imageUrl = "https://images.unsplash.com/photo-1583863788434-e58a36330cf0?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "140W Power Delivery with Gallium Nitride (GaN) Efficiency",
                "Charges MacBook Pro 16\" up to 50% in just 30 minutes",
                "Includes 2m Braided USB-C to MagSafe 3 Cable",
                "Compatible with all USB-C Mac, iPad, iPhone, and Android devices",
                "Comprehensive Over-Voltage and Surge Protection"
            ),
            specifications = mapOf(
                "Specs" to listOf("Output" to "140W Max", "Cable" to "2m Braided MagSafe 3", "Compatibility" to "Universal USB-C PD 3.1")
            ),
            colorVariants = listOf("White"),
            storageVariants = listOf("140W Pack"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("5% Cashback on MMKART Axis Card"),
            reviews = sampleReviews
        ),
        Product(
            id = "acc-2",
            name = "Samsung Trio Super Fast Wireless Charger",
            brand = "Samsung",
            category = ProductCategory.ACCESSORIES,
            price = 6499.0,
            originalPrice = 7999.0,
            discountPercent = 19,
            rating = 4.6f,
            ratingCount = 4910,
            reviewCount = 420,
            imageUrl = "https://images.unsplash.com/photo-1622445262464-84b14e0745b1?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "3-in-1 Pad: Charge Phone, Smartwatch, and Earbuds simultaneously",
                "Dedicated Magnetic Pad specifically for Galaxy Watches",
                "LED Status Indicators with Dimming Function for Bedside Sleep",
                "Supports Qi Certified iPhones, AirPods, and Android Flagships",
                "Includes 25W Wall Charger and Type-C Cable"
            ),
            specifications = mapOf(
                "Details" to listOf("Coils" to "6 Internal Charging Coils", "Max Output" to "9W Fast Wireless per device", "Input" to "25W PD")
            ),
            colorVariants = listOf("Black", "White"),
            storageVariants = listOf("Trio Pad"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("Extra 10% Off on Combo Purchases"),
            reviews = sampleReviews
        ),
        Product(
            id = "acc-3",
            name = "HP Thunderbolt 4 Dock 120W G4",
            brand = "HP",
            category = ProductCategory.ACCESSORIES,
            price = 19999.0,
            originalPrice = 24999.0,
            discountPercent = 20,
            rating = 4.7f,
            ratingCount = 1890,
            reviewCount = 210,
            imageUrl = "https://images.unsplash.com/photo-1541807084-5c52b6b3adef?w=600&auto=format&fit=crop&q=80",
            shortHighlights = listOf(
                "Universal Thunderbolt 4 Dock with 120W Power Delivery",
                "Supports up to four 4K 60Hz Displays or two 8K Displays",
                "Gigabit RJ45 Ethernet, 4x USB-A 3.2, 2x DisplayPort, HDMI 2.1",
                "HP Sure Start Hardware Root of Trust Security",
                "Tested and Certified for Apple, Dell, HP, Lenovo & Windows PCs"
            ),
            specifications = mapOf(
                "Hub" to listOf("Bandwidth" to "40Gbps Thunderbolt 4", "Power" to "120W Laptop pass-through", "Warranty" to "3 Years HP Onsite")
            ),
            colorVariants = listOf("Matte Black"),
            storageVariants = listOf("120W G4"),
            stockStatus = "In Stock",
            isAssured = true,
            bankOffers = listOf("₹1,500 Off with Corporate Card or Credit Cards"),
            reviews = sampleReviews
        )
    )

    val initialAddresses = listOf(
        Address(
            id = "addr-1",
            fullName = "Alex Johnson",
            phoneNumber = "+91 98765 43210",
            pincode = "560001",
            addressLine = "Flat 402, HighTech Tech Park Residences, MG Road",
            city = "Bengaluru",
            state = "Karnataka",
            type = "Home",
            isDefault = true
        ),
        Address(
            id = "addr-2",
            fullName = "Alex Johnson",
            phoneNumber = "+91 98765 43210",
            pincode = "560103",
            addressLine = "Level 6, Tower B, Prestige Tech Cloud Campus, Outer Ring Rd",
            city = "Bengaluru",
            state = "Karnataka",
            type = "Work",
            isDefault = false
        )
    )

    val initialOrders = listOf(
        Order(
            orderId = "MM-94812-2026",
            items = listOf(
                CartItem(
                    product = products.first { it.id == "aud-1" }, // Sony WH-1000XM5
                    quantity = 1,
                    selectedColor = "Midnight Blue",
                    selectedStorage = "Standard Edition"
                )
            ),
            totalAmount = 34990.0,
            discountAmount = 8000.0,
            deliveryFee = 0.0,
            finalAmount = 26990.0,
            orderDate = "Yesterday, 3:45 PM",
            estimatedDelivery = "Tomorrow by 11:00 AM",
            deliveryAddress = initialAddresses.first(),
            paymentMethod = "UPI (Google Pay)",
            status = OrderStatus.OUT_FOR_DELIVERY,
            trackingSteps = listOf(
                TrackingStep(OrderStatus.PLACED, "04 Oct, 3:45 PM", "Bengaluru Central Hub", isCompleted = true, isCurrent = false),
                TrackingStep(OrderStatus.PACKED, "04 Oct, 6:10 PM", "MMKART Electronic Vault 3", isCompleted = true, isCurrent = false),
                TrackingStep(OrderStatus.SHIPPED, "05 Oct, 2:00 AM", "Express Transit Bengaluru", isCompleted = true, isCurrent = false),
                TrackingStep(OrderStatus.OUT_FOR_DELIVERY, "Today, 8:30 AM", "Out with Courier Agent Rahul (9845012345)", isCompleted = true, isCurrent = true),
                TrackingStep(OrderStatus.DELIVERED, "Expected by 11:00 AM", "Delivery at MG Road address", isCompleted = false, isCurrent = false)
            )
        ),
        Order(
            orderId = "MM-82104-2026",
            items = listOf(
                CartItem(
                    product = products.first { it.id == "mob-3" }, // OnePlus 13
                    quantity = 1,
                    selectedColor = "Midnight Ocean",
                    selectedStorage = "512 GB / 16GB RAM"
                ),
                CartItem(
                    product = products.first { it.id == "acc-1" }, // Apple 140W charger
                    quantity = 1,
                    selectedColor = "White",
                    selectedStorage = "140W Pack"
                )
            ),
            totalAmount = 80899.0,
            discountAmount = 6400.0,
            deliveryFee = 0.0,
            finalAmount = 74499.0,
            orderDate = "28 Sep 2026",
            estimatedDelivery = "Delivered on 30 Sep 2026",
            deliveryAddress = initialAddresses.first(),
            paymentMethod = "Credit Card (HDFC **** 8821)",
            status = OrderStatus.DELIVERED,
            trackingSteps = listOf(
                TrackingStep(OrderStatus.PLACED, "28 Sep, 10:15 AM", "Bengaluru Hub", isCompleted = true, isCurrent = false),
                TrackingStep(OrderStatus.PACKED, "28 Sep, 1:30 PM", "MMKART Vault", isCompleted = true, isCurrent = false),
                TrackingStep(OrderStatus.SHIPPED, "29 Sep, 4:00 AM", "Hub Transit", isCompleted = true, isCurrent = false),
                TrackingStep(OrderStatus.OUT_FOR_DELIVERY, "30 Sep, 9:00 AM", "Out for Delivery", isCompleted = true, isCurrent = false),
                TrackingStep(OrderStatus.DELIVERED, "30 Sep, 1:45 PM", "Delivered safely with OTP verification", isCompleted = true, isCurrent = true)
            )
        )
    )
}
