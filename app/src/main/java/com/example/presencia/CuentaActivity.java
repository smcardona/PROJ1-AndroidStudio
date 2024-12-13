package com.example.presencia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class CuentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cuenta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Menu menuFragment = Menu.newInstance(MainActivity.role);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomMenu, menuFragment)
                .commit();

        String randomName = UtilRandom.pickRandomString(
                getResources().getStringArray(R.array.student_name_samples)
        );
        String randomGroup = UtilRandom.pickRandomString(
                getResources().getStringArray(R.array.groups)
        );


        TextView rolField = findViewById(R.id.rolField);
        rolField.setText(MainActivity.role.name());


        TextView nameField = findViewById(R.id.nameField);
        TextView mailField = findViewById(R.id.mailField);
        TextView groupField = findViewById(R.id.groupField);
        TextView rfidField = findViewById(R.id.rfidField);

        String rfid = generateRandomRFID();
        rfidField.setText(rfid);

        switch (MainActivity.role) {

            case ADMIN:
                nameField.setText("admin");
                mailField.setText("admin@iticbcn.cat");
                groupField.setText("Administradores");
                break;

            case ALUMNO:
                nameField.setText(randomName);
                mailField.setText(
                        String.format(
                                "2024_%s.%s@iticbcn.cat",
                                randomName.split(" ")[1],
                                randomName.split(" ")[0]
                        ).toLowerCase()
                );
                groupField.setText(randomGroup);
                break;

            case PROFE:
                nameField.setText(randomName);
                mailField.setText(
                        String.format(
                                "%c%s@iticbcn.cat",
                                randomName.split(" ")[0].charAt(0),
                                randomName.split(" ")[1]
                        ).toLowerCase()
                );
                groupField.setText("Profesores");
                break;

        }

        Button logOut = findViewById(R.id.logOutBtn);

        logOut.setOnClickListener(v ->
            startActivity(new Intent(this, MainActivity.class))
        );

    }

    private static String generateRandomRFID() {
        final int RFID_LENGTH = 16;
        StringBuilder rfid = new StringBuilder();
        Random random = new Random();
        String hexChars = "0123456789ABCDEF";

            for (int i = 0; i < RFID_LENGTH; i++) {
            int index = random.nextInt(hexChars.length()); // Genera un índice aleatorio.
            rfid.append(hexChars.charAt(index)); // Agrega un carácter hexadecimal al RFID.
        }

        return rfid.toString();
    }
}