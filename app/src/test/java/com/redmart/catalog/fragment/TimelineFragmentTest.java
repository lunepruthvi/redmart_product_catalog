package com.redmart.catalog.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.redmart.catalog.interfaces.callback.GetProductsListener;
import com.redmart.catalog.model.productList.Product;
import com.redmart.catalog.presenter.ProductCatalogPresenter;
import com.redmart.catalog.services.ProductCatalogService;
import com.redmart.catalog.views.ProductCatalogView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimelineFragmentTest {


    @Mock
    Product product;
    @Mock
    ProductCatalogView timelineView;
    @Mock
    ProductCatalogService productCatalogService;
    @Mock
    Context context;
    @Mock
    Activity activity;
    @Mock
    View imageView;
    @Mock
    View amountView;
    @Mock
    RecyclerView recyclerView;
    @Mock
    GetProductsListener getProductsListener;
    ProductCatalogPresenter timelinePresenter;
    @Mock
    private List<Product> productsList;

    @Before
    public void setUp() throws Exception {
        timelinePresenter = new ProductCatalogPresenter(context, productCatalogService, timelineView, recyclerView);
    }

    @Test
    public void testForMainProductListingSuccessCase() throws Exception {
        timelinePresenter.loadProducts();
        verify(timelineView, atLeastOnce()).showMainProgressBar();
        verify(productCatalogService, atLeastOnce()).getProductList(0, timelinePresenter.getProductsListener);
    }

    @Test
    public void testForMainProductListingErrorCase() throws Exception {
        timelinePresenter.getProductsListener.onProductListError("something wrong");
        verify(timelineView, atLeastOnce()).showThisErrorMsg("something wrong");
    }

    @Test
    public void testForProductClickedFlow() throws Exception {
        timelinePresenter.onProductClicked(activity, product, imageView, amountView);
        timelineView.callDetailViewForThisProduct(any(Intent.class), any(ActivityOptionsCompat.class));
    }


    @Test
    public void testForLoadMoreScroller() throws Exception {
        when(timelineView.getLayoutManager()).thenReturn(new GridLayoutManager(activity, 2));
        when(timelineView.isLoading()).thenReturn(false);
        timelinePresenter.recyclerViewOnScrollListener.onScrolled(recyclerView, 1, 1);
        verify(productCatalogService, atLeastOnce()).getProductList(0, timelinePresenter.getProductsListener);
    }


    @Test
    public void testForCurrentLoadingOnCase() throws Exception {
        when(timelineView.getLayoutManager()).thenReturn(new GridLayoutManager(activity, 2));
        when(timelineView.isLoading()).thenReturn(true);
        timelinePresenter.recyclerViewOnScrollListener.onScrolled(recyclerView, 1, 1);
        verify(productCatalogService, never()).getProductList(1, timelinePresenter.getProductsListener);
    }


    @Test
    public void testForPageCountCompleteForLoadMoreCase() throws Exception {
        when(timelineView.getLayoutManager()).thenReturn(new GridLayoutManager(activity, 2));
        when(timelineView.isLoading()).thenReturn(true);
        timelinePresenter.isLastPage = true;
        timelinePresenter.recyclerViewOnScrollListener.onScrolled(recyclerView, 1, 1);
        verify(productCatalogService, never()).getProductList(1, timelinePresenter.getProductsListener);
    }


}