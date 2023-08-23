package com.skydevices.fakestoreapi.api

import com.skydevices.fakestoreapi.model.CategoryResponseAPI
import com.skydevices.fakestoreapi.model.responseAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProdutoAPI {

    @GET("products/category/{categoria}")
    suspend fun recuperarPorCategoria(
        @Path("categoria")
        Categoria: String
    ) : Response<responseAPI>

    @GET("products")
    suspend fun recuperarProdutos(
    ) : Response<responseAPI>

    @GET("products/categories")
    suspend fun recuperarCategoria() : Response<CategoryResponseAPI>
}