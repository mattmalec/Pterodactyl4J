package com.mattmalec.pterodactyl4j;

import java.util.function.Consumer;

public interface IPteroAsyncAction<T> {

	void executeAsync();
	void executeAsync(Consumer<? super T> success);
	void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure);

}
