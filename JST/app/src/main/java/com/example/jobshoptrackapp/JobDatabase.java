package com.example.jobshoptrackapp;

//import android.arch.persistence.db.SupportSQLiteDatabase;
//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.Room;
//import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//This is where database is setup, pulling from Job class (can add to array)
@Database(entities = {Job.class}, version = 1)
public abstract class JobDatabase extends RoomDatabase {

    private static JobDatabase instance;

    public abstract JobDAO jobDAO();

    //Create database
    public static synchronized JobDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    JobDatabase.class, "job_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    //Overrides on create database to start with some live DB sample data
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private JobDAO jobDAO;
        private PopulateDbAsyncTask(JobDatabase db){
            jobDAO = db.jobDAO();
        }

        //Sample data for first time table population/creation
        @Override
        protected Void doInBackground(Void... voids) {
            jobDAO.insert(new Job("Job Title 1", "Job Description 1", 1));
            jobDAO.insert(new Job("Job Title 2", "Job Description 2", 2));
            jobDAO.insert(new Job("Job Title 3", "Job Description 3", 3));

            return null;
        }
    }

}
