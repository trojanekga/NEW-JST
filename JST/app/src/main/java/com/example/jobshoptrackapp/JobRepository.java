package com.example.jobshoptrackapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class JobRepository {
    private JobDAO jobDAO;
    private LiveData<List<Job>> allJobs;

    public JobRepository(Application application){
        JobDatabase database = JobDatabase.getInstance(application);
        jobDAO = database.jobDAO();
        allJobs = jobDAO.getAllJobs();
    }

    public void insert(Job job){
        new InsertJobAsyncTask(jobDAO).execute(job);
    }
    public void update(Job job){
        new UpdateJobAsyncTask(jobDAO).execute(job);
    }
    public void delete(Job job){
        new DeleteJobAsyncTask(jobDAO).execute(job);
    }
    public void deleteAllJobs(){
        new DeleteAllJobsAsyncTask(jobDAO).execute();
    }

    public LiveData<List<Job>> getAllJobs() {
        return allJobs;
    }

    private static class InsertJobAsyncTask extends AsyncTask<Job, Void, Void>{
        private JobDAO jobDAO;
        private InsertJobAsyncTask(JobDAO jobDAO){
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.insert(jobs[0]);
            return null;
        }
    }

    private static class UpdateJobAsyncTask extends AsyncTask<Job, Void, Void>{
        private JobDAO jobDAO;
        private UpdateJobAsyncTask(JobDAO jobDAO){
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.update(jobs[0]);
            return null;
        }
    }
    private static class DeleteJobAsyncTask extends AsyncTask<Job, Void, Void>{
        private JobDAO jobDAO;
        private DeleteJobAsyncTask(JobDAO jobDAO){
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.delete(jobs[0]);
            return null;
        }
    }
    private static class DeleteAllJobsAsyncTask extends AsyncTask<Void, Void, Void>{
        private JobDAO jobDAO;
        private DeleteAllJobsAsyncTask(JobDAO jobDAO){
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            jobDAO.deleteAllJobs();
            return null;
        }
    }
}
