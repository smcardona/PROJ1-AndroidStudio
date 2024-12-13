package com.example.presencia;

import java.util.ArrayList;

public class AlumnoManager {

    public static final ArrayList<Alumno> alumnos = new ArrayList<Alumno>();

    public static void add (String name, String group) {
        alumnos.add(new Alumno(name, group));
    }

    public static class Alumno {
        public final String name;
        public final String group;

        public Alumno(String name, String group) {
            this.group = group;
            this.name = name;
        }

        public String getGroup() { return group; }


    }
}
