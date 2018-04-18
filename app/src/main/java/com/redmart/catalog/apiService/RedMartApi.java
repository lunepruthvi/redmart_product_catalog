package com.redmart.catalog.apiService;

import com.redmart.catalog.model.productDetails.ProductDetailsResponse;
import com.redmart.catalog.model.productList.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedMartApi {

    @GET("/v1.6.0/catalog/search")
    Call<ProductListResponse> getAllProducts(@Query("page") String pageCount, @Query("pageSize") String pageSize);


    @GET("/v1.6.0/catalog/products/{product_id}")
    Call<ProductDetailsResponse> getProductDetails(@Path("product_id") String productId);


}
