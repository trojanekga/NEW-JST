package com.example.jobshoptrackapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Distinguish between add and edit
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private JobViewModel jobViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddJob = findViewById(R.id.button_add_job);
        buttonAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditJobActivity.class);
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
                adapter.submitList(jobs);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                jobViewModel.delete(adapter.getJobAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Job deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new JobAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Job job) {
                //Open edit activity based on clicked job
                Intent intent = new Intent(MainActivity.this, AddEditJobActivity.class);
                //Sending values over to edit screen
                intent.putExtra(AddEditJobActivity.EXTRA_ID, job.getId());
                intent.putExtra(AddEditJobActivity.EXTRA_TITLE, job.getTitle());
                intent.putExtra(AddEditJobActivity.EXTRA_DESCRIPTION, job.getDescription());
                intent.putExtra(AddEditJobActivity.EXTRA_PRIORITY, job.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditJobActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditJobActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditJobActivity.EXTRA_PRIORITY, 1);

            Job job = new Job(title, description, priority);
            jobViewModel.insert(job);

            Toast.makeText(this, "Job saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditJobActivity.EXTRA_ID, -1);
            
            if (id == -1){
                Toast.makeText(this, "Job can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditJobActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditJobActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditJobActivity.EXTRA_PRIORITY, 1);
            
            Job job = new  Job(title, description, priority);
            job.setId(id);
            jobViewModel.update(job);

            Toast.makeText(this, "Job updated", Toast.LENGTH_SHORT).show();
            
        }else {
            Toast.makeText(this, "Job not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_jobs:
                jobViewModel.deleteAllJobs();
                Toast.makeText(this, "All jobs deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}