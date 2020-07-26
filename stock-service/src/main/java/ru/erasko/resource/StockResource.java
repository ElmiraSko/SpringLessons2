package ru.erasko.resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class StockResource {

    @RequestMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StockInfo getStockInfo(@PathVariable("productId") Long productId) {
        return new StockInfo(productId, new Random().nextInt(100) + 10);
    }
}
