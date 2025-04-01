package com.haydenshui.stock.lib.dto.stock;

import org.springframework.beans.BeanUtils;

import com.haydenshui.stock.lib.entity.stock.Stock;
import com.haydenshui.stock.lib.entity.stock.StockStatus;

public class StockMapper {
    
    private StockMapper() {}

    public static StockDTO toDTO(Stock stock) {
        if(stock == null)
            return null;
        StockDTO stockDTO = new StockDTO();
        BeanUtils.copyProperties(stock, stockDTO);
        stockDTO.setStatus(stock.getStatus() != null ? stock.getStatus().toString() : null);
        return stockDTO;
    }

    public static Stock toEntity(StockDTO stockDTO) {
        if(stockDTO == null)
            return null;
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockDTO, stock);
        stock.setStatus(StockStatus.fromString(stockDTO.getStatus()));
        return stock;
    }
}
