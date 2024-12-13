package com.example.presencia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AsistenciaAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_asistencia_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner groupSpinner = findViewById(R.id.groupSpinner);
        Spinner classroomSpinner = findViewById(R.id.classroomSpinner);
        Menu menuFragment = Menu.newInstance(UserRole.ADMIN);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomMenu, menuFragment)
                .runOnCommit(() -> menuFragment.setPageElementActive(Menu.Page.ATTENDANCE))
                .commit();


        Button manualReviewButton = findViewById(R.id.manualReviewButton);

        manualReviewButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaProfeActivity.class);
            intent.putExtra("group", groupSpinner.getSelectedItem().toString());
            intent.putExtra("room", classroomSpinner.getSelectedItem().toString());

            startActivity(intent);
        });


        ImageButton profile = findViewById(R.id.profileButton);
        profile.setOnClickListener(v ->
                startActivity(
                        new Intent(this, CuentaActivity.class)
                ));
    }
}