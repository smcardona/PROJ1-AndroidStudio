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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AsistenciaAlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_asistencia_alumno);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Menu menuFragment = Menu.newInstance(UserRole.ALUMNO);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomMenu, menuFragment)
                .runOnCommit(() -> menuFragment.setPageElementActive(Menu.Page.ATTENDANCE))
                .commit();

        String[] grupos = getResources().getStringArray(R.array.groups);
        String[] days = Arrays.stream(DaySelector.Day.values()).map(Enum::name).toArray(String[]::new);

        String randomGroup = UtilRandom.pickRandomString(grupos);
        String randomDay = UtilRandom.pickRandomString(days);

        TextView groupField = findViewById(R.id.groupField);
        groupField.setText(randomGroup);

        renderSchedulesByGroup(randomGroup, DaySelector.Day.fromString(randomDay));

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

            View inflater = LayoutInflater.from(this).inflate(R.layout.schedule_status_item, ll, false);

            TextView time = inflater.findViewById(R.id.tv_time);
            TextView room = inflater.findViewById(R.id.tv_room);
            View container = inflater.findViewById(R.id.detailsContainer);

            time.setText(
                    String.format("%s - %s", h.startHour, h.endHour)
            );

            room.setText(h.room);

            String[] colors = new String[] {"#B5FF8B", "#FFE573", "#FF8181", "#C9C9C9"};
            List<String> colorsList = Arrays.asList(colors);
            Collections.shuffle(colorsList);
            String randomColor = colorsList.get(0);
            container.setBackgroundColor(Color.parseColor(randomColor));

            ll.addView(inflater);
        }
    }
}