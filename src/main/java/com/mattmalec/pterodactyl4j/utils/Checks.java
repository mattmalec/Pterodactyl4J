package com.mattmalec.pterodactyl4j.utils;


import java.util.Collection;

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

    public static void notNumeric(Object o, String name) {
        notNull(o, name);
        if(o instanceof String) {
            notBlank((String) o, name);
        }
        try {
            Long.parseLong(o.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " must be a number!");
        }
    }
    public static void notEmpty(Collection<?> collection, String name) {
        notNull(collection, name);
        if(collection.isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be empty!");
        }
    }
}
