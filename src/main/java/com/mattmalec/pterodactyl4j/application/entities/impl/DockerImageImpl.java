package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.application.entities.DockerImage;

public class DockerImageImpl implements DockerImage {
    private final String name;
    private final String image;

    public DockerImageImpl(String name, String image) {
        this.name = name;
        this.image = image;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImage() {
        return image;
    }
}
