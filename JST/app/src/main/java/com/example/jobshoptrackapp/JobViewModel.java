package com.example.jobshoptrackapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class JobViewModel extends AndroidViewModel {
    private JobRepository repository;
    private LiveData<List<Job>> allJobs;
    private JobDAO jobDAO;
    private LiveData<List<Job>> searchResults;


    public JobViewModel(@NonNull Application application) {
        super(application);
        repository = new JobRepository(application);
        allJobs = repository.getAllJobs();
        JobDatabase db = JobDatabase.getInstance(application);
        jobDAO = db.jobDAO();
        searchResults = new MutableLiveData<>();
    }

    public LiveData<List<Job>> getSearchResults(){
        return searchResults;
    }

    public void searchEntities(String query){
        searchResults = (LiveData<List<Job>>) jobDAO.searchEntities("%" + query + "%");
    }

    public void insert(Job job){
        repository.insert(job);
    }
    public void update(Job job){
        repository.update(job);
    }
    public void delete(Job job){
        repository.delete(job);
    }
    public void deleteAllJobs(){
        repository.deleteAllJobs();
    }

    //public searchDatabase(searchQuery: String): LiveData<List<Job>>{
    //    return repository.searchDatabase(searchQuery).asLiveData();
    //}

    public LiveData<List<Job>> getAllJobs() {
        return allJobs;
    }
}
