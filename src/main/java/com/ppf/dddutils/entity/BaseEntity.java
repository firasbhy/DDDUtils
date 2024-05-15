package com.ppf.dddutils.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;
import java.util.Optional;

@MappedSuperclass // <1>
public abstract class BaseEntity<Id extends Serializable> extends AbstractPersistable<Id> { // <2>

    @Version // <3>
    private Long version;

    public @NotNull Optional<Long> getVersion() {
        return Optional.ofNullable(version);
    }

    protected void setVersion(@Nullable Long version) { // <4>
        this.version = version;
    }
}