package com.mattmalec.pterodactyl4j.utils;

import com.mattmalec.pterodactyl4j.entities.PteroAction;

import java.util.Optional;

public abstract class Relationed<T> {

    public abstract PteroAction<T> retrieve();

    public abstract Optional<T> get();

    public boolean isPresent() {
        return get().isPresent();
    }

}
