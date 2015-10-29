package com.aindong.restoko.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    public int id;
    public int table_id;
    public List<CartItem> items;

    private double totalPrice;

    /**
     * Create an empty cart
     *
     * @param id
     * @param table_id
     */
    public Cart(int id, int table_id) {
        this.id = id;
        this.table_id = table_id;
        this.items = new ArrayList<CartItem>();
    }

    /**
     * Create a cart with products in it
     *
     * @param id
     * @param table_id
     * @param products
     */
    public Cart(int id, int table_id, List<CartItem> products) {
        this.id = id;
        this.table_id = table_id;
        this.items = products;
    }

    public void addProduct(CartItem item) {

        // Check if product already exists
        if (searchProductOnList(item.product.id)) {
            return;
        }

        // Add product on the list
        this.items.add(item);
    }

    /**
     * Calculate the total price of items in the cart
     *
     * @return
     */
    public double calculateTotalPrice() {
        totalPrice = 0;

        // Iterate each product on the list
        for (int i = 0; i < this.items.size(); i++) {
            CartItem item = this.items.get(i);

            double price = item.product.amount * item.quantity;
            totalPrice += price;
        }

        return totalPrice;
    }

    public double calculateVat() {
        double vat = 0;

        if (totalPrice <= 0) {
            return 0;
        }

        vat = totalPrice * 0.12;

        return vat;
    }

    public double calculateDiscount(double discount) {
        discount = discount / 100;
        discount = totalPrice * discount;

        return discount;
    }

    public void applyDiscount(double discount) {
        discount = discount / 100;
        discount = totalPrice * discount;

        // Deduct the discount
        totalPrice = totalPrice - discount;
    }

    /**
     * Calculate for the grand price
     *
     * @return
     */
    public double calculateGrandTotal() {
        return totalPrice + calculateVat();
    }

    /**
     * Search a product on the list
     *
     * @param productId
     * @return
     */
    private boolean searchProductOnList(int productId) {
        boolean found = false;

        // Iterate each product on the list
        for (int i = 0; i < this.items.size(); i++) {
            CartItem item = this.items.get(i);

            // Check if product id is same with the current item
            if (item.product.id == productId) {
                found = true;
                break;
            }
        }

        return found;
    }
}
