package com.example.myapplication.model

import com.androidnetworking.AndroidNetworking
import com.google.gson.Gson
import com.kole.myapplication.cms.nnsettings.NNSettingsUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiHandlerImpl : ApiHandler {
    override suspend fun getProductInfo(): Product = withContext(Dispatchers.IO) {
        return@withContext AndroidNetworking.get(NNSettingsUrl("ProductInfoUrl", PRODUCT_INFO_URL))
            .build().executeForObject(Product::class.java).result
    } as Product

    override suspend fun getSimilarProductsInfo(): SimilarProductsInfo =
        withContext(Dispatchers.IO) {
            val response =
                AndroidNetworking.get(NNSettingsUrl("SimilarProductsUrl", SIMILAR_PRODUCTS_URL))
                    .build().executeForJSONArray().result
            val products = Gson().fromJson(response.toString(), Array<Product>::class.java).toList()
            return@withContext SimilarProductsInfo(products)
        }
}

const val PRODUCT_INFO_URL = "https://private-0c5632-yusuf6.apiary-mock.com/product"
const val SIMILAR_PRODUCTS_URL = "https://private-0c5632-yusuf6.apiary-mock.com/product/similar"
