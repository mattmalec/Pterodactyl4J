package com.mattmalec.pterodactyl4j.exceptions;

public class IllegalActionException extends PteroException {

    public IllegalActionException(String message) {
        super(message);
    }

// Save this for later
//
//    public IllegalActionException(String text, JSONObject json) {
//        String message = text + "\n\n";
//        for(Object o : json.getJSONArray("errors")) {
//            JSONObject obj = new JSONObject(o.toString());
//            message += "\t- " + obj.getString("detail") + "\n";
//        }
//        throw new IllegalActionException(message);
//    }

}