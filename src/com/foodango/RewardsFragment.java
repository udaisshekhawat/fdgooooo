package com.foodango;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RewardsFragment extends Fragment {

    public static Fragment newInstance(Context context) {
        RewardsFragment f = new RewardsFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rewards_screen, container, false);

        return rootView;
    }

}
