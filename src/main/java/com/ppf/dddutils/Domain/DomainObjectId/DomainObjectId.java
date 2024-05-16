package com.ppf.dddutils.Domain.DomainObjectId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DomainObjectId<T> implements Serializable {
    private final T id;

    @JsonCreator
    public DomainObjectId(@NonNull T id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
    }

    /**
     *Creates a new , random instance of the given {@code idClass}
     */
    @NonNull
    public static <ID extends DomainObjectId> ID randomId(@NonNull Class<ID> idClass) {
        Objects.requireNonNull(idClass, "idClass must not be null");
        try {
           if (idClass.equals(String.class)) {
               return idClass.getConstructor(String.class).newInstance(UUID.randomUUID().toString());
           } else if (idClass.equals(Long.class)) {
               return idClass.getConstructor(String.class).newInstance(Long.valueOf(UUID.randomUUID().getMostSignificantBits()));

           }
           else {
               throw new IllegalArgumentException("Unsupported id class: " + idClass);
           }
        }
        catch (Exception ex){
            throw new RuntimeException("Could not create new instance of "+idClass,ex);
        }
    }

    /**
     * Returns the ID as a UUID T
     */
    @JsonValue
    @NonNull
    public T toId(){
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DomainObjectId other = (DomainObjectId) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), id);
    }
}
