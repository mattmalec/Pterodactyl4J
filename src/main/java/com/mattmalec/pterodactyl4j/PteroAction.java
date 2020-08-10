package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.entities.IPteroAction;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class PteroAction<T> implements IPteroAction<T> {

	@Override
	public void executeAsync() {
		this.executeAsync((result) -> {
		});
	}

	@Override
	public void executeAsync(Consumer<? super T> success) {
		this.executeAsync(success, (e) -> {
			System.err.println("An exception occured while making a request");
			e.printStackTrace();
		});
	}

	@Override
	public void executeAsync(Consumer<? super T> success, Consumer<? super Throwable> failure) {
		CompletableFuture.supplyAsync(this::execute)
				.whenCompleteAsync((s, f) -> {
					try {
						if (f == null) {
							success.accept(s);
						} else {
							failure.accept(f);
						}
					} catch (Exception e) {
						System.err.println("A consumer threw an exception");
						e.printStackTrace();
					}
				});
	}
}
