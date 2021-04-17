package com.mattmalec.pterodactyl4j;

import java.util.Optional;

public class EnvironmentValue<T> {

    private final T value;

    private EnvironmentValue(T value) {
        this.value = value;
    }

    public Optional<T> get() {
        return Optional.of(value);
    }

    public Optional<String> getAsString() {
        if(value instanceof String)
            return Optional.of((String) value);
        return get().map(String::valueOf);
    }

    public Optional<Integer> getAsInteger() {
        if(value instanceof Integer)
            return Optional.of((Integer) value);
        return Optional.empty();
    }

    public static EnvironmentValue<?> of(Object value) {
        return new EnvironmentValue<>(value);
    }

    public static EnvironmentValue<String> ofString(String value) {
        return new EnvironmentValue<>(value);
    }

    public static EnvironmentValue<Integer> ofInteger(int value) {
        return new EnvironmentValue<>(value);
    }

    @Override
    public String toString() {
        return get().toString();
    }
}
