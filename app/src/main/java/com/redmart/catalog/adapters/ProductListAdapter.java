package com.redmart.catalog.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.redmart.catalog.R;
import com.redmart.catalog.model.productList.Product;
import com.redmart.catalog.utils.AppConstants;
import com.redmart.catalog.utils.Utils;
import com.redmart.catalog.widgets.CustomFontTextView;
import com.redmart.catalog.widgets.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LOADING_VIEW = 1;
    private static final int ITEM_VIEW = 2;
    private List<Product> productItems;
    private Context context;
    private com.redmart.catalog.interfaces.callback.OnProductClickListener productClickListener;
    private boolean isLoadingAdded;

    public ProductListAdapter(Activity activity, List<Product> discoverDataTables, com.redmart.catalog.interfaces.callback.OnProductClickListener productClickListener) {
        this.productItems = discoverDataTables;
        this.context = activity;
        this.productClickListener = productClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING_VIEW) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_product_list_loader, null);
            return new ProgressBarFooterViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, null);
            return new ProductListViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holderObject, final int position) {

        if (holderObject instanceof ProductListViewHolder) {
            final ProductListViewHolder holder = (ProductListViewHolder) holderObject;
            final Product productData = productItems.get(position);

            Utils.loadThisImage(context, AppConstants.getImageUrl(productData.getImg().getName()), holder.productImageView);
            holder.productTitleTextView.setText(productData.getTitle());

            if (productData.getPricing() != null) {
                holder.priceMainTextView.setVisibility(View.VISIBLE);
                if (productData.getPricing().getPromoPrice() != null && productData.getPricing().getPromoPrice() > 0) {
                    holder.priceOriginalTextView.setVisibility(View.VISIBLE);
                    holder.priceMainTextView.setText(Utils.formatAmountToString(productData.getPricing().getPromoPrice()));
                    holder.priceOriginalTextView.setText(Utils.formatAmountToString(productData.getPricing().getPrice()));
                    holder.priceOriginalTextView.setPaintFlags(holder.priceOriginalTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.priceOriginalTextView.setVisibility(View.GONE);
                    holder.priceMainTextView.setText(Utils.formatAmountToString(productData.getPricing().getPrice()));
                }

                if (productData.getPricing().getSavingsText() != null) {
                    holder.promoTextView.setVisibility(View.VISIBLE);
                    holder.promoTextView.setText(productData.getPricing().getSavingsText());
                    if (productData.getPricing().getSavingsType() != null) {
                        int colorResId;
                        if (productData.getPricing().getSavingsType() == 1) {
                            colorResId = R.color.promoType_1;
                        } else if (productData.getPricing().getSavingsType() == 2) {
                            colorResId = R.color.promoType_2;
                        } else {
                            colorResId = R.color.promoType_others;
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            holder.promoTextView.setBackgroundColor(context.getColor(colorResId));
                        } else {
                            holder.promoTextView.setBackgroundColor(context.getResources().getColor(colorResId));
                        }
                    }
                } else {
                    holder.promoTextView.setVisibility(View.GONE);
                }

            } else {
                holder.priceMainTextView.setVisibility(View.GONE);
            }

            if (productData.getMeasure() != null && productData.getMeasure().getWtOrVol() != null) {
                holder.sizeDetailsTextView.setText(productData.getMeasure().getWtOrVol());
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productClickListener.onProductClicked(productData, holder.productImageView, holder.productTitleTextView);
                }
            });
        } else {
            ProgressBarFooterViewHolder holder = (ProgressBarFooterViewHolder) holderObject;

            holder.progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (position == productItems.size() - 1 && isLoadingAdded) ? LOADING_VIEW : ITEM_VIEW;
    }

    public List<Product> getProductItems() {
        return productItems;
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    static class ProgressBarFooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        ProgressBarFooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ProductListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productImageView)
        SquareImageView productImageView;
        @BindView(R.id.productMainCardView)
        CardView productMainCardView;
        @BindView(R.id.promoTextView)
        CustomFontTextView promoTextView;
        @BindView(R.id.productTitleTextView)
        CustomFontTextView productTitleTextView;
        @BindView(R.id.sizeDetailsTextView)
        CustomFontTextView sizeDetailsTextView;
        @BindView(R.id.priceMainTextView)
        CustomFontTextView priceMainTextView;
        @BindView(R.id.priceOriginalTextView)
        CustomFontTextView priceOriginalTextView;

        ProductListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

