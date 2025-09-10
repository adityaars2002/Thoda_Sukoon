package com.example.thodasukoon;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;


public class HomeFragment extends Fragment {







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_home, container, false);

        MaterialCardView findCouncelor = view.findViewById(R.id.findCouncelor);
        MaterialCardView btnQuickChat = view.findViewById(R.id.btnQuickChat);


        findCouncelor.setOnClickListener(v -> openFragment(new Book()));
        btnQuickChat.setOnClickListener(v -> openFragment(new AiSupport()));

        return view;

    }

    private void openFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, fragment) // fragment_container = your main FrameLayout id
                .addToBackStack(null) // enables back navigation
                .commit();
    }
}