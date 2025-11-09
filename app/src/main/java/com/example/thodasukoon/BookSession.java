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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookSession extends Fragment {

    private static final String TAG = "BookSession";
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_session, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        fetchLocationAndDoctors();
    }

    private void fetchLocationAndDoctors() {
        // Check if location permission is granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permissions are handled in MainActivity, but as a safeguard:
            Toast.makeText(getContext(), "Location permission not granted.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Fetch the last known location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(requireActivity(), location -> {
                    if (location != null) {
                        // Location found, now fetch doctors
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        getNearbyDoctors(latitude, longitude);
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
        // Get Retrofit client instance
        ApiService apiService = ApiClient.getClient(token).create(ApiService.class);

        LocationRequestBody requestBody = new LocationRequestBody(lat, lng);


        // Make the network call
        // In BookSession.java, inside the getNearbyDoctors method

// Change the Call type
        Call<DoctorResponse> call = apiService.getDoctorsByLocation(requestBody);

        call.enqueue(new Callback<DoctorResponse>() { // <-- Change the type here
            @Override
            public void onResponse(Call<DoctorResponse> call, Response<DoctorResponse> response) { // <-- And here
                if (response.isSuccessful() && response.body() != null) {
                    // Get the list of doctors FROM the response object
                    List<Doctor> doctors = response.body().getDoctors();

                    if (doctors != null) {
                        // TODO: Update your UI here. For example, display doctors in a RecyclerView.
                        Toast.makeText(getContext(), "Found " + doctors.size() + " doctors nearby.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Doctors found: " + doctors.size());
                    } else {
                        Toast.makeText(getContext(), "No doctors found in the response.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Doctors list was null in the response body.");
                    }

                } else {
                    // Handle API errors (e.g., 404, 500)
                    Toast.makeText(getContext(), "Failed to fetch doctors: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "API Error: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DoctorResponse> call, Throwable t) { // <-- Change the type here
                // Handle network failure (e.g., no internet)
                Toast.makeText(getContext(), "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Network Failure", t);
            }
        });

    }
}
