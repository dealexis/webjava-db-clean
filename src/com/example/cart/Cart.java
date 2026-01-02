package com.example.cart;

import com.example.models.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.increment();
                return;
            }
        }
        items.add(new CartItem(product, 1));
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public List<CartItem> getItems() {
        return items;
    }
}
