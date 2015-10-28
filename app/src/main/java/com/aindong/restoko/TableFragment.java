package com.aindong.restoko;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aindong.restoko.models.Table;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {


    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        List<Table> tables = new ArrayList<Table>();
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

        TablesAdapter tablesAdapter = new TablesAdapter(getActivity(), tables);

        GridView tablesGrid = (GridView) view.findViewById(R.id.gridview_tables);
        tablesGrid.setAdapter(tablesAdapter);
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
                                Thread.sleep(2000);

                                Intent intent;
                                // Create a new intent for showing main activity
                                // only if the table status is available
                                if (table.status == "available") {
                                    intent = new Intent(getContext(), MainActivity.class);
                                    intent.putExtra("table", table.id);
                                } else {
                                    // Go directly to cart when table is occupied
                                    intent = new Intent(getContext(), MainActivity.class);
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
