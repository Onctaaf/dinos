package com.example.baksteen_13.dinopackopening;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ThirdFragment extends Fragment{
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.third_layout, container, false);

        final EditText et = (EditText) myView.findViewById(R.id.userText);

        Button nextButton = (Button) myView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                intent.putExtra("username", et.getText().toString());
                Bundle b = new Bundle();
                b.putInt("key", 1);
                intent.putExtras(b);
                startActivity(intent);

                /*Snackbar snackbar = Snackbar
                        .make(v, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);

                snackbar.show();*/
            }
        });

        return myView;


    } /*hier stopt de on create*/



}
