package cn.xpbootcamp.legacy_code.entity;

public class Order {

    private User buyer;
    private User seller;
    private Product product;


    public Order(User buyer, User seller, Product product) {
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
    }

    public User getBuyer() {
        return buyer;
    }

    public User getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }
}