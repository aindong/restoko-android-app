package com.aindong.restoko;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aindong.restoko.common.view.SlidingTabLayout;
import com.aindong.restoko.models.Category;
import com.aindong.restoko.models.Product;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductMenuFragment extends Fragment {

    static final String LOG_TAG = "ProductMenuFragment";
    protected List<Category> categories;
    public ProductsAdapter mProductAdapter;

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
        createCategories();

        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.menu_viewpager);
        mViewPager.setAdapter(new MenuPagerAdapter(this.categories));
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }

    private void createCategories()
    {
        // Create a list of categories
        this.categories = new ArrayList<Category>();

        Category appetizers = new Category(1, "APPETIZERS", "appetizers");
        Category recommendations = new Category(2, "CHEF's RECOMMENDATIONS", "chefs-recommendations");
        Category gourmet = new Category(2, "GOURMET BURGERS PANINIS & QUESADILLA", "gourmet-burgers-paninis-and-quesadilla");
        Category pizzas = new Category(2, "PIZZA & SALADS", "pizza-and-salads");
        Category pasta = new Category(2, "PASTA", "pasta");
        Category studentMeals = new Category(5, "STUDENT MEALS", "student-meals");
        Category desserts = new Category(5, "DESSERTS", "desserts");
        Category drinks = new Category(4, "DRINKS", "drinks");


        // Add dummy products
        appetizers.addProduct(new Product(1, "Tex-Mex Nachos",
                "best-ever-jalapeno-poppers",
                "Platter of nacho chips topped with sautéed beef, cheese sauce and flavoured dressing (serves 2-3 people)",
                "http://i.imgur.com/a1GwO8A.jpg",
                250,
                appetizers.id));

        appetizers.addProduct(new Product(1, "Nachos Deluxe",
                "best-ever-jalapeno-poppers",
                "Crispy nacho chips topped with taco beef, jalapeno, salsa, olives and cheese",
                "https://i.imgur.com/rjoLxVH.jpg",
                145,
                appetizers.id));

        appetizers.addProduct(new Product(1, "Java Chicken Wings",
                "best-ever-jalapeno-poppers",
                "Deep fried 3 pieces chicken wings coated with BBQ sauce served with crudités in Java aioli",
                "https://i.imgur.com/l2VyrBL.jpg",
                140,
                appetizers.id));

        appetizers.addProduct(new Product(1, "Hors D’oeuvres Plate",
                "best-ever-jalapeno-poppers",
                "A platter of breaded and spicy calamari, buffalo wings, fish fillet, shrimp and potato fries (serves 3-4 people)",
                "http://i.imgur.com/a1GwO8A.jpg",
                650,
                appetizers.id));

        appetizers.addProduct(new Product(1, "Poutine",
                "best-ever-jalapeno-poppers",
                "Potato fries, cheese and gravy.",
                "http://i.imgur.com/a1GwO8A.jpg",
                100,
                appetizers.id));


        // CHEFS RECOMMENDATIONS
        recommendations.addProduct(new Product(1, "Surf and Turf",
                "surf-and-turf",
                "Grilled “Angus” Salisbury steak and breaded shrimp served with Java gravy, green salad and rice.",
                "https://i.imgur.com/Blt6mYj.jpg",
                280,
                recommendations.id));

        recommendations.addProduct(new Product(1, "Grilled Pork Steak",
                "surf-and-turf",
                "Marinated pork steak grilled to perfection served with buttered vegetables, rice and Java gravy",
                "https://i.imgur.com/WF65HQw.png",
                280,
                recommendations.id));

        recommendations.addProduct(new Product(1, "Herb and Nut Crusted Fish Fillet",
                "surf-and-turf",
                "Crispy pan-fried fish fillet coated with herbs and nuts served with green salad and rice",
                "https://i.imgur.com/WF65HQw.png",
                190,
                recommendations.id));

        // PIZZA AND SALADS
        pizzas.addProduct(new Product(1, "Java Garden Salad",
                "surf-and-turf",
                "Lettuce, tomato, turnips, carrots, cucumber, bell peppers served with honey orange vinaigrette.",
                "https://i.imgur.com/T8K88Yq.jpg",
                115,
                pizzas.id));

        pizzas.addProduct(new Product(1, "Pizza Margherita",
                "surf-and-turf",
                "Homemade pizza with Java tomato sauce.",
                "https://i.imgur.com/NQeYwNu.jpg",
                155,
                pizzas.id));

        pizzas.addProduct(new Product(1, "Java Caesar Salad",
                "surf-and-turf",
                "Our version of Caesar salad, lettuce, bacon, croutons, parmesan cheese served with homemade Caesar dressing",
                "https://i.imgur.com/jZhvUwy.jpg",
                155,
                pizzas.id));



        // Categories into the list
        this.categories.add(appetizers);
        this.categories.add(pizzas);
        this.categories.add(recommendations);
        this.categories.add(gourmet);
        this.categories.add(studentMeals);
        this.categories.add(pasta);
        this.categories.add(desserts);
        this.categories.add(drinks);
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
            Product product = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_product, parent, false);
            }

            // Lookup view for data population
            TextView productName = (TextView) convertView.findViewById(R.id.text_product_name);
            TextView productAmount = (TextView) convertView.findViewById(R.id.text_product_amount);
            ImageView productImage = (ImageView) convertView.findViewById(R.id.image_product);

            // Assign values
            productName.setText(product.name);
            productAmount.setText(Double.toString(product.amount));
            Picasso.with(getContext()).load(product.image_url).placeholder(R.drawable.food_placeholder).into(productImage);

            // Return the completed view to render on screen
            return convertView;
        }
    }
}
