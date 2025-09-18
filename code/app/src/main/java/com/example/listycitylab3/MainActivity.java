package com.example.listycitylab3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> dataList;
    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private Button addCityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Moscow",
                "Sydney", "Berlin", "Vienna",
                "Tokyo", "Beijing", "Osaka", "New Delhi"
        };

        // type instances
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityList = findViewById(R.id.city_list);
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        addCityBtn = findViewById(R.id.add_city_btn);

        // Add new city
        addCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCityDialog();
            }
        });

        // Edit/delete a city on tap
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            showEditDeleteDialog(position);
        });
    }

    private void showAddCityDialog() {
        final EditText input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New City");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String newCity = input.getText().toString().trim();
            if (!newCity.isEmpty()) {
                dataList.add(newCity);
                cityAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // pop up when click on a city
    private void showEditDeleteDialog(int position) {
        final String cityName = dataList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this); //dialog box
        // dialog box text
        builder.setTitle("Modify City");
        builder.setMessage("What do you want to do with " + cityName + "?");
        //dialog box buttons
        builder.setPositiveButton("Edit", (dialog, which) -> showEditCityDialog(position));
        builder.setNeutralButton("Delete", (dialog, which) -> {
            dataList.remove(position);
            cityAdapter.notifyDataSetChanged();
        });
        // close button
        builder.setNegativeButton("Cancel", null);

        builder.show();
    }
    // dialog box when editing city name
    private void showEditCityDialog(int position) {
        final EditText input = new EditText(this);
        input.setText(dataList.get(position));
        // dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //dialog textbox
        builder.setTitle("Edit City");
        builder.setView(input);
        // save new name
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = input.getText().toString().trim(); // clean inpttxt
            if (!newName.isEmpty()) {
                dataList.set(position, newName);
                cityAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
