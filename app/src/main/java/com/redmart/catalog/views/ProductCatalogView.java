package com.redmart.catalog.views;


import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.redmart.catalog.model.productList.Product;

import java.util.List;

public interface ProductCatalogView {

    void showMainProgressBar();

    void hideMainProgressBar();

    void showThisErrorMsg(String errorMsg);

    void showOrUpdateRecyclerView(List<Product> products);


    void callDetailViewForThisProduct(Intent intent, ActivityOptionsCompat activityOptions);

    RecyclerView getProductRecyclerView();

    boolean isLoading();

    GridLayoutManager getLayoutManager();

    int getPageCount();

}
