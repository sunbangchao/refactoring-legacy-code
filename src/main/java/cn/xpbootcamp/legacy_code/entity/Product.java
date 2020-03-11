package cn.xpbootcamp.legacy_code.entity;

import javax.transaction.InvalidTransactionException;

public class Product {
    private String productId;
    private Double price;

    public Product(String productId, Double price) throws InvalidTransactionException {
        this.productId = productId;
        this.price = price;
        if(!checkPrice()) throw new InvalidTransactionException("This is an invalid product");
    }

    public String getProductId() {
        return productId;
    }

    public Double getPrice() {
        return price;
    }

    private boolean checkPrice(){
        return this.price >= 0;
    }
}
