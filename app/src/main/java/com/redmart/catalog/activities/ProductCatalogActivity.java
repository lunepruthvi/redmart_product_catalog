package com.redmart.catalog.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.redmart.catalog.R;
import com.redmart.catalog.base.BaseActivity;
import com.redmart.catalog.base.BaseFragment;
import com.redmart.catalog.fragment.ProductCatalogFragment;
import com.redmart.catalog.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProductCatalogActivity extends BaseActivity {
    private static String TAG = "ProductCatalogActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private BaseFragment currentSelectedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        loadMainFragment(new ProductCatalogFragment());
        Logger.d3(TAG, "@onCreate");

        registerConnectivityChangeReceiver();
    }


    private void loadMainFragment(BaseFragment fragment) {
        currentSelectedFragment = fragment;
        getSupportActionBar().setTitle(fragment.getFragmentTitle());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
        Logger.d3(TAG, "loadMainFragment");
    }

    @Override
    protected void onResume() {
        Logger.d3(TAG, "@onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Logger.d3(TAG, "@onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
