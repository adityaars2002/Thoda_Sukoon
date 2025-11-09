package com.example.thodasukoon;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView; // Import RecyclerView

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookSession extends Fragment {

    private static final String TAG = "BookSession";
    private FusedLocationProviderClient fusedLocationClient;

    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_session, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.doctorlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter with an empty list first
        doctorAdapter = new DoctorAdapter(requireContext(), doctorList);
        recyclerView.setAdapter(doctorAdapter);

        // Location and data fetching logic
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        fetchLocationAndDoctors();
    }

    private void fetchLocationAndDoctors() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Location permission not granted.", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        getNearbyDoctors(location.getLatitude(), location.getLongitude());
                    } else {
                        Toast.makeText(getContext(), "Could not get location. Make sure location is enabled.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting location", e);
                    Toast.makeText(getContext(), "Failed to get location.", Toast.LENGTH_SHORT).show();
                });
    }

    private void getNearbyDoctors(double lat, double lng) {
        PrefManager prefManager = new PrefManager(requireContext());
        String token = prefManager.getToken();
        ApiService apiService = ApiClient.getClient(token).create(ApiService.class);
        LocationRequestBody requestBody = new LocationRequestBody(lat, lng);
        Call<DoctorResponse> call = apiService.getDoctorsByLocation(requestBody);

        call.enqueue(new Callback<DoctorResponse>() {
            @Override
            public void onResponse(Call<DoctorResponse> call, Response<DoctorResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Doctor> fetchedDoctors = response.body().getDoctors();

                    if (fetchedDoctors != null && !fetchedDoctors.isEmpty()) {
                        // Use the adapter's update method
                        doctorAdapter.updateDoctors(fetchedDoctors);
                        Log.d(TAG, "Doctors found and UI updated: " + fetchedDoctors.size());
                    } else {
                        Toast.makeText(getContext(), "No doctors found nearby.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch doctors: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
