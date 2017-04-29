package com.ajou.jinwoo.lab3;

import android.app.FragmentManager;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private CalendarView calendarView;
    private EditText dateEditText;
    private EditText timeEditText;
    private Button addButton;
    private Button subButton;
    private Button reserveButton;
    private TextView countTextView;
    private String dateString;
    private int count;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Clear");
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Calendar c = Calendar.getInstance();
        calendarView.setDate(c.getTimeInMillis());
        timePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(c.get(Calendar.MINUTE));
        dateEditText.setText(String.format("%d:%d:%d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)));
        timeEditText.setText(String.format("%d:%d", timePicker.getHour(), timePicker.getMinute()));


        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timePicker = (TimePicker) findViewById(R.id.time_picker);
        calendarView = (CalendarView) findViewById(R.id.calendarView1);
        dateEditText = (EditText) findViewById(R.id.date_edit_text);
        timeEditText = (EditText) findViewById(R.id.time_edit_text);
        addButton = (Button) findViewById(R.id.add_button);
        subButton = (Button) findViewById(R.id.sub_button);
        reserveButton = (Button) findViewById(R.id.reserve_button);
        countTextView = (TextView) findViewById(R.id.count_text_view);

        Date reserveDate = new Date(Calendar.getInstance().getTimeInMillis());
        DateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
        dateString = df.format(reserveDate);

        subButton.setEnabled(false);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count != 0)
                    subButton.setEnabled(true);
                countTextView.setText(count + "명");
            }
        });
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                if (count == 0)
                    subButton.setEnabled(false);
                countTextView.setText(count + "명");
            }
        });
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                ConfirmDialog dialog = ConfirmDialog.newInstance(dateString, timePicker.getHour(), timePicker.getMinute());
                dialog.show(fragmentManager, "DIALOG");


            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateString = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일 ";
                dateEditText.setText(String.format("%d:%d:%d", year, month + 1, dayOfMonth));
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeEditText.setText(String.format("%d:%d", timePicker.getHour(), timePicker.getMinute()));
            }
        });


    }
}
