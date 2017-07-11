package com.tns.espapp.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tns.espapp.R;
import com.tns.espapp.service.GPSTracker;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TextView welcomeTextView;
    private View view;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(int index) {
        HomeFragment f = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        getLayoutsId();


        BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            // Set action to perform when any menu-item is selected.
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            // Write code to perform some actions.

                            selectFragment(item);

                            return false;
                        }
                    });
        }


        //  TextView setpos =(TextView)v.findViewById(R.id.setpos);
        // String getvalue = getArguments().getString("index");
        //  setpos.setText(getvalue+"");


        return view;
    }

    private void selectFragment(MenuItem item) {
        item.setChecked(true);
        FragmentManager fragmentManager = getFragmentManager();
        switch (item.getItemId()) {
            case R.id.action_taxi:

                if (fragmentManager != null) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    if (ft != null) {
                        ft.replace(R.id.frameLayout_home_frag, new TaxiFormFragment());
                        ft.commit();
                    }
                }
                break;
            case R.id.action_currentlocation:
                 fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    if (ft != null) {
                        ft.replace(R.id.frameLayout_home_frag, new RouteMapFragment());
                        ft.commit();
                    }
                }
                break;
            case R.id.action_servey:
                 fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    if (ft != null) {
                        ft.replace(R.id.frameLayout_home_frag, new FeedBackFragment());
                        ft.commit();
                    }
                }
                break;
        }



    }
    private void getLayoutsId()
    {
        welcomeTextView = (TextView)view.findViewById(R.id.txt_example3);
        setFontFamily();
    }
    private void setFontFamily()
    {
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "arial.ttf");

        welcomeTextView.setTypeface(face);


    }

}