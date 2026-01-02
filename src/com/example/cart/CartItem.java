package com.example.cart;

import com.example.models.Product;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        if (quantity == null) {
            quantity = 1;
        }
        this.quantity = quantity;
    }

    public void increment() {
        quantity++;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
