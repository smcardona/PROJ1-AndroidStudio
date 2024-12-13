package com.example.presencia;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DaySelector#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaySelector extends Fragment {

    private OnDaySelectedListener listener;
    private Day selected = Day.LUNES;


    public DaySelector() {
        // Required empty public constructor
    }


    public static DaySelector newInstance() {
        return new DaySelector();
    }

    public void setOnDaySelectedListener(OnDaySelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_day_selector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ViewGroup choices = view.findViewById(R.id.daySelectContainer);

        for (int i = 0; i < choices.getChildCount(); i++) {

            TextView choice = (TextView) choices.getChildAt(i);
            if (i == 0) {
                setActive(choice);
            }

            choice.setOnClickListener( v -> {
                // cambiar estilo a selected
                setActive(choice);

                // cambiar estilo de hermanos a selector
                for (int s = 0; s < choices.getChildCount(); s++) {
                    TextView sibling = (TextView) choices.getChildAt(s);
                    if (sibling == choice) continue;
                    setUnactive(sibling);

                }

                // Llamar evento onSelect
                listener.onSelected(Day.fromString(choice.getText().toString()));
            });


        }


    }

    private void setActive(TextView view) {
        Context context = this.requireContext();
        selected = Day.fromString(view.getText().toString());

        // Establece el fondo a color negro
        view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black)));
        // Establece el color del texto a blanco
        view.setTextColor(ContextCompat.getColor(context, R.color.white));
    }

    private void setUnactive(TextView view) {
        // Establece el fondo a color blanco
        Context context = this.requireContext();
        view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
        // Establece el color del texto a negro
        view.setTextColor(ContextCompat.getColor(context, R.color.black));
    }

    public Day getSelectedDay () {
        return selected;
    }

    public enum Day {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES;

        public static Day fromString(String name) {
            for (Day day : Day.values()) {
                if (day.name().startsWith(name)) return day;
            }

            throw new RuntimeException("INVALID DAY: There is no existing day that matches Name: "+name);
        }

        public String pascalName() {
            String name = this.name().toLowerCase();
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
    }

    public interface OnDaySelectedListener {
        public void onSelected(DaySelector.Day day);
    }
}