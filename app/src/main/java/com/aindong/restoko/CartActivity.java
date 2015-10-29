package com.aindong.restoko;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aindong.restoko.faker.FakeData;
import com.aindong.restoko.models.Cart;
import com.aindong.restoko.models.CartItem;
import com.aindong.restoko.models.Product;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    public Cart cart;
    public TextView vatable;
    public TextView vat;
    public TextView discount;
    public TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Get extras
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            return;
        }

        final int tableId = bundle.getInt("table");

        // Check if there's a cart already for table
        cart = FakeData.getCartByTableId(tableId);
        if (cart == null) {
            cart = new Cart(FakeData.carts.size() + 1, tableId);
            // Save to global carts
            FakeData.carts.add(cart);
        }

        // Add to cart
        int productId;
        if (bundle.containsKey("product")) {
            productId = bundle.getInt("product");

            // Get product
            Product product = FakeData.getProductById(productId);

            // Create a cartItem and attach product
            CartItem item = new CartItem(cart.items.size() + 1, product, 1);
            cart.addProduct(item);
        }

        vatable = (TextView)  findViewById(R.id.text_vatable);
        vat = (TextView) findViewById(R.id.text_vat);
        discount = (TextView) findViewById(R.id.text_discount);
        totalPrice = (TextView) findViewById(R.id.text_total_price);

        // Adapter
        ListView listView = (ListView) findViewById(R.id.listview_cart);
        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(getApplicationContext(), cart.items);
        listView.setAdapter(cartItemsAdapter);

        Button addProduct = (Button) findViewById(R.id.button_add_product);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("table", tableId);

                            startActivity(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // Start the thread worker
                thread.start();
            }
        });
    }

    public class CartItemsAdapter extends ArrayAdapter<CartItem> {

        public CartItemsAdapter(Context context, List<CartItem> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final CartItem item = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_cart, parent, false);
            }

            // Lookup view for data population
            TextView productName = (TextView) convertView.findViewById(R.id.text_product_name);
            TextView productAmount = (TextView) convertView.findViewById(R.id.text_product_amount);
            final TextView productPrice = (TextView) convertView.findViewById(R.id.text_individual_total);
            final TextView qty = (TextView) convertView.findViewById(R.id.text_product_quantity);

            ImageView productImage = (ImageView) convertView.findViewById(R.id.image_product);
            ImageButton addQty = (ImageButton) convertView.findViewById(R.id.add_cart_item);
            ImageButton subQty = (ImageButton) convertView.findViewById(R.id.subtract_cart_item);

            // Assign values
            productName.setText(item.product.name);
            productAmount.setText(Double.toString(item.product.amount));
            productPrice.setText(Double.toString(item.product.amount * item.quantity));
            Picasso.with(getContext()).load(item.product.image_url).placeholder(R.drawable.food_placeholder).into(productImage);
            qty.setText(Integer.toString(item.quantity));

            vatable.setText(Double.toString(cart.calculateTotalPrice()));
            vat.setText(Double.toString(cart.calculateVat()));
            discount.setText(Double.toString(cart.calculateDiscount(0)));
            totalPrice.setText(Double.toString(cart.calculateGrandTotal()));

            // Add quantity
            addQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.addQuantity(1);
                    qty.setText(Integer.toString(item.quantity));
                    productPrice.setText(Double.toString(item.product.amount * item.quantity));

                    vatable.setText(Double.toString(cart.calculateTotalPrice()));
                    vat.setText(Double.toString(cart.calculateVat()));
                    discount.setText(Double.toString(cart.calculateDiscount(0)));
                    totalPrice.setText(Double.toString(cart.calculateGrandTotal()));
                }
            });

            // Subtract quantity
            subQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.subtractQuantity(1);
                    qty.setText(Integer.toString(item.quantity));
                    productPrice.setText(Double.toString(item.product.amount * item.quantity));

                    vatable.setText(Double.toString(cart.calculateTotalPrice()));
                    vat.setText(Double.toString(cart.calculateVat()));
                    discount.setText(Double.toString(cart.calculateDiscount(0)));
                    totalPrice.setText(Double.toString(cart.calculateGrandTotal()));
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
