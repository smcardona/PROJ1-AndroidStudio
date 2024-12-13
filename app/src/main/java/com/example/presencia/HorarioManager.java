package com.example.presencia;

import java.util.ArrayList;

public class HorarioManager {

    public static final ArrayList<Horario> horarios = new ArrayList<Horario>();

    public static void add (String start, String end, String group, String room, String profesor, DaySelector.Day day) {
        horarios.add(new Horario(start, end, group, room, profesor, day));
    }

    public static class Horario {
        public final String startHour;
        public final String endHour;
        public final String group;
        public final String room;
        public final String profesor;
        public final DaySelector.Day day;

        public Horario(String start, String end, String group, String room, String profesor, DaySelector.Day day) {
            startHour = start;
            endHour = end;
            this.group = group;
            this.room = room;
            this.profesor = profesor;
            this.day = day;

        }

        public String getGroup() { return group; }


    }
}
