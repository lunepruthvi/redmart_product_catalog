package com.redmart.catalog.interfaces.callback;

import android.view.View;

import com.redmart.catalog.model.productList.Product;

public interface OnProductClickListener {

    void onProductClicked(Product product, View imageView, View amountView);

}
