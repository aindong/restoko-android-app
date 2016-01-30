package com.aindong.restoko.models;

import com.aindong.restoko.faker.FakeData;

import java.util.ArrayList;
import java.util.List;
import com.aindong.restoko.library.Model;

public class Category extends Model {
    // Id property of the category
    public int id;

    // Name property of the category
    public String name;

    // Slug property of the category
    public String slug;

    // List of Products inside the category
    public List<Product> products;

    public Category() {
        // Do nothing
    }

    /**
     * Constructor when creating a new category
     *
     * @param id
     * @param name
     * @param slug
     */
    public Category(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;

        products = new ArrayList<Product>();
    }

    /**
     * Set the products of this category
     *
     * @param products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Add product to list
     *
     * @param product
     */
    public void addProduct(Product product) {
        // This product does not belong here
        if (product.category_id != this.id) {
            return;
        }

        //FakeData.products.add(product);
        this.products.add(product);
    }
}