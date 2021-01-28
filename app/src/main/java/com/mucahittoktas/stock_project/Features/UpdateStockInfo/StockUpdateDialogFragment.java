package com.mucahittoktas.stock_project.Features.UpdateStockInfo;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mucahittoktas.stock_project.Database.DatabaseQueryClass;
import com.mucahittoktas.stock_project.Features.CreateStock.Stock;
import com.mucahittoktas.stock_project.R;
import com.mucahittoktas.stock_project.Util.Config;


public class StockUpdateDialogFragment extends DialogFragment {

    private static long stockRegNo;
    private static int stockItemPosition;
    private static StockUpdateListener stockUpdateListener;

    private Stock mStock;

    private EditText nameEditText;
    private EditText barcodeEditText;
    private EditText quantityEditText;
    private EditText purchase_priceEditText;
    private EditText sell_priceEditText;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";
    private long barcodeNumber = -1;
    private String quantityString = "";
    private String purchase_priceString = "";
    private String sell_priceString = "";

    private DatabaseQueryClass databaseQueryClass;

    public StockUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static StockUpdateDialogFragment newInstance(long barcodeNumber, int position, StockUpdateListener listener){
        stockRegNo = barcodeNumber;
        stockItemPosition = position;
        stockUpdateListener = listener;
        StockUpdateDialogFragment stockUpdateDialogFragment = new StockUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update stock information");
        stockUpdateDialogFragment.setArguments(args);

        stockUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return stockUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stock_update_dialog, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        nameEditText = view.findViewById(R.id.stockNameEditText);
        barcodeEditText = view.findViewById(R.id.barcodeEditText);
        quantityEditText = view.findViewById(R.id.quantityEditText);
        purchase_priceEditText = view.findViewById(R.id.purchase_priceEditText);
        sell_priceEditText= view.findViewById(R.id.sell_priceEditText);
        updateButton = view.findViewById(R.id.updateStockInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mStock = databaseQueryClass.getStockByRegNum(stockRegNo);

        if(mStock !=null){
            nameEditText.setText(mStock.getName());
            barcodeEditText.setText(String.valueOf(mStock.getBarcode()));
            quantityEditText.setText(mStock.getQuantity());
            purchase_priceEditText.setText(mStock.getPurchase_price());
            sell_priceEditText.setText(mStock.getSell_price());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = nameEditText.getText().toString();
                    barcodeNumber = Integer.parseInt(barcodeEditText.getText().toString());
                    quantityString = quantityEditText.getText().toString();
                    purchase_priceString = purchase_priceEditText.getText().toString();
                    sell_priceString = sell_priceEditText.getText().toString();

                    mStock.setName(nameString);
                    mStock.setBarcode(barcodeNumber);
                    mStock.setQuantity(quantityString);
                    mStock.setPurchase_price(purchase_priceString);
                    mStock.setSell_price(sell_priceString);

                    long id = databaseQueryClass.updateStockInfo(mStock);

                    if(id>0){
                        stockUpdateListener.onStockInfoUpdated(mStock, stockItemPosition);
                        getDialog().dismiss();
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
