package com.aindong.restoko;

import android.app.Activity;
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
import android.widget.TextView;

import com.aindong.restoko.common.view.SlidingTabLayout;
import com.aindong.restoko.models.Category;

import java.util.ArrayList;
import java.util.List;

public class ProductMenuFragment extends Fragment {

    static final String LOG_TAG = "ProductMenuFragment";
    protected List<Category> categories;

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
        Category pizzas = new Category(2, "PIZZAS", "pizzas");
        Category salads = new Category(3, "SALADS", "salads");
        Category beverages = new Category(4, "BEVERAGES", "beverages");
        Category studentMeals = new Category(5, "STUDENT MEALS", "student-meals");
        Category riceMeals = new Category(6, "RICE MEALS", "rice-meals");
        Category sandwitches = new Category(7, "SANDWITCHES", "sandwitches");

        // Categories into the list
        this.categories.add(appetizers);
        this.categories.add(pizzas);
        this.categories.add(salads);
        this.categories.add(beverages);
        this.categories.add(studentMeals);
        this.categories.add(riceMeals);
        this.categories.add(sandwitches);
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

            // TODO: GET MENU ITEM HERE

            // Retrieve a TextView from the inflated View, and update it's text
            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));

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

    }
}
