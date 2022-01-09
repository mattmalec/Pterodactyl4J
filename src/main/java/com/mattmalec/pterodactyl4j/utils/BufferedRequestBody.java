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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import java.io.IOException;

// thank you jda
public class BufferedRequestBody extends RequestBody
{
    private final Source source;
    private final MediaType type;
    private byte[] data;

    public BufferedRequestBody(Source source, MediaType type) {
        this.source = source;
        this.type = type;
    }

    @Override
    public MediaType contentType() {
        return type;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (data != null) {
            sink.write(data);
            return;
        }

        try (BufferedSource s = Okio.buffer(source)) {
            data = s.readByteArray();
            sink.write(data);
        }
    }
}