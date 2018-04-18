package com.redmart.catalog.interfaces.callback;


import com.redmart.catalog.model.productList.ProductListResponse;

public interface GetProductsListener {

    void onProductListSuccess(ProductListResponse productsListResponse);

    void onProductListError(String errorMsg);


}
