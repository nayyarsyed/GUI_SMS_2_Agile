package com.example.gui_sms_2_agile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    private Button scheduleButton;
    private int selectedHour, selectedMinute;

    private Intent mServiceIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, ToastService.class));

        scheduleButton = findViewById(R.id.schedule_button);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScheduleDialog();
            }
        });

        mServiceIntent = new Intent(this, ToastService.class);

        Button stopButton = findViewById(R.id.stop_service_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(mServiceIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(mServiceIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(mServiceIntent);

    }

    private void showScheduleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_schedule, null);

        final RadioButton dailyRadio = dialogView.findViewById(R.id.daily_radio);
        final Button timeButton = dialogView.findViewById(R.id.time_button);

        dailyRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        builder.setView(dialogView)
                .setPositiveButton(R.string.schedule, (dialog, id) -> {
                    if (dailyRadio.isChecked()) {
                        Toast.makeText(MainActivity.this, "Daily schedule set for " +
                                selectedHour + ":" + selectedMinute, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    // User cancelled the dialog
                });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;
                        Toast.makeText(MainActivity.this, "Time set to " + selectedHour +
                                ":" + selectedMinute, Toast.LENGTH_SHORT).show();
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }
}
