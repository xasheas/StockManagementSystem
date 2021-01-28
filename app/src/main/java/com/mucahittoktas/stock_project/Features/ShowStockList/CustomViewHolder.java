package com.mucahittoktas.stock_project.Features.ShowStockList;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mucahittoktas.stock_project.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;
    TextView barcodeNumTextView;
    TextView quantityTextView;
    TextView purhase_priceTextView;
    TextView sell_priceTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder(View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.nameTextView);
        barcodeNumTextView = itemView.findViewById(R.id.barcodeNumTextView);
        quantityTextView = itemView.findViewById(R.id.quantityTextView);
        purhase_priceTextView = itemView.findViewById(R.id.purchase_priceTextView);
        sell_priceTextView = itemView.findViewById(R.id.sell_priceTextView);

        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }
}
