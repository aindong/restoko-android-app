package com.aindong.restoko;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aindong.restoko.faker.FakeData;
import com.aindong.restoko.models.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    public static List<Table> tables;
    private TablesAdapter tablesAdapter;

    public TableFragment() {
        // Required empty public constructor
        tables = new ArrayList<Table>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tablesAdapter = new TablesAdapter(getActivity(), tables);

        GridView tablesGrid = (GridView) view.findViewById(R.id.gridview_tables);
        tablesGrid.setAdapter(tablesAdapter);

        FetchTablesTask tablesTask = new FetchTablesTask();
        tablesTask.execute();
    }

    public class FetchTablesTask extends AsyncTask<Void, String, List<Table>> {
        private final String LOG_TAG = FetchTablesTask.class.getSimpleName();

        protected List<Table> doInBackground(Void... params) {
            Table table = new Table();

            List<Table> tables = new ArrayList<Table>();

            String json = table.fetch("tables");

            try {
                int status = table.getStatus(json);
                JSONArray data = table.getData(json);

                // Make sure to clean the tables arraylist first
                tables.clear();
                // Iterate thru each jsonarray of tables and add them to the arraylist
                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.getJSONObject(i);

                    tables.add(new Table(obj.getInt("id"), obj.getString("name"), obj.getString("status")));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "ERROR PARSING JSON OBJECT");
            }

            return tables;
        }

        @Override
        protected void onPostExecute(List<Table> tables) {
            for (Table table : tables) {
                tablesAdapter.add(table);
            }
        }
    }

    public class TablesAdapter extends ArrayAdapter<Table> {

        public TablesAdapter(Context context, List<Table> tables) {
            super(context, 0, tables);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Table table = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.table_item, parent, false);
            }

            // Lookup view for data population
            final Button tableName = (Button) convertView.findViewById(R.id.button_table);
            TextView tableStatus = (TextView) convertView.findViewById(R.id.text_table_status);

            // Assign values
            tableName.setText(table.name);
            tableStatus.setText(table.status);

            int textColor = R.color.colorPrimaryDarkRed;
            if (table.status == "available") {
                textColor = R.color.colorPrimaryDark;
                tableStatus.setBackgroundColor(getResources().getColor(textColor));
            }

            tableStatus.setBackgroundColor(getResources().getColor(textColor));

            // Click listener
            tableName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(getContext(), "Preparing " + tableName.getText() + "...", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    // Create a new thread for processing the table
                    // This is needed to not disrupt the main thread or ui thread from hanging
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);

                                Intent intent;
                                // Create a new intent for showing main activity
                                // only if the table status is available
                                if (table.status == "available") {
                                    intent = new Intent(getContext(), MainActivity.class);
                                    intent.putExtra("table", table.id);
                                } else {
                                    // Go directly to cart when table is occupied
                                    intent = new Intent(getContext(), CartActivity.class);
                                    intent.putExtra("table", table.id);
                                }

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
}
