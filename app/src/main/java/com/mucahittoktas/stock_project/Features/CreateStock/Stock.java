package com.mucahittoktas.stock_project.Features.CreateStock;

public class Stock {
    private long id;
    private String name;
    private long barcode;
    private String quantity;
    private String purchase_price;
    private String sell_price;

    public Stock(int id, String name, long barcode, String quantity, String purchase_price,String sell_price) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.quantity = quantity;
        this.purchase_price = purchase_price;
        this.sell_price = sell_price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }


    public String getSell_price() { return sell_price; }

    public void setSell_price(String sell_price) { this.sell_price = sell_price; }


}
