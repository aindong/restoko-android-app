package com.aindong.restoko.models;

public class Product {
    public int id;
    public String name;
    public String slug;
    public int category_id;

    /**
     * Constructor when creating a new product object
     *
     * @param id
     * @param name
     * @param slug
     * @param category_id
     */
    public Product(int id, String name, String slug, int category_id) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.category_id = category_id;
    }
}
