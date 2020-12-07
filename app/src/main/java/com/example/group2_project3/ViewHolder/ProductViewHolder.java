package com.example.group2_project3.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group2_project3.Interface.ItemClickListener;
import com.example.group2_project3.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView mImageView;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    //throwing error , jerry
    @Override
    public void onClick(View view) {
//         listener.onClick(view, getAdapterPosition(), false);
    }

}
