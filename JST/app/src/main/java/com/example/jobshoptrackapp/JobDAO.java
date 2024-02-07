package com.example.jobshoptrackapp;

//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.Query;
//import android.arch.persistence.room.Update;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
//import java.util.concurrent.Flow;

@Dao
public interface JobDAO {

    @Insert
    void insert(Job job);

    @Update
    void update(Job job);

    @Delete
    void delete(Job job);

    @Query("DELETE FROM job_table")
    void deleteAllJobs();

    //Query to get all jobs from DB
    @Query("SELECT * FROM job_table ORDER BY priority DESC")
    LiveData<List<Job>> getAllJobs();


}
