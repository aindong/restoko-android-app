package com.aindong.restoko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

        int productId;
        if (bundle.containsKey("product")) {
            productId = bundle.getInt("product");
        }
    }
}
