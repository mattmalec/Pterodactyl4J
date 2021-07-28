/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.utils;


import java.util.Collection;

public class Checks {

    public static void check(boolean expression, String message) {
        if (!expression)
            throw new IllegalArgumentException(message);
    }

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
