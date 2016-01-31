package com.aindong.restoko.faker;

import android.util.Log;

import com.aindong.restoko.models.Cart;
import com.aindong.restoko.models.Category;
import com.aindong.restoko.models.Product;
import com.aindong.restoko.models.Table;

import java.util.ArrayList;
import java.util.List;

public class FakeData {
    public static List<Category> categories;
    public static List<Product> products;
    public static List<Table> tables;
    public static List<Cart> carts;

    public static void make() {
        //categories = new ArrayList<Category>();
        //products = new ArrayList<Product>();
        //tables = new ArrayList<Table>();
        carts = new ArrayList<Cart>();

        //createTables();
        //createCategoriesAndProducts();
    }

    public static Product getProductById(int productId)
    {
        // Iterate each product on the list
        for (int i = 0; i < products.size(); i++) {
            Product tmpProduct = products.get(i);

            // Check if product id is same with the current item
            if (tmpProduct.id == productId) {
                return tmpProduct;
            }
        }

        return null;
    }

    public static Cart getCartByTableId(int tableId)
    {
        // Iterate each product on the list
        for (int i = 0; i < carts.size(); i++) {
            Cart cart = carts.get(i);

            // Check if product id is same with the current item
            if (cart.table_id == tableId) {
                return cart;
            }
        }

        return null;
    }

    private static void getTables() {

    }

    private static void createTables() {
        tables.add(new Table(1, "Table R1", "available"));
        tables.add(new Table(2, "Table R2", "available"));
        tables.add(new Table(3, "Table R3", "available"));
        tables.add(new Table(4, "Table R4", "available"));
        tables.add(new Table(5, "Table R5", "available"));
        tables.add(new Table(6, "Table R6", "available"));
        tables.add(new Table(7, "Table R7", "available"));
        tables.add(new Table(8, "Table R8", "available"));
        tables.add(new Table(9, "Table R9", "occupied"));
        tables.add(new Table(10, "Table R10", "available"));
        tables.add(new Table(11, "Table B1", "available"));
        tables.add(new Table(12, "Table B2", "available"));
        tables.add(new Table(13, "Table B3", "occupied"));
        tables.add(new Table(14, "Table B4", "available"));
        tables.add(new Table(15, "Table B5", "occupied"));
    }

    private static void createCategoriesAndProducts() {
        Category appetizers = new Category(1, "APPETIZERS", "appetizers");
        Category recommendations = new Category(2, "CHEF's RECOMMENDATIONS", "chefs-recommendations");
        Category gourmet = new Category(3, "GOURMET BURGERS PANINIS & QUESADILLA", "gourmet-burgers-paninis-and-quesadilla");
        Category pizzas = new Category(4, "PIZZA & SALADS", "pizza-and-salads");
        Category pasta = new Category(5, "PASTA", "pasta");
        Category studentMeals = new Category(6, "STUDENT MEALS", "student-meals");
        Category desserts = new Category(7, "DESSERTS", "desserts");
        Category drinks = new Category(8, "DRINKS", "drinks");


        // Add dummy products
        appetizers.addProduct(new Product(1, "Tex-Mex Nachos",
                "best-ever-jalapeno-poppers",
                "Platter of nacho chips topped with sautéed beef, cheese sauce and flavoured dressing (serves 2-3 people)",
                "http://i.imgur.com/a1GwO8A.jpg",
                250,
                appetizers.id));

        appetizers.addProduct(new Product(2, "Nachos Deluxe",
                "best-ever-jalapeno-poppers",
                "Crispy nacho chips topped with taco beef, jalapeno, salsa, olives and cheese",
                "https://i.imgur.com/rjoLxVH.jpg",
                145,
                appetizers.id));

        appetizers.addProduct(new Product(3, "Java Chicken Wings",
                "best-ever-jalapeno-poppers",
                "Deep fried 3 pieces chicken wings coated with BBQ sauce served with crudités in Java aioli",
                "https://i.imgur.com/l2VyrBL.jpg",
                140,
                appetizers.id));

        appetizers.addProduct(new Product(4, "Hors D’oeuvres Plate",
                "best-ever-jalapeno-poppers",
                "A platter of breaded and spicy calamari, buffalo wings, fish fillet, shrimp and potato fries (serves 3-4 people)",
                "http://i.imgur.com/a1GwO8A.jpg",
                650,
                appetizers.id));

        appetizers.addProduct(new Product(5, "Poutine",
                "best-ever-jalapeno-poppers",
                "Potato fries, cheese and gravy.",
                "http://i.imgur.com/a1GwO8A.jpg",
                100,
                appetizers.id));


        // CHEFS RECOMMENDATIONS
        recommendations.addProduct(new Product(6, "Surf and Turf",
                "surf-and-turf",
                "Grilled “Angus” Salisbury steak and breaded shrimp served with Java gravy, green salad and rice.",
                "https://i.imgur.com/Blt6mYj.jpg",
                280,
                recommendations.id));

        recommendations.addProduct(new Product(7, "Grilled Pork Steak",
                "surf-and-turf",
                "Marinated pork steak grilled to perfection served with buttered vegetables, rice and Java gravy",
                "https://i.imgur.com/WF65HQw.png",
                280,
                recommendations.id));

        recommendations.addProduct(new Product(8, "Herb and Nut Crusted Fish Fillet",
                "surf-and-turf",
                "Crispy pan-fried fish fillet coated with herbs and nuts served with green salad and rice",
                "https://i.imgur.com/WF65HQw.png",
                190,
                recommendations.id));

        // PIZZA AND SALADS
        pizzas.addProduct(new Product(9, "Java Garden Salad",
                "surf-and-turf",
                "Lettuce, tomato, turnips, carrots, cucumber, bell peppers served with honey orange vinaigrette.",
                "https://i.imgur.com/T8K88Yq.jpg",
                115,
                pizzas.id));

        pizzas.addProduct(new Product(10, "Pizza Margherita",
                "surf-and-turf",
                "Homemade pizza with Java tomato sauce.",
                "https://i.imgur.com/NQeYwNu.jpg",
                155,
                pizzas.id));

        pizzas.addProduct(new Product(11, "Java Caesar Salad",
                "surf-and-turf",
                "Our version of Caesar salad, lettuce, bacon, croutons, parmesan cheese served with homemade Caesar dressing",
                "https://i.imgur.com/jZhvUwy.jpg",
                155,
                pizzas.id));


        // Categories into the list
        categories.add(appetizers);
        categories.add(pizzas);
        categories.add(recommendations);
        categories.add(gourmet);
        categories.add(studentMeals);
        categories.add(pasta);
        categories.add(desserts);
        categories.add(drinks);
    }
}
