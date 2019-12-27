package com.yogi.financeapp.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.yogi.financeapp.R;
import com.yogi.financeapp.RoomDb.ExpenseEntity;
import com.yogi.financeapp.RoomDb.ExpenseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;

public class GraphFragment extends Fragment {

    private static final String[] days = new String[]{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};

    private static final String TAG = "GraphFragment";
    LineChart lineChart;
    private ExpenseViewModel expenseViewModel;
    ArrayList<Entry> incomeValues, expenseValues;
    String tenDaysBefore;
    Date dayAfter;
    List<ExpenseEntity> expenseEntityList;
    LineChartView lineChartView;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        lineChart = view.findViewById(R.id.line_chart);
        incomeValues = new ArrayList<>();
        expenseValues = new ArrayList<>();
        expenseViewModel = ViewModelProviders.of(this).get(ExpenseViewModel.class);
        tenDaysBefore = LocalDate.now().minusDays(7).toString();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dayAfter = format.parse(tenDaysBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        expenseEntityList = expenseViewModel.getAllAmount();


        LineDataSet incomeLineDataSet = new LineDataSet(incomeValues(), "Income Values");
        incomeLineDataSet.setLineWidth(4f);
        incomeLineDataSet.setColor(Color.BLUE);

        LineDataSet expenseLineDataSet = new LineDataSet(getExpenseValues(), "Expense Values");
        expenseLineDataSet.setLineWidth(4f);
        expenseLineDataSet.setColor(Color.RED);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(incomeLineDataSet);
        iLineDataSets.add(expenseLineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();

        return view;

    }


    private ArrayList<Entry> incomeValues() {
        ArrayList<Entry> entries = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();


        int sunday = 0, monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0;


        for (int i = 0; i < expenseEntityList.size(); i++) {

            if (expenseEntityList.get(i).getTransactionType().equals(AddIncomeFragment.CONSTANT_INCOME)
                    && expenseEntityList.get(i).getDate().after(dayAfter)) {

                calendar.setTime(expenseEntityList.get(i).getDate());
                int dayNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                String day = days[dayNumber];


                switch (day) {
                    case "SUNDAY":
                        sunday += expenseEntityList.get(i).getAmount();
                        break;
                    case "MONDAY":
                        monday += expenseEntityList.get(i).getAmount();
                        break;
                    case "TUESDAY":
                        tuesday += expenseEntityList.get(i).getAmount();
                        break;
                    case "WEDNESDAY":
                        Log.d(TAG, "incomeValues: wed: " + i + ' ' + expenseEntityList.get(i).getAmount());
                        wednesday += expenseEntityList.get(i).getAmount();
                        break;
                    case "THURSDAY":
                        thursday += expenseEntityList.get(i).getAmount();
                        break;
                    case "FRIDAY":
                        friday += expenseEntityList.get(i).getAmount();
                        break;
                    case "SATURDAY":
                        saturday += expenseEntityList.get(i).getAmount();
                        break;
                    default:
                        break;
                }

            }

            Log.d(TAG, "incomeValues: day after: " + dayAfter);
            Log.d(TAG, "incomeValues: daysvalues mon " + monday);
            Log.d(TAG, "incomeValues: daysvalues tue " + tuesday);
            Log.d(TAG, "incomeValues: daysvalues wed " + wednesday);
            Log.d(TAG, "incomeValues: daysvalues thurs " + thursday);
            Log.d(TAG, "incomeValues: daysvalues fri " + friday);
            Log.d(TAG, "incomeValues: daysvalues sat " + saturday);
            Log.d(TAG, "incomeValues: daysvalues sun " + sunday);

        }

        entries.add(new Entry(0, sunday));
        entries.add(new Entry(1, monday));
        entries.add(new Entry(2, tuesday));
        entries.add(new Entry(3, wednesday));
        entries.add(new Entry(4, thursday));
        entries.add(new Entry(5, friday));
        entries.add(new Entry(6, saturday));
        return entries;
    }

    private ArrayList<Entry> getExpenseValues() {
        ArrayList<Entry> entries = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();


        int sunday = 0, monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0;


        for (int i = 0; i < expenseEntityList.size(); i++) {

            if (expenseEntityList.get(i).getTransactionType().equals(AddExpenseFragment.CONSTANT_EXPENSE)
                    && expenseEntityList.get(i).getDate().after(dayAfter)) {

                calendar.setTime(expenseEntityList.get(i).getDate());
                int dayNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                String day = days[dayNumber];


                switch (day) {
                    case "SUNDAY":
                        sunday += expenseEntityList.get(i).getAmount();
                        break;
                    case "MONDAY":
                        monday += expenseEntityList.get(i).getAmount();
                        break;
                    case "TUESDAY":
                        tuesday += expenseEntityList.get(i).getAmount();
                        break;
                    case "WEDNESDAY":
                        Log.d(TAG, "incomeValues: wed: " + i + ' ' + expenseEntityList.get(i).getAmount());
                        wednesday += expenseEntityList.get(i).getAmount();
                        break;
                    case "THURSDAY":
                        thursday += expenseEntityList.get(i).getAmount();
                        break;
                    case "FRIDAY":
                        friday += expenseEntityList.get(i).getAmount();
                        break;
                    case "SATURDAY":
                        saturday += expenseEntityList.get(i).getAmount();
                        break;
                    default:
                        break;
                }

            }

            Log.d(TAG, "incomeValues: day after: " + dayAfter);
            Log.d(TAG, "incomeValues: daysvalues mon " + monday);
            Log.d(TAG, "incomeValues: daysvalues tue " + tuesday);
            Log.d(TAG, "incomeValues: daysvalues wed " + wednesday);
            Log.d(TAG, "incomeValues: daysvalues thurs " + thursday);
            Log.d(TAG, "incomeValues: daysvalues fri " + friday);
            Log.d(TAG, "incomeValues: daysvalues sat " + saturday);
            Log.d(TAG, "incomeValues: daysvalues sun " + sunday);


        }


        entries.add(new Entry(0, sunday));
        entries.add(new Entry(1, monday));
        entries.add(new Entry(2, tuesday));
        entries.add(new Entry(3, wednesday));
        entries.add(new Entry(4, thursday));
        entries.add(new Entry(5, friday));
        entries.add(new Entry(6, saturday));
        return entries;
    }


}