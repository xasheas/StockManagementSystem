package com.mucahittoktas.stock_project.Features.CreateStock;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mucahittoktas.stock_project.Util.Config;
import com.mucahittoktas.stock_project.Database.DatabaseQueryClass;
import com.mucahittoktas.stock_project.R;


public class StockCreateDialogFragment extends DialogFragment {

    private static StockCreateListener stockCreateListener;

    private EditText nameEditText;
    private EditText barcodeEditText;
    private EditText quantityEditText;
    private EditText purchase_priceEditText;
    private EditText sell_priceEditText;
    private Button createButton;
    private Button cancelButton;

    private String nameString = "";
    private long barcodeNumber = -1;
    private String quantityString = "";
    private String purchase_priceString = "";
    private String sell_priceString = "";

    public StockCreateDialogFragment() {
        // Required empty public constructor
    }

    public static StockCreateDialogFragment newInstance(String title, StockCreateListener listener){
        stockCreateListener = listener;
        StockCreateDialogFragment stockCreateDialogFragment = new StockCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        stockCreateDialogFragment.setArguments(args);

        stockCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return stockCreateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stock_create_dialog, container, false);

        nameEditText = view.findViewById(R.id.stockNameEditText);
        barcodeEditText = view.findViewById(R.id.barcodeEditText);
        quantityEditText = view.findViewById(R.id.quantityEditText);
        purchase_priceEditText = view.findViewById(R.id.purchase_priceEditText);
        sell_priceEditText = view.findViewById(R.id.sell_priceEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameString = nameEditText.getText().toString();
                barcodeNumber = Integer.parseInt(barcodeEditText.getText().toString());
                quantityString = quantityEditText.getText().toString();
                purchase_priceString = purchase_priceEditText.getText().toString();
                sell_priceString = sell_priceEditText.getText().toString();

                Stock stock = new Stock(-1, nameString, barcodeNumber, quantityString, purchase_priceString, sell_priceString);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                long id = databaseQueryClass.insertStock(stock);

                if(id>0){
                    stock.setId(id);
                    stockCreateListener.onStockCreated(stock);
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
