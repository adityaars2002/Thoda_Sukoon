package com.example.thodasukoon;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DoctorResponse {

    // The name "counsellors" must match the key in the JSON response
    @SerializedName("counsellors")
    private List<Doctor> doctors;

    // Getter method
    public List<Doctor> getDoctors() {
        return doctors;
    }

    // Setter method (optional but good practice)
    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}
