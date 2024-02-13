package com.example.jobshoptrackapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Report extends AppCompatActivity {
    List<Job> jobsByPriority;
    private JobViewModel jobViewModel;
    private RecyclerView recyclerView;
    private JobAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JobAdapter();
        recyclerView.setAdapter(adapter);

        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);

        EditText searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                jobViewModel.searchEntities(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        jobViewModel.getSearchResults().observe(this, jobs -> {
            // UPDATE UI WITH SEARCH RESULTS
            adapter.setJobs(jobs);
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //***ADD DATA BELOW FOR REPORT***
        //Test with get all jobs by priority??


    }


}