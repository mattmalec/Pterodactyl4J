package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.entities.PteroAction;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PteroActionImpl<T> implements PteroAction<T> {

	private final Supplier<? extends T> execute;

	public static <T> PteroActionImpl<T> onExecute(Supplier<? extends T> execute) {
		return new PteroActionImpl<>(execute);
	}

	private PteroActionImpl(Supplier<? extends T> execute) {
		this.execute = execute;
	}

	@Override
	public T execute() {
		return execute.get();
	}

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
