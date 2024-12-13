package com.example.presencia;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu extends Fragment {

    private static final String ARG_ROLE = "role";
    private UserRole type = UserRole.ALUMNO;

    public Menu() {
        // Required empty public constructor
    }

    public static Menu newInstance(UserRole role) {
        Menu menu = new Menu();
        Bundle args = new Bundle();
        menu.type = role;
        args.putSerializable(ARG_ROLE, role); // Guarda el rol como argumento
        menu.setArguments(args);
        return menu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        configureMenu(view, type);

        return view;
    }

    public void setPageElementActive(Page page) {
        View view = requireView();
        int bgColor = Color.parseColor("#06042C");
        ImageButton btn = view.findViewById(page.iconId);

        Drawable drawable = btn.getDrawable();

        btn.setBackgroundResource(R.drawable.menu_item_bg);
        drawable.setColorFilter(bgColor,PorterDuff.Mode.SRC_IN);
    }

    private void configureMenu(View view, UserRole type) {
        LinearLayout scheduleNav = view.findViewById(R.id.scheduleNav);
        LinearLayout attendanceNav = view.findViewById(R.id.attendanceNav);
        LinearLayout registryNav = view.findViewById(R.id.registryNav);

        // Configurar comportamientos según el rol
        switch (type) {
            case ADMIN:
                System.out.println("MENU ADMIN");
                scheduleNav.setOnClickListener(v -> startActivity(new Intent(getContext(), HorarioAdminActivity.class)));
                attendanceNav.setOnClickListener(v -> startActivity(new Intent(getContext(), AsistenciaAdminActivity.class)));
                registryNav.setOnClickListener(v -> startActivity(new Intent(getContext(), SeguimientoAdminActivity.class)));
                break;

            case PROFE:
                System.out.println("MENU PROFE");
                scheduleNav.setOnClickListener(v -> startActivity(new Intent(getContext(), HorarioProfeActivity.class)));
                attendanceNav.setOnClickListener(v -> startActivity(new Intent(getContext(), AsistenciaProfeActivity.class)));
                registryNav.setOnClickListener(v -> startActivity(new Intent(getContext(), SeguimientoProfeActivity.class)));
                break;

            case ALUMNO:
                System.out.println("MENU ALUMNO");
                scheduleNav.setOnClickListener(v -> startActivity(new Intent(getContext(), HorarioAlumnoActivity.class)));
                attendanceNav.setOnClickListener(v -> startActivity(new Intent(getContext(), AsistenciaAlumnoActivity.class)));
                registryNav.setVisibility(View.GONE); // Ocultar Seguimiento
                break;
        }
    }


    public enum Page {
        SCHEDULE(R.id.scheduleNav, R.id.scheduleIcon),
        ATTENDANCE(R.id.attendanceNav, R.id.attendanceIcon),
        REGISTRY(R.id.registryNav, R.id.registryIcon);

        public final int viewId;
        public final int iconId;

        private Page (int viewId, int iconId) {
            this.viewId = viewId;
            this.iconId = iconId;
        }
    }

}