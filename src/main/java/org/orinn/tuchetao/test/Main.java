package org.orinn.tuchetao.test;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 15; i++) {
            map.put("num", i);
        }

        System.out.println(map);
    }

}