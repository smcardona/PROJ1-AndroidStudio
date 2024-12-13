package com.example.presencia;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UtilRandom {

    public static String pickRandomString(String[] list) {
        List<String> listed = Arrays.asList(list);
        Collections.shuffle(listed);
        return listed.get(0);
    }
}
