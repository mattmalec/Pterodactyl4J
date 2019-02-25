package com.mattmalec.pterodactyl4j.utils;

public class Checks {

    public static void notNull(Object o, String name) {
        if(o == null) {
            throw new IllegalArgumentException(name + " cannot be null!");
        }
    }
    public static void notBlank(String s, String name){
        notNull(s, name);
        if(s.equals("")){
            throw new IllegalArgumentException(name + " cannot be empty!");
        }
    }
}
