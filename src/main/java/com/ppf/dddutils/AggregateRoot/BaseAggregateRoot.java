package com.ppf.dddutils.AggregateRoot;

import com.ppf.dddutils.Domain.DomainObjectId.DomainObjectId;
import com.ppf.dddutils.entity.BaseEntity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.io.Serializable;
import java.util.*;

@MappedSuperclass // <1>
public abstract class BaseAggregateRoot<Id extends DomainObjectId> extends BaseEntity<Id> {

    private final @Transient List<Object> domainEvents = new ArrayList<>(); // <2>

    protected void registerEvent(@NotNull Object event) { // <3>
        domainEvents.add(Objects.requireNonNull(event));
    }

    @AfterDomainEventPublication // <4>
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }

    @DomainEvents // <5>
    protected Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}