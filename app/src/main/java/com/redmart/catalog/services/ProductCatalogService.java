package com.redmart.catalog.services;

import com.redmart.catalog.apiService.RetrofitAdapters;
import com.redmart.catalog.interfaces.callback.GetProductsListener;
import com.redmart.catalog.model.productList.ProductListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ProductCatalogService {

    public ProductCatalogService() {
    }

    public void getProductList(int pageCount, final GetProductsListener getProductsListener) {

        //getProductsListener.onProductListLoadingStarted();

        Timber.d("-- getProductList --");
        Call<ProductListResponse> call = RetrofitAdapters.getWebServiceInterface().getAllProducts(String.valueOf(pageCount), String.valueOf(20));

        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.isSuccessful()) {
                    getProductsListener.onProductListSuccess(response.body());
                } else {
                    getProductsListener.onProductListError("Something went wrong..");
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable throwable) {
                getProductsListener.onProductListError(throwable.getLocalizedMessage());
            }
        });
        //getProductsListener.onProductListLoadingFinished();
    }

}
