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

package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.exceptions.HttpException;
import com.mattmalec.pterodactyl4j.requests.Requester;
import com.mattmalec.pterodactyl4j.utils.Checks;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;

public class DownloadableFile {

    private final P4J p4j;
    private final File file;
    private final String url;

    public DownloadableFile(P4J p4j, File file, String url) {
        this.p4j = p4j;
        this.file = file;
        this.url = url;
    }

    public CompletableFuture<InputStream> retrieveInputStream() {
        CompletableFuture<InputStream> future = new CompletableFuture<>();
        Request req = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", p4j.getUserAgent())
                .build();

        OkHttpClient requester = p4j.getHttpClient();
        requester.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(new UncheckedIOException(e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    InputStream body = response.body().byteStream();
                    if (!future.complete(body))
                        try {
                            response.close();
                        } catch (Exception ignored) {}
                } else {
                    future.completeExceptionally(new HttpException(response.code() + ": " + response.message()));
                    try {
                        response.close();
                    } catch (Exception ignored) {}
                }
            }
        });
        return future;
    }

    public CompletableFuture<java.io.File> downloadToFile() {
        return downloadToFile(file.getName());
    }


    public CompletableFuture<java.io.File> downloadToFile(String path) {
        Checks.notNull(path, "Path");
        return downloadToFile(new java.io.File(path));
    }

    public CompletableFuture<java.io.File> downloadToFile(java.io.File file) {
        Checks.notNull(file, "File");
        try {
            if (!file.exists())
                file.createNewFile();
            else
                Checks.check(file.canWrite(), String.format("Cannot write to file %s", file.getName()));
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create file", e);
        }

        return retrieveInputStream().thenApplyAsync((stream) -> {
            try (FileOutputStream out = new FileOutputStream(file)) {
                byte[] buf = new byte[1024];
                int count;
                while ((count = stream.read(buf)) > 0)
                    out.write(buf, 0, count);
                return file;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } finally {
                try {
                    stream.close();
                } catch (Exception ignored) {}
            }
        }, p4j.getCallbackPool());
    }

}
