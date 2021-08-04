package com.mattmalec.pterodactyl4j.requests.action;

import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.requests.*;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.function.BiFunction;

public class RelatablePteroActionImpl<T, R extends Enum<R>> extends PteroActionImpl<T> implements RelatablePteroAction<T, R> {

    protected EnumSet<R> relationships;
    private final Class<R> relationshipType;

    public RelatablePteroActionImpl(Class<R> relationshipType, P4J api, Route.CompiledRoute route, BiFunction<Response, Request<T>, T> handler) {
        super(api, route, handler);
        this.relationshipType = relationshipType;
        this.relationships = EnumSet.allOf(relationshipType);
    }

    @Override
    public final RelatablePteroAction<T, R> include(R relationship) {
        this.relationships = EnumSet.of(relationship);
        return this;
    }

    @SafeVarargs
    @Override
    public final RelatablePteroAction<T, R> include(R relationship, R... relationships) {
        this.relationships = EnumSet.of(relationship, relationships);
        return this;
    }

    @Override
    public RelatablePteroAction<T, R> includeNone() {
        this.relationships = EnumSet.noneOf(relationshipType);
        return this;
    }

    @Override
    protected Route.CompiledRoute finalizeRoute() {

        if (relationships.isEmpty())
            return route;

        StringBuilder includeParams = new StringBuilder();

        Iterator<R> iterator = relationships.iterator();
        while (iterator.hasNext()) {
            R relationship = iterator.next();

            includeParams.append(relationship.name().toLowerCase());
            iterator.remove();

            if (iterator.hasNext()) includeParams.append(",");
        }

        return route.withQueryParams("include", includeParams.toString());
    }
}
