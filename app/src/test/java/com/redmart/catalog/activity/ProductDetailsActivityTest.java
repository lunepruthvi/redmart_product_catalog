package com.redmart.catalog.activity;

import android.content.Context;

import com.redmart.catalog.R;
import com.redmart.catalog.model.productList.Product;
import com.redmart.catalog.presenter.ProductDetailViewPresenter;
import com.redmart.catalog.services.DetailViewService;
import com.redmart.catalog.views.ProductDetailsView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsActivityTest {

    DetailViewService detailViewService;

    @Mock
    ProductDetailsView productDetailsView;
    @Mock
    Context context;
    @Mock
    Product product;

    ProductDetailViewPresenter detailViewPresenter;

    @Test
    public void checkIfProductObjectIsNullCase() throws Exception {
        detailViewService = new DetailViewService(null);
        detailViewPresenter = new ProductDetailViewPresenter(context, productDetailsView, detailViewService);
        detailViewPresenter.displayAllDetails();
        verify(productDetailsView, only()).showThisErrorMsg(R.string.err_productDetailsNotFound);
    }

    @Test
    public void checkIfProductObjectIsNotNullCase() throws Exception {
        detailViewService = new DetailViewService(product);
        detailViewPresenter = new ProductDetailViewPresenter(context, productDetailsView, detailViewService);
        detailViewPresenter.displayAllDetails();
        verify(productDetailsView, never()).showThisErrorMsg(R.string.err_productDetailsNotFound);
    }

    @Test
    public void checkIfTitleDisplayerCorrectly() throws Exception {
        // here we can get an actual object of Product (from a JSON raw file and make assert test cases with the known values)

        detailViewService = new DetailViewService(product);
        detailViewPresenter = new ProductDetailViewPresenter(context, productDetailsView, detailViewService);
        when(product.getTitle()).thenReturn("");
        detailViewPresenter.displayAllDetails();
        verify(productDetailsView, atLeastOnce()).updateTitleAndSizeDetails("", null);
    }

    @Test
    public void checkIfImageAdapterInvokedCorrectly() throws Exception {
        // here we can get an actual object of Product (from a JSON raw file and make assert test cases with the known values)
        detailViewService = new DetailViewService(product);
        detailViewPresenter = new ProductDetailViewPresenter(context, productDetailsView, detailViewService);
        detailViewPresenter.displayAllDetails();
        verify(productDetailsView, never()).updateImageGallery(null);
    }

}