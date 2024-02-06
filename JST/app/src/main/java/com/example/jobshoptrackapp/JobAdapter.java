package com.example.jobshoptrackapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//Implements Filterable before {}
public class JobAdapter extends ListAdapter<Job, JobAdapter.JobHolder> {
    private List<Job> exampleList;
    private List<Job> exampleListFull;

    //private List<Job> jobs = new ArrayList<>();
    private OnItemClickListener listener;

    public JobAdapter() {
        super(DIFF_CALLBACK);
        //this.exampleList = exampleList;
        //exampleListFull = new ArrayList<>(exampleList);

    }

    private static final DiffUtil.ItemCallback<Job> DIFF_CALLBACK = new DiffUtil.ItemCallback<Job>() {
        @Override
        public boolean areItemsTheSame(@NonNull Job oldItem, @NonNull Job newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Job oldItem, @NonNull Job newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);
        return new JobHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobHolder holder, int position) {
        Job currentJob = getItem(position);
        holder.textViewTitle.setText(currentJob.getTitle());
        holder.textViewDescription.setText(currentJob.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentJob.getPriority()));

    }

    public Job getJobAt(int position) {
        return getItem(position);
    }

    class JobHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public JobHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            //Get job from position based on click listener passing positon to listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    // Interface - no implementation, just declare
    public interface OnItemClickListener {
        void onItemClick(Job job);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /*
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Job> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                //Iterate through items to determine if search match and if so, add to filter list
                for (Job job : exampleListFull){
                    if (job.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(job);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

     */
}
