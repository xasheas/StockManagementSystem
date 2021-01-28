package com.mucahittoktas.stock_project.Features.ShowStockList;

import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mucahittoktas.stock_project.Database.DatabaseQueryClass;
import com.mucahittoktas.stock_project.Features.CreateStock.Stock;
import com.mucahittoktas.stock_project.Features.CreateStock.StockCreateDialogFragment;
import com.mucahittoktas.stock_project.Features.CreateStock.StockCreateListener;
import com.mucahittoktas.stock_project.R;
import com.mucahittoktas.stock_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class StockListActivity extends AppCompatActivity implements StockCreateListener {

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Stock> stockList = new ArrayList<>();

    private TextView stockListEmptyTextView;
    private RecyclerView recyclerView;
    private StockListRecyclerViewAdapter stockListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.stockRecyclerView);
        stockListEmptyTextView = (TextView) findViewById(R.id.emptyStockListTextView);

        stockList.addAll(databaseQueryClass.getAllStock());

        stockListRecyclerViewAdapter = new StockListRecyclerViewAdapter(this, stockList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(stockListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStockCreateDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, You wanted to delete all stocks?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllStock();
                            if(isAllDeleted){
                                stockList.clear();
                                stockListRecyclerViewAdapter.notifyDataSetChanged();
                                viewVisibility();
                            }
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

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(stockList.isEmpty())
            stockListEmptyTextView.setVisibility(View.VISIBLE);
        else
            stockListEmptyTextView.setVisibility(View.GONE);
    }

    private void openStockCreateDialog() {
        StockCreateDialogFragment stockCreateDialogFragment = StockCreateDialogFragment.newInstance("Create Stock", this);
        stockCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_STOCK);
    }

    @Override
    public void onStockCreated(Stock stock) {
        stockList.add(stock);
        stockListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(stock.getName());
    }


}
