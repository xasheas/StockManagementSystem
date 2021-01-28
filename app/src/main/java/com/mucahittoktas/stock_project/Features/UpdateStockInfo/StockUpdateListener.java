package com.mucahittoktas.stock_project.Features.UpdateStockInfo;

import com.mucahittoktas.stock_project.Features.CreateStock.Stock;

public interface StockUpdateListener {
    void onStockInfoUpdated(Stock stock, int position);
}
