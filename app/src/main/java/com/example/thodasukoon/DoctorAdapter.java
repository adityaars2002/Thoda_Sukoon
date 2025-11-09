package com.example.thodasukoon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList;
    private Context context;

    // Constructor to initialize the adapter with the list of doctors and the context
    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the doctor_info.xml layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_info, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        // Get the doctor at the current position
        Doctor doctor = doctorList.get(position);

        // Bind the data from the Doctor object to the views in the ViewHolder
        holder.name.setText(doctor.getName());
        holder.address.setText(doctor.getAddress());

        // Format and set the rating. Handle cases where rating might be 0.
        if (doctor.getRating() > 0) {
            holder.rating.setText(String.format(Locale.US, "%.1f", doctor.getRating()));
            holder.ratingLayout.setVisibility(View.VISIBLE);
        } else {
            // Hide the rating if it's not available
            holder.ratingLayout.setVisibility(View.GONE);
        }

        // --- Logic for displaying tags (you can customize this) ---
        // Clear previous tags and only show the first one for simplicity
        holder.tagsContainer.removeAllViews(); // Important for recycling views
        if (doctor.getTags() != null && !doctor.getTags().isEmpty()) {
            // Let's just display the first tag for now
            String firstTag = doctor.getTags().get(0);
            holder.tag1.setText(firstTag);
            holder.tag1.setVisibility(View.VISIBLE);
            holder.tag2.setVisibility(View.GONE); // Hide the second static tag
        } else {
            // Hide all tags if none are provided
            holder.tag1.setVisibility(View.GONE);
            holder.tag2.setVisibility(View.GONE);
        }

        // --- Set OnClickListener for the Book Session button ---
        holder.bookButton.setOnClickListener(v -> {
            // TODO: Implement your booking logic here.
            // For example, you can open a new Activity or Fragment and pass the doctor's details.
            // For now, let's show a Toast message.
            Intent intent = new Intent(context, BookAppointmentActivity.class);
            intent.putExtra("doctor_name", doctor.getName());
            intent.putExtra("doctor_address", doctor.getAddress());
            context.startActivity(intent);
        });

        // --- Set OnClickListener for the whole card to open Google Maps for directions ---
        holder.itemView.setOnClickListener(v -> {
            if (doctor.getDirectionsUrl() != null && !doctor.getDirectionsUrl().isEmpty()) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(doctor.getDirectionsUrl()));
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of doctors in the list
        return doctorList != null ? doctorList.size() : 0;
    }

    // This method allows updating the list of doctors from the fragment
    public void updateDoctors(List<Doctor> newDoctors) {
        this.doctorList.clear();
        this.doctorList.addAll(newDoctors);
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }


    /**
     * ViewHolder class to hold the views for each item.
     * This improves performance by avoiding repeated findViewById calls.
     */
    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, rating, tag1, tag2;
        LinearLayout ratingLayout, tagsContainer;
        Button bookButton;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doctor_name);
            address = itemView.findViewById(R.id.doctor_address);
            rating = itemView.findViewById(R.id.doctor_rating);
            ratingLayout = itemView.findViewById(R.id.rating_layout);
            tagsContainer = itemView.findViewById(R.id.tags_container);
            tag1 = itemView.findViewById(R.id.tag1);
            tag2 = itemView.findViewById(R.id.tag2);
            bookButton = itemView.findViewById(R.id.book_button);
        }
    }
}
