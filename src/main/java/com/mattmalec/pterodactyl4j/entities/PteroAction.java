package com.mattmalec.pterodactyl4j.entities;

import java.util.function.Consumer;

public interface PteroAction<T>
{
	T execute();
	void executeAsync();
	void executeAsync(Consumer<? super T> success);
	void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure);
}
