package com.mucahittoktas.stock_project.Features.ShowStockList;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mucahittoktas.stock_project.Database.DatabaseQueryClass;
import com.mucahittoktas.stock_project.Features.CreateStock.Stock;
import com.mucahittoktas.stock_project.Features.UpdateStockInfo.StockUpdateDialogFragment;
import com.mucahittoktas.stock_project.Features.UpdateStockInfo.StockUpdateListener;
import com.mucahittoktas.stock_project.R;
import com.mucahittoktas.stock_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class StockListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Stock> stockList;
    private DatabaseQueryClass databaseQueryClass;

    public StockListRecyclerViewAdapter(Context context, List<Stock> stockList) {
        this.context = context;
        this.stockList = stockList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Stock stock = stockList.get(position);

        holder.nameTextView.setText(stock.getName());
        holder.barcodeNumTextView.setText(String.valueOf(stock.getBarcode()));
        holder.quantityTextView.setText(stock.getQuantity());
        holder.purhase_priceTextView.setText(stock.getPurchase_price());
        holder.sell_priceTextView.setText(stock.getSell_price());


        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this stock?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        deleteStock(itemPosition);
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StockUpdateDialogFragment stockUpdateDialogFragment = StockUpdateDialogFragment.newInstance(stock.getBarcode(), itemPosition, new StockUpdateListener() {
                    @Override
                    public void onStockInfoUpdated(Stock stock, int position) {
                        stockList.set(position, stock);
                        notifyDataSetChanged();
                    }
                });
                stockUpdateDialogFragment.show(((StockListActivity) context).getSupportFragmentManager(), Config.UPDATE_STOCK);
            }
        });
    }

    private void deleteStock(int position) {
        Stock stock = stockList.get(position);
        long count = databaseQueryClass.deleteStockByRegNum(stock.getBarcode());

        if(count>0){
            stockList.remove(position);
            notifyDataSetChanged();
            ((StockListActivity) context).viewVisibility();
            Toast.makeText(context, "Stock deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Stock not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }
}
