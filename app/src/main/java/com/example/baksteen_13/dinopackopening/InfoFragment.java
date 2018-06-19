package com.example.baksteen_13.dinopackopening;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InfoFragment extends Fragment{
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.info_layout, container, false);



        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closefragment();
            }
        });
        return myView;
    }

    private void closefragment() {

        FragmentManager fragmentmanager = getFragmentManager();
        fragmentmanager.beginTransaction()
                .replace(R.id.content_frame
                        , new FirstFragment())
                .commit();
    }
}
