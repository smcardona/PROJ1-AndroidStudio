package com.example.presencia;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static UserRole role = UserRole.ALUMNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        startData();



        EditText emailIn = findViewById(R.id.emailInput);
        EditText passIn = findViewById(R.id.passwordInput);

        ViewGroup logins = findViewById(R.id.loginButtons);

        for (int i = 0; i < logins.getChildCount(); i++) {
            View loginBtn = logins.getChildAt(i);


            loginBtn.setOnClickListener( v -> {
                String emailString = emailIn.getText().toString().trim();
                String passString = passIn.getText().toString().trim();

                Intent intent;

                if (emailString.isEmpty()) {
                    emailIn.setError("Email requerido");
                    emailIn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff9999")));
                    emailIn.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    return;
                }

                if (passString.isEmpty()) {
                    passIn.setError("Contaseña requerida");
                    passIn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff9999")));
                    passIn.setBackgroundTintMode(PorterDuff.Mode.ADD);
                    return;
                }

                if (emailString.startsWith("admin")) { // admin case
                    intent = new Intent(this, HorarioAdminActivity.class);
                    role = UserRole.ADMIN;
                }
                else if (Character.isDigit(emailString.charAt(0))) { // alumno case
                    intent = new Intent(this, AsistenciaAlumnoActivity.class);
                    role = UserRole.ALUMNO;
                }
                else { // profesor case
                    intent = new Intent(this, HorarioProfeActivity.class);
                    role = UserRole.PROFE;
                }

                startActivity(intent);
            });
        }
    }


    private void startData() {

        final String[] professors = getResources().getStringArray(R.array.professors);
        final String[] groups = getResources().getStringArray(R.array.groups);
        final String[] rooms = getResources().getStringArray(R.array.rooms);
        final String[] studentNames = getResources().getStringArray(R.array.student_name_samples);
        final DaySelector.Day[] days = DaySelector.Day.values();

        Random rand = new Random();

        // 1. Asignar horarios a los grupos (14:00 - 21:00)
        for (String group : groups) {
            for (DaySelector.Day day : days) {  // Días de lunes a viernes
                for (int hour = 14; hour <= 21; hour++) {  // Horas de 14:00 a 21:00
                    String start = hour + ":00";
                    String end = (hour + 1) + ":00";
                    String room = rooms[rand.nextInt(rooms.length)];  // Aula aleatoria
                    String professor = professors[rand.nextInt(professors.length)];  // Profesor aleatorio

                    // Añadir horario
                    HorarioManager.add(start, end, group, room, professor, day);
                }
            }
        }

        // 2. Asignar alumnos a los grupos
        int studentIndex = 0;
        for (String group : groups) {
            // Crear un número de alumnos según el tamaño del grupo
            for (int i = 0; i < studentNames.length / groups.length; i++) {
                String studentName = studentNames[studentIndex++];
                AlumnoManager.add(studentName, group);
            }
        }



    }
}