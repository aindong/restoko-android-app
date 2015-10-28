package com.aindong.restoko;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aindong.restoko.faker.FakeData;
import com.aindong.restoko.models.Cart;
import com.aindong.restoko.models.CartItem;
import com.aindong.restoko.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Get extras
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            return;
        }

        int tableId = bundle.getInt("table");

        // Check if there's a cart already for table
        Cart cart = FakeData.getCartByTableId(tableId);
        if (cart == null) {
            cart = new Cart(FakeData.carts.size() + 1, tableId);
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

        // Adapter
        ListView listView = (ListView) findViewById(R.id.listview_cart);
        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(getApplicationContext(), cart.items);
        listView.setAdapter(cartItemsAdapter);
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_product, parent, false);
            }

            // Lookup view for data population
            TextView productName = (TextView) convertView.findViewById(R.id.text_product_name);
            TextView productAmount = (TextView) convertView.findViewById(R.id.text_product_amount);
            ImageView productImage = (ImageView) convertView.findViewById(R.id.image_product);
            ImageButton btnAddToCart = (ImageButton) convertView.findViewById(R.id.add_to_cart);

            // Assign values
            productName.setText(item.product.name);
            productAmount.setText(Double.toString(item.product.amount));
            Picasso.with(getContext()).load(item.product.image_url).placeholder(R.drawable.food_placeholder).into(productImage);

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
