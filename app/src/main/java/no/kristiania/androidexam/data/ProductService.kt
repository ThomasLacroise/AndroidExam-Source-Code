package no.kristiania.androidexam.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("https://fakestoreapi.com/products/")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("https://fakestoreapi.com/products/{id}/")
    suspend fun  getProductDetails(
        @Path("id") id: Int
    ): Response<ProductDetails>
}