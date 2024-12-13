package com.example.presencia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AsistenciaProfeActivity extends AppCompatActivity {


    private Spinner groupSpinner, classroomSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_asistencia_profe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Menu menuFragment = Menu.newInstance(UserRole.PROFE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomMenu, menuFragment)
                .runOnCommit(() -> menuFragment.setPageElementActive(Menu.Page.ATTENDANCE))
                .commit();


        groupSpinner = findViewById(R.id.groupSpinner);
        classroomSpinner = findViewById(R.id.classroomSpinner);
        Button manualReviewButton = findViewById(R.id.manualReviewButton);
        TextView autoGroupEdit = findViewById(R.id.autoGroupEdit);
        TextView autoClassroomEdit = findViewById(R.id.autoClassroomEdit);
        Button reviewButton = findViewById(R.id.reviewButton);

        // Cargando datos aleatorios al seleccion auto
        String grupo = UtilRandom.pickRandomString(
                getResources().getStringArray(R.array.groups)
        );

        String aula = UtilRandom.pickRandomString(
                getResources().getStringArray(R.array.rooms)
        );

        autoGroupEdit.setText(grupo);
        autoClassroomEdit.setText(aula);

        // Configurando botones
        Intent intent = new Intent(this, ListaProfeActivity.class);
        manualReviewButton.setOnClickListener(v -> {
            intent.putExtra("group", groupSpinner.getSelectedItem().toString());
            intent.putExtra("room", classroomSpinner.getSelectedItem().toString());

            startActivity(intent);
        });

        reviewButton.setOnClickListener(v -> {
            intent.putExtra("group", grupo);
            intent.putExtra("room", aula);

            startActivity(intent);
        });


        ImageButton profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(v ->
                startActivity(
                        new Intent(this, CuentaActivity.class)
                ));



    }
}