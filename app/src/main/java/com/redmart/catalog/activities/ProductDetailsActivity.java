package com.redmart.catalog.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.redmart.catalog.R;
import com.redmart.catalog.adapters.DetailViewImageAdapter;
import com.redmart.catalog.adapters.ProductDetailsDescriptionAdapter;
import com.redmart.catalog.base.BaseActivity;
import com.redmart.catalog.model.productList.Image;
import com.redmart.catalog.model.productList.Primary;
import com.redmart.catalog.model.productList.Product;
import com.redmart.catalog.presenter.ProductDetailViewPresenter;
import com.redmart.catalog.services.DetailViewService;
import com.redmart.catalog.utils.Utils;
import com.redmart.catalog.views.ProductDetailsView;
import com.redmart.catalog.widgets.CustomFontTextView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class ProductDetailsActivity extends BaseActivity implements ProductDetailsView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageViewPager)
    ViewPager imageViewPager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.productTitleTextView)
    CustomFontTextView productTitleTextView;
    @BindView(R.id.sizeDetailsTextView)
    CustomFontTextView sizeDetailsTextView;
    @BindView(R.id.priceMainTextView)
    CustomFontTextView priceMainTextView;
    @BindView(R.id.priceOriginalTextView)
    CustomFontTextView priceOriginalTextView;
    @BindView(R.id.promoTextView)
    CustomFontTextView promoTextView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private ProductDetailViewPresenter productDetailViewPresenter;

    public static void startActivity(Context context, Product product) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Product selectedProduct = (Product) getIntent().getParcelableExtra("product");

        // recycler view layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        productDetailViewPresenter = new ProductDetailViewPresenter(activity, this, new DetailViewService(selectedProduct));
        productDetailViewPresenter.displayAllDetails();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateTitleAndSizeDetails(String productName, String productDetails) {
        Timber.d("-- updateTitleAndSizeDetails --");
        getSupportActionBar().setTitle(productName);
        productTitleTextView.setText(productName);
        if (productDetails != null) {
            sizeDetailsTextView.setText(productDetails);
        }
        sizeDetailsTextView.setVisibility(productDetails == null ? View.GONE : View.VISIBLE);
    }

    @Override
    public void updateAmountDetails(String currentAmount, String originalAmount, String savingMsg, int savingBgColor) {
        if (originalAmount != null) {
            priceOriginalTextView.setText(originalAmount);
            priceOriginalTextView.setPaintFlags(priceOriginalTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (savingMsg != null) {
            promoTextView.setText(savingMsg);
            promoTextView.setBackgroundColor(getResources().getColor(savingBgColor));
        }

        priceMainTextView.setText(currentAmount);
        priceOriginalTextView.setVisibility(originalAmount == null ? View.GONE : View.VISIBLE);
        promoTextView.setVisibility(savingMsg == null ? View.GONE : View.VISIBLE);

    }

    @Override
    public void updateImageGallery(List<Image> images) {
        Timber.d("-- updateImageGallery --");
        for (Image image : images) {

        }
        imageViewPager.setAdapter(new DetailViewImageAdapter(activity, images));
        indicator.setViewPager(imageViewPager);
        indicator.setVisibility(images.size() > 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateProductMetaDescriptions(List<Primary> metaDetailsList) {
        recyclerView.setAdapter(new ProductDetailsDescriptionAdapter(activity, metaDetailsList));
    }

    @Override
    public void showThisErrorMsg(int stringResourceId) {
        Utils.showShortToast(activity, getString(stringResourceId));
    }
}
