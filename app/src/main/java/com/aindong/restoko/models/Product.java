package com.aindong.restoko.models;

import com.aindong.restoko.library.Model;

public class Product extends Model {
    public int id;
    public String name;
    public String slug;
    public String description;
    public String image_url;
    public double amount;
    public int category_id;

    public Product()
    {
        // Do nothing
    }

    /**
     * Constructor when creating a new product object
     *
     * @param id
     * @param name
     * @param slug
     * @param description
     * @param image_url
     * @param amount
     * @param category_id
     */
    public Product(int id, String name, String slug, String description, String image_url,
                   double amount, int category_id) {

        // Assign parameters
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.image_url = image_url;
        this.amount = amount;
        this.category_id = category_id;
    }
}
