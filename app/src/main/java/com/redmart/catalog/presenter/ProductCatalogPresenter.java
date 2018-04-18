package com.redmart.catalog.presenter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.redmart.catalog.activities.ProductDetailsActivity;
import com.redmart.catalog.interfaces.callback.GetProductsListener;
import com.redmart.catalog.model.productList.Product;
import com.redmart.catalog.model.productList.ProductListResponse;
import com.redmart.catalog.services.ProductCatalogService;
import com.redmart.catalog.views.ProductCatalogView;

import timber.log.Timber;


public class ProductCatalogPresenter {

    public boolean isLastPage;
    Context context;
    ProductCatalogService productCatalogService;
    ProductCatalogView productCatalogView;
    public GetProductsListener getProductsListener = new GetProductsListener() {
        @Override
        public void onProductListSuccess(ProductListResponse productsListResponse) {
            if (productsListResponse.getProducts().size() > 0) {
                isLastPage = false;
            } else {
                isLastPage = true;
            }
            productCatalogView.showOrUpdateRecyclerView(productsListResponse.getProducts());
        }

        @Override
        public void onProductListError(String errorMsg) {
            isLastPage = true;
            productCatalogView.showThisErrorMsg(errorMsg);
        }
    };
    int pageCounter;
    public RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = productCatalogView.getLayoutManager().getChildCount();
            int totalItemCount = productCatalogView.getLayoutManager().getItemCount();
            int firstVisibleItemPosition = productCatalogView.getLayoutManager().findFirstVisibleItemPosition();

            if (!productCatalogView.isLoading() && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= productCatalogView.getPageCount()) {
                    loadProducts();
                }
            }
        }
    };

    public ProductCatalogPresenter(Context context,
                                   ProductCatalogService productCatalogService,
                                   ProductCatalogView productCatalogView,
                                   RecyclerView recyclerView) {
        this.context = context;
        this.productCatalogService = productCatalogService;
        this.productCatalogView = productCatalogView;
        pageCounter = 0;
        isLastPage = false;
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        makeGetProductsRequest();
    }

    private void makeGetProductsRequest() {
        productCatalogView.showMainProgressBar();

        productCatalogService.getProductList(pageCounter, getProductsListener);
    }

    public void loadProducts() {
        Timber.d("-- loadProducts --");
        pageCounter++;
        makeGetProductsRequest();
    }

    public void onProductClicked(Activity activity, Product product, View imageView, View amountView) {
        Timber.d("-- onProductClicked --");
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, new Pair<View, String>(imageView, "productImage"),
                new Pair<View, String>(amountView, "productName"));

        productCatalogView.callDetailViewForThisProduct(intent, activityOptions);
    }

    public void onProductClicked(Activity activity, Product product) {
        Timber.d("-- onProductClicked --");
        ProductDetailsActivity.startActivity(activity, product);
    }
}
