package com.example.presencia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HorarioAlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_horario_alumno);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Menu menuFragment = Menu.newInstance(UserRole.ALUMNO);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomMenu, menuFragment)
                .runOnCommit(() -> menuFragment.setPageElementActive(Menu.Page.SCHEDULE))
                .commit();

        String group = UtilRandom.pickRandomString(getResources().getStringArray(R.array.groups));

        TextView dayText = findViewById(R.id.dayText);

        DaySelector daySelector = new DaySelector();
        daySelector.setOnDaySelectedListener(day -> {
            renderSchedulesByGroup(group, day);
            dayText.setText(day.pascalName());
        });


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.daySelector, daySelector)
                .runOnCommit(() -> {
                    renderSchedulesByGroup(group, DaySelector.Day.LUNES);

                    dayText.setText(DaySelector.Day.LUNES.pascalName());
                })
                .commit();

        ImageButton profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(v ->
                startActivity(
                        new Intent(this, CuentaActivity.class)
                ));
    }


    private void renderSchedulesByGroup(String filter, DaySelector.Day day) {

        LinearLayout ll = findViewById(R.id.scheduleContainer);
        ll.removeAllViews();

        for (HorarioManager.Horario h: HorarioManager.horarios.stream()
                .filter(horario -> horario.group.equalsIgnoreCase(filter) && horario.day == day)
                .collect(Collectors.toList())) {

            View inflater = LayoutInflater.from(this).inflate(R.layout.schedule_editable_item, ll, false);

            TextView time = inflater.findViewById(R.id.tv_time);
            TextView group = inflater.findViewById(R.id.tv_group);
            TextView room = inflater.findViewById(R.id.tv_room);

            time.setText(
                    String.format("%s - %s", h.startHour, h.endHour)
            );

            group.setText(h.group);
            room.setText(h.room);

            ll.addView(inflater);
        }
    }
}