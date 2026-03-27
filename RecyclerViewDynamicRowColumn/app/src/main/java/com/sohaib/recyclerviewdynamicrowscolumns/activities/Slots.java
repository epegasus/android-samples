package com.sohaib.recyclerviewdynamicrowscolumns.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sohaib.recyclerviewdynamicrowscolumns.adapters.CustomAdapter_Slots;
import com.sohaib.recyclerviewdynamicrowscolumns.databinding.ActivitySlotsBinding;

import java.util.ArrayList;
import java.util.List;

public class Slots extends AppCompatActivity {

    private ActivitySlotsBinding binding;
    private List<Boolean> slotList;
    private int rows, columns;

    private void initializations() {
        slotList = new ArrayList<>();
        Intent intent = getIntent();
        rows = intent.getIntExtra("rows", 1);
        columns = intent.getIntExtra("columns", 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySlotsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializations();
        fillList();
    }

    private void fillList() {
        int size = rows * columns;
        for (int i = 0; i < size; i++) {
            slotList.add(false);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columns);
        binding.rvSlotsSlots.setLayoutManager(gridLayoutManager);
        CustomAdapter_Slots adapterSlots = new CustomAdapter_Slots(slotList);
        binding.rvSlotsSlots.setAdapter(adapterSlots);
    }
}