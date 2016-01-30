package com.aindong.restoko.models;

import com.aindong.restoko.library.Model;

public class CartItem extends Model {
    public int id;
    public Product product;
    public int quantity;

    public CartItem(int id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public void addQuantity(int qty) {
        this.quantity += qty;
    }

    public void subtractQuantity(int qty) {
        if (this.quantity <= 1) {
            return;
        }

        this.quantity -= qty;
    }
}
