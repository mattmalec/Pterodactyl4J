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

package com.mattmalec.pterodactyl4j;

/**
 * Contains information to this specific build of P4J.
 */
public class P4JInfo {

    public static final String VERSION_MAJOR = "@MAJOR@";
    public static final String VERSION_MINOR = "@MINOR@";
    public static final String VERSION_BUILD = "@BUILD@";

    public static final String VERSION = VERSION_MAJOR.startsWith("@") ? "DEV" :
            String.format("%s.%s_%s", VERSION_MAJOR, VERSION_MINOR, VERSION_BUILD);

}
