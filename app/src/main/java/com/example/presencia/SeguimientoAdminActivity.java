package com.example.presencia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.stream.Collectors;

public class SeguimientoAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seguimiento_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Menu menuFragment = Menu.newInstance(UserRole.ADMIN);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomMenu, menuFragment)
                .runOnCommit(() -> menuFragment.setPageElementActive(Menu.Page.REGISTRY))
                .commit();


        Spinner groupSpinner = findViewById(R.id.groupFilter);


        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtiene el texto seleccionado
                String group = parent.getItemAtPosition(position).toString();

                // Renderiza cuando cambie el valor
                generateEntriesByGroup(group);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejar caso en que no haya selección
            }
        });


        ImageButton profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(v ->
                startActivity(
                        new Intent(this, CuentaActivity.class)
                ));



    }

    @SuppressLint("DefaultLocale")
    private void generateEntriesByGroup(String group) {

        LinearLayout container = findViewById(R.id.attendanceContainer);
        container.removeAllViews();
        Random random = new Random();
        float totalAttends = 20 + random.nextFloat() * 11;

        for (AlumnoManager.Alumno al: AlumnoManager.alumnos.stream()
                .filter(alumno -> alumno.group.equalsIgnoreCase(group))
                .collect(Collectors.toList())
        ) {
            View inflater = LayoutInflater.from(this)
                    .inflate(R.layout.registry_item, container, false);


            TextView nameField = inflater.findViewById(R.id.name);
            TextView quantField = inflater.findViewById(R.id.quantity);
            TextView totalField = inflater.findViewById(R.id.total);
            TextView percField = inflater.findViewById(R.id.percent);

            // Determinar si este elemento será un caso raro
            boolean isRareCase = random.nextFloat() < 0.2f; // 20% de probabilidad para casos raros

            float basePercentage = isRareCase
                    ? 0.5f + random.nextFloat() * 0.3f // Entre 50% y 80% en casos raros
                    : 0.8f + random.nextFloat() * 0.2f; // Entre 80% y 100% en casos normales

            float assisted = basePercentage * totalAttends;

            float percentage = (assisted / totalAttends) * 100f;

            nameField.setText(al.name);
            quantField.setText(String.format("%.0f", assisted));
            totalField.setText(String.format("%.0f", totalAttends));
            percField.setText(
                    String.format("%.0f%%", percentage)
            );

            LinearLayout square = inflater.findViewById(R.id.dataContainer);
            if (percentage < 80) {
                square.setBackgroundColor(Color.parseColor("#FF8181"));
            }
            else if (percentage >= 80 && percentage <= 82) {

                square.setBackgroundColor(Color.parseColor("#FFE573"));

            }

            container.addView(inflater);

        }



    }
}