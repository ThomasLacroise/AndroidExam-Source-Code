package no.kristiania.androidexam.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import no.kristiania.androidexam.data.room.AppDatabase
import no.kristiania.androidexam.screens.product_cart.ProductCartViewModel.loadCartItems
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Repository for the backend //
object ProductRepository {
    // Logs HTTP requests/responses to logcat
    // for better error message and handling
    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    // Creates a retrofit client that gets the base url from the api
    // Converter for Gson to help parse the Json in to the apps data classes //
    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/products/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // Retrofit creates an instance of ProductService so my functions get access
    // from the api
    private val _productService = _retrofit.create(ProductService::class.java)

    // Accesses my local database query's
    private lateinit var _appDatabase: AppDatabase
    private val _productDao by lazy { _appDatabase.productDao() }
    private val _productDetailsDao by lazy { _appDatabase.productDetailsDao() }
    private val _productCartDao by lazy { _appDatabase.productCartDao() }
    private val _orderHistoryDao by lazy { _appDatabase.orderHistoryDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // Function to get all products from the api and display in list screen
    suspend fun getProducts(): List<Product> {
        try {
            val response = _productService.getAllProducts()
            // Checks for possible crashes
            if (!response.isSuccessful) {
                throw Exception("Response was not successful: HTTP ${response.code()}")
            }

            val products = response.body()?.map { product ->
                product.copy(image = product.image)
            } ?: throw Exception("response.body() was empty")

            _productDao.insertProducts(products)
            return _productDao.getProducts()
        } catch (e: Exception) {
            Log.e("ProductRepository", "Failed to get list of products", e)
            return _productDao.getProducts()
        }
    }

    // Function to get product details using the ID of the product
    suspend fun getProductDetails(id: Int): ProductDetails? {
        try {
            val response = _productService.getProductDetails(id)
            // Throws error message if response is not successful
            if (!response.isSuccessful) {
                throw Exception("Response was not successful: HTTP ${response.code()}")
            }

            val productDetails = response.body() ?: throw Exception("response.body() was empty")

            _productDetailsDao.insertProductDetail(productDetails.copy(image = productDetails.image))
            return _productDetailsDao.getProductById(id)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Failed to get productDetails", e)
            return _productDetailsDao.getProductById(id)
        }
    }

    // Product cart functions with functions to check if there is
    // already a product in the cart, either adds the product or increases the value //
    suspend fun addToCart(productDetails: ProductDetails, quantity: Int) {
        val existingCartItem = _productCartDao.getCartItemById(productDetails.id)
        if (existingCartItem != null) {
            _productCartDao.increaseQuantity(productDetails.id)
        } else {
            val cartItem = CartItem(
                id = productDetails.id,
                title = productDetails.title,
                image = productDetails.image,
                category = productDetails.category,
                price = productDetails.price,
                quantity = quantity
            )
            _productCartDao.addToCart(cartItem)
            Log.d("ProductRepository", "Added product ${productDetails.id}")
            loadCartItems()
        }
    }

    fun getCartItems(): Flow<List<CartItem>> {
        return _productCartDao.getCartItems()
    }

    suspend fun removeFromCart(cartItem: CartItem) {
        Log.d("ProductRepository", "Product removed from cart successfully")
        _productCartDao.removeFromCart(cartItem)
    }

    suspend fun increaseQuantity(cartItem: CartItem) {
        try {
            _productCartDao.increaseQuantity(cartItem.id)
            println("Increased quantity")
        } catch (e: Exception) {
            println("increase quantity failed: ${e.message}")
        }
    }

    suspend fun decreaseQuantity(cartItem: CartItem) {
        try {
            _productCartDao.decreaseQuantity(cartItem.id)
            println("Decreased quantity ")
        } catch (e: Exception) {
            println("Decrease quantity failed: ${e.message}")
        }
    }

    // Function to place order
    suspend fun placeOrder(order: Order) {
        try {
            _orderHistoryDao.addToOrderHistory(order)
            Log.d("ProductRepository", "Placed order: ${order.orderId}")
        } catch (e: Exception) {
            Log.e("ProductRepository", "Failed to place order", e)
        }
    }

    // Order history functions

    fun getOrderHistory(): Flow<List<Order>> {
        return _orderHistoryDao.getOrders()
    }

    suspend fun clearCart() {
        try {
            _productCartDao.clearCart()
            Log.d("ProductRepository", "Cart cleared successfully")
        } catch (e: Exception) {
            Log.e("ProductRepository", "Failed to clear cart", e)
        }
    }
}
