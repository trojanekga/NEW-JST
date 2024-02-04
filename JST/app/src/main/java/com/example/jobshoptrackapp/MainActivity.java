package com.example.jobshoptrackapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;

    private JobViewModel jobViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddJob = findViewById(R.id.button_add_job);
        buttonAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddJobActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        JobAdapter adapter = new JobAdapter();
        recyclerView.setAdapter(adapter);

        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        jobViewModel.getAllJobs().observe(this, new Observer<List<Job>>() {
            @Override
            public void onChanged(@Nullable List<Job> jobs) {
                adapter.setJobs(jobs);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddJobActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddJobActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddJobActivity.EXTRA_PRIORITY, 1);

            Job job = new Job(title, description, priority);
            jobViewModel.insert(job);

            Toast.makeText(this, "Job saved", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Job not saved", Toast.LENGTH_SHORT).show();
        }
    }
}