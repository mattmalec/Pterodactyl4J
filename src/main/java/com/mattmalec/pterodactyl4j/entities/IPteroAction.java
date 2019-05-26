package com.mattmalec.pterodactyl4j.entities;

public interface IPteroAction<T> extends IPteroAsyncAction<T> {

	T execute();

}
