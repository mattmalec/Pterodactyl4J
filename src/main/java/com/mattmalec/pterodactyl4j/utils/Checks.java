package com.mattmalec.pterodactyl4j.utils;

/**
 *
 * Class designed for repetitve checking.
 * Mostly used for checking if objects are null
 * and if strings are empty
 *
 */


public class Checks {

    /**
     *
     * Checks if an object is null
     *
     * @param o
     * Object to check null status
     * @param name
     * The user-friendly name of this object
     *
     * @throws IllegalArgumentException
     * If the object is null
     *
     */
    public static void notNull(Object o, String name) {
        if(o == null) {
            throw new IllegalArgumentException(name + " cannot be null!");
        }
    }
    /**
     *
     * Checks if an object is blank.
     * <br />
     * This function will first check if the string is null
     * by calling the {@link Checks#notNull(Object, String) Checks.notNull(o, name)}
     * method and then will proceed to check if the string is blank
     *
     * @param s
     * String to check blank and null status
     * @param name
     * The user-friendly name of this String
     *
     * @throws IllegalArgumentException
     * If the string is either null or empty
     *
     */
    public static void notBlank(String s, String name){
        notNull(s, name);
        if(s.equals("")){
            throw new IllegalArgumentException(name + " cannot be empty!");
        }
    }
    /**
     *
     * Checks if an object is numeric/is a number
     * <br />
     * This function will first check if the object is null
     * by calling the {@link Checks#notNull(Object, String) Checks.notNull(o, name)}
     * method and then will proceed to check if the the object is a String. If it is a String,
     * this method will then check if the String is blank
     * by calling the {@link Checks#notBlank(String, String) Checks.notBlank(s, name)}
     *
     * @param o
     * Number to check numeric and null status
     * @param name
     * The user-friendly name of this object
     *
     * @throws IllegalArgumentException
     * If the object is either null, blank, or cannot be parsed as a long
     *
     */
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
}
