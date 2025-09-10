package com.example.thodasukoon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter2 extends FragmentStateAdapter {


    public MyViewPagerAdapter2(@NonNull resource2 fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AudioResource();
            case 1:
                return new VideoResource();
            case 2:
                return new TextResource();
        }
        return new BookUpcoming();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
