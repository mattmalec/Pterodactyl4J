package com.mattmalec.pterodactyl4j.requests;

import com.mattmalec.pterodactyl4j.PteroAction;

public interface RelatablePteroAction<T, R extends Enum<R>> extends PteroAction<T> {

    RelatablePteroAction<T, R> include(R relationship);
    RelatablePteroAction<T, R> include(R relationship, R... relationships);
    RelatablePteroAction<T, R> includeNone();

}
