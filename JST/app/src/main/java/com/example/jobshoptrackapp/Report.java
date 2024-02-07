package com.example.jobshoptrackapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Report extends AppCompatActivity {
    List<Job> jobsByPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //***ADD DATA BELOW FOR REPORT***
        //Test with get all jobs by priority??


    }


}