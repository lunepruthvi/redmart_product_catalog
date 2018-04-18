package com.redmart.catalog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.redmart.catalog.R;
import com.redmart.catalog.adapters.ProductListAdapter;
import com.redmart.catalog.base.BaseActivity;
import com.redmart.catalog.base.BaseFragment;
import com.redmart.catalog.interfaces.callback.OnProductClickListener;
import com.redmart.catalog.model.productList.Product;
import com.redmart.catalog.presenter.ProductCatalogPresenter;
import com.redmart.catalog.services.ProductCatalogService;
import com.redmart.catalog.utils.GridSpacingItemDecoration;
import com.redmart.catalog.views.ProductCatalogView;
import com.redmart.catalog.widgets.CustomFontTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.redmart.catalog.appconfig.AppConfig.PRODUCT_CATALOG_GRID_SPAN;


public class ProductCatalogFragment extends BaseFragment implements BaseActivity.ConnectivityReceiver,
        ProductCatalogView,
        OnProductClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.errorTextView)
    CustomFontTextView errorTextView;

    private ProductCatalogPresenter productCatalogPresenter;
    private ProductListAdapter productListAdapter;
    private boolean isLoading = false;
    private int pageCount = 0;
    private GridLayoutManager gridLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, view);
        gridLayoutManager = new GridLayoutManager(getActivity(), PRODUCT_CATALOG_GRID_SPAN);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(PRODUCT_CATALOG_GRID_SPAN, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()), true));
        productCatalogPresenter = new ProductCatalogPresenter(getActivity(),
                new ProductCatalogService(),
                this,
                recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setConnectivityReceiver(this);
        }
    }

    @Override
    public String getFragmentTitle() {
        return "RedMart";
    }

    @Override
    public void hideMainProgressBar() {
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMainProgressBar() {

        isLoading = true;
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void showThisErrorMsg(String errorMsg) {
        errorTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        errorTextView.setText(errorMsg);
        recyclerView.setVisibility(View.GONE);
    }


    @Override
    public void showOrUpdateRecyclerView(List<Product> products) {
        Timber.d("-- showOrUpdateRecyclerView --");
        hideMainProgressBar();
        if (productListAdapter == null) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            productListAdapter = new ProductListAdapter(activity, products, this);
            recyclerView.setAdapter(productListAdapter);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            productListAdapter.getProductItems().addAll(products);
            productListAdapter.notifyItemRangeInserted(pageCount, productListAdapter.getProductItems().size());
        }
        pageCount = productListAdapter.getProductItems().size();
        isLoading = false;
    }


    @Override
    public void callDetailViewForThisProduct(Intent intent, ActivityOptionsCompat activityOptions) {
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
    }

    @Override
    public void onProductClicked(Product product, View imageView, View amountView) {
        productCatalogPresenter.onProductClicked(activity, product);
        //productCatalogPresenter.onProductClicked(activity, product, imageView, amountView);
    }

    @Override
    public RecyclerView getProductRecyclerView() {
        return recyclerView;
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return gridLayoutManager;
    }

    @Override
    public int getPageCount() {
        return pageCount;
    }

    @Override
    public void onConnected() {
        productCatalogPresenter.loadProducts();
    }
}
