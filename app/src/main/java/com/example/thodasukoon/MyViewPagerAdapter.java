package com.example.thodasukoon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull Book fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position){
           case 0:
               return new BookSession();
           case 1:
               return new BookUpcoming();
           case 2:
               return new BookHistory();
       }
       return new BookUpcoming();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
