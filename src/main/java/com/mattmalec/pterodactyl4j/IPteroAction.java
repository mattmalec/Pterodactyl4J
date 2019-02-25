package com.mattmalec.pterodactyl4j;

public interface IPteroAction<T> extends IPteroAsyncAction<T> {

	T execute();

}
