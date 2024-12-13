package com.example.presencia;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.stream.Collectors;

public class ListaProfeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_profe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Menu menuFragment = Menu.newInstance(UserRole.PROFE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomMenu, menuFragment)
                .commit();


        TextView groupField = findViewById(R.id.autoGroupEdit);
        TextView roomField = findViewById(R.id.autoClassroomEdit);

        String group = getIntent().getStringExtra("group");
        String room = getIntent().getStringExtra("room");
        LinearLayout container = findViewById(R.id.attendanceContainer);

        groupField.setText(group);
        roomField.setText(room);

        List<AlumnoManager.Alumno> alumnos = AlumnoManager.alumnos.stream()
                .filter(alu -> alu.group.equalsIgnoreCase(group))
                .collect(Collectors.toList());

        for (AlumnoManager.Alumno alumno: alumnos) {
            View inflater = LayoutInflater.from(this).inflate(R.layout.attendance_select_item, container, false);

            TextView nameField = inflater.findViewById(R.id.name);
            nameField.setText(alumno.name);

            ViewGroup buttons = inflater.findViewById(R.id.typeContainer);

            for (int i = 0; i < buttons.getChildCount(); i++) {

                ImageButton btn = (ImageButton) buttons.getChildAt(i);

                btn.setOnClickListener(view -> {
                    btn.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
                    for (int j = 0; j < buttons.getChildCount(); j++) {
                        ImageButton siblingButton = (ImageButton) buttons.getChildAt(j);

                        if (siblingButton != btn) {
                            // Cambiar backgroundTintMode de los botones hermanos
                            siblingButton.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                        }
                    }
                });

            }

            container.addView(inflater);


        }


        ImageButton profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(v ->
                startActivity(
                        new Intent(this, CuentaActivity.class)
                ));



    }
}