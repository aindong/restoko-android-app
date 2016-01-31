package com.aindong.restoko;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aindong.restoko.common.view.SlidingTabLayout;
import com.aindong.restoko.faker.FakeData;
import com.aindong.restoko.models.Category;
import com.aindong.restoko.models.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductMenuFragment extends Fragment {

    static final String LOG_TAG = ProductMenuFragment.class.getSimpleName();
    protected List<Category> categories;
    public ProductsAdapter mProductAdapter;
    public int tableId;

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;


    public ProductMenuFragment() {
        // Required empty public constructor
        categories = new ArrayList<Category>();
    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);

        this.tableId = bundle.getInt("table");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.menu_viewpager);
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);

        FetchCategoriesTask categoriesTask = new FetchCategoriesTask();
        categoriesTask.execute();
    }

    class MenuPagerAdapter extends PagerAdapter {

        private List<Category> categories;

        public MenuPagerAdapter(List<Category> categories) {
            this.categories = categories;
        }

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return categories.size();
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Category category = categories.get(position);

            return category.name;
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
                    container, false);
            // Add the newly created View to the ViewPager
            container.addView(view);

            // Manage product list
            configureProductListview(position, view);

            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

            // Return the View
            return view;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }

        /**
         * Manage product listing on listview
         * @param position
         * @param view
         */
        private void configureProductListview(int position, View view)
        {
            List<Product> products = this.categories.get(position).products;

            // Instantiate productadapter
            mProductAdapter = new ProductsAdapter(getContext(), products);

            // Find listview for reference
            ListView listView = (ListView) view.findViewById(R.id.listview_products);
            // Assign arrayadapter to listview
            listView.setAdapter(mProductAdapter);
        }

    }

    public class ProductsAdapter extends ArrayAdapter<Product> {

        public ProductsAdapter(Context context, List<Product> categories) {
            super(context, 0, categories);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Product product = getItem(position);

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
            productName.setText(product.name);
            productAmount.setText(Double.toString(product.amount));
            Picasso.with(getContext()).load(product.image_url).placeholder(R.drawable.food_placeholder).into(productImage);

            // Imageview onclick listener
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getContext(), "Adding " + product.name + " into cart...", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    // Create a new thread for processing the table
                    // This is needed to not disrupt the main thread or ui thread from hanging
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);

                                Intent intent = new Intent(getContext(), CartActivity.class);
                                intent.putExtra("product", product.id);
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

            // Return the completed view to render on screen
            return convertView;
        }
    }

    public class FetchCategoriesTask extends AsyncTask<Void, String, List<Category>> {
        private final String LOG_TAG = FetchCategoriesTask.class.getSimpleName();

        @Override
        protected List<Category> doInBackground(Void... params) {

            Category category = new Category();

            List<Category> categories = new ArrayList<Category>();

            String json = category.fetch("categories/products");

            try {
                int status = category.getStatus(json);
                JSONArray data = category.getData(json);

                // Make sure to clean the categories arraylist first
                categories.clear();
                // Iterate thru each jsonarray of categories and add them to the arraylist
                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.getJSONObject(i);
                    Category categoryData = new Category(obj.getInt("id"), obj.getString("name"), obj.getString("slug"));

                    // Get products
                    JSONArray products = obj.getJSONArray("products");
                    for (int j = 0; j < products.length(); j++) {
                        JSONObject productObj = products.getJSONObject(j);

                        Product product = new Product(
                                productObj.getInt("id"),
                                productObj.getString("name"),
                                productObj.getString("slug"),
                                productObj.getString("description"),
                                Product.API_URL + "media/" + productObj.getString("picture"),
                                productObj.getDouble("price"),
                                productObj.getInt("category_id")
                        );

                        categoryData.addProduct(product);
                    }

                    categories.add(categoryData);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "ERROR PARSING JSON OBJECT");
            }

            return categories;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            // BEGIN_INCLUDE (setup_viewpager)
            // Get the ViewPager and set it's PagerAdapter so that it can display items
            mViewPager.setAdapter(new MenuPagerAdapter(categories));
            // END_INCLUDE (setup_viewpager)

            // BEGIN_INCLUDE (setup_slidingtablayout)
            // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
            // it's PagerAdapter set.
            mSlidingTabLayout.setViewPager(mViewPager);
            // END_INCLUDE (setup_slidingtablayout)
        }
    }
}
