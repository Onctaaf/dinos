package com.example.baksteen_13.dinopackopening;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<cards>{

    Context context;

    public arrayAdapter(Context context, int resourceId, List<cards> items){
        super(context, resourceId, items);
    }
}
