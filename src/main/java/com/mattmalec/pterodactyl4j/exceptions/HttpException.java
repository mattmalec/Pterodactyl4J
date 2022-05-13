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

package com.mattmalec.pterodactyl4j.exceptions;

import com.mattmalec.pterodactyl4j.requests.Response;
import org.json.JSONObject;

public class HttpException extends PteroException {

    public HttpException(String message) {
        super(message);
    }

    private static String create(String text, Response response) {
        if (response.isEmpty())
            return text;
        else
            return formatMessage(text, response.getObject());
    }

    private static String formatMessage(String text, JSONObject json) {
        StringBuilder message = new StringBuilder(text + "\n\n");
        for(Object o : json.getJSONArray("errors")) {
            JSONObject obj = new JSONObject(o.toString());
            message.append("\t- ").append(obj.getString("detail")).append("\n");
        }
        return message.toString();
    }

    public HttpException(String text, Response response) {
        super(create(text, response));
    }
}