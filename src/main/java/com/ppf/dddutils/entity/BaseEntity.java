package com.ppf.dddutils.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ppf.dddutils.Domain.DomainObjectId.DomainObjectId;
import jakarta.annotation.Nullable;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
@MappedSuperclass // <1>
public abstract class BaseEntity<ID extends DomainObjectId>  extends AbstractPersistable<ID>{ // <2>

    @Id
    @JsonProperty("id")
    protected ID id;

    /**
     * copy constructor
     *
     * @param source the entity to copy from
     */
    protected BaseEntity(@NonNull BaseEntity<ID> source)
    {
        Objects.requireNonNull(source,"source must not be null");
        this.id = source.getId();
    }

    /**
     * Constructor for creating new entites
     *
     * @param  id the ID to assign to the entity
     */
    protected BaseEntity(@NonNull ID id){
        this.id = Objects.requireNonNull(id,"id must not be null");
    }

    @Override
    @NonNull
    public ID getId() {
        return id;
    }
}