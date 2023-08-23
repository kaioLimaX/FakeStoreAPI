package com.skydevices.fakestoreapi.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    const val BASE_URL = "https://fakestoreapi.com/"

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .writeTimeout(10, TimeUnit.SECONDS)// Escrita(salvando na API)tempo maximo
        .readTimeout(20, TimeUnit.SECONDS) //  Leitura(recuperando dados da API)tempo maximo
        .connectTimeout(20, TimeUnit.SECONDS)//tempo maximo de conexao
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl( BASE_URL )
        .addConverterFactory( GsonConverterFactory.create() )
        .client(okHttpClient)
        .build()

    val produtoAPI = retrofit.create( ProdutoAPI::class.java )
}