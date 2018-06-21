package com.example.baksteen_13.dinopackopening;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        pieChart = (PieChart) findViewById(R.id.idPieChart);

        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(18.5f, "Green"));
        entries.add(new PieEntry(26.7f, "Yellow"));
        entries.add(new PieEntry(24.0f, "Red"));
        entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "Election Results");
        PieData data = new PieData(set);
        data.setValueTextSize(60f);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

    }

}