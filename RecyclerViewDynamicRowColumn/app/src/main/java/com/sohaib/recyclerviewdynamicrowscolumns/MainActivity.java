package com.sohaib.recyclerviewdynamicrowscolumns;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sohaib.recyclerviewdynamicrowscolumns.activities.Slots;
import com.sohaib.recyclerviewdynamicrowscolumns.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String rowsString, columnsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGenerateMain.setOnClickListener(view -> onGenerateClick());
    }

    private void onGenerateClick() {
        getValues();
        if (checkValidations()) {
            submitForm();
        }
    }

    private void getValues() {
        rowsString = String.valueOf(binding.etRowMain.getText()).trim();
        columnsString = String.valueOf(binding.etColumnMain.getText()).trim();
    }

    private boolean checkValidations() {
        if (!rowsString.isEmpty() && !columnsString.isEmpty())
            return true;
        else
            Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void submitForm() {
        int rows = Integer.parseInt(rowsString);
        int columns = Integer.parseInt(columnsString);
        Intent intent = new Intent(this, Slots.class);
        intent.putExtra("rows", rows);
        intent.putExtra("columns", columns);
        startActivity(intent);
    }
}