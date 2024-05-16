package com.ppf.dddutils.AggregateRoot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ppf.dddutils.Domain.DomainObjectId.DomainObjectId;
import com.ppf.dddutils.DomainEvent.DomainEvent;
import com.ppf.dddutils.entity.BaseEntity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.io.Serializable;
import java.util.*;

/**
 * Base class for aggregate root
 * @param <Id> the aggregate root ID type
 */
@NoArgsConstructor
@MappedSuperclass // <1>
public abstract class BaseAggregateRoot<Id extends DomainObjectId> extends BaseEntity<Id> {

    @Transient
    @JsonIgnore
    private final List<DomainEvent> domainEvents = new ArrayList<>(); // <2>

    /**
     *Copy constructor. Please note that any registered domainevent are <b>not</b> copied.
     *
     * @param source the aggregate root to copy
     */
    protected BaseAggregateRoot(@NonNull BaseAggregateRoot<Id> source) {
        super(source);
    }

    /**
     * Constructor for creating new aggregate root
     *
     * @param  id the ID to assign to the aggregate root
     */

    protected BaseAggregateRoot(Id id) {
        super(id);
    }

    /***
     * Registers the given domain event to be published when the aggregate root is presisted
     *
     * @param event
     */
    protected void registerEvent(@NotNull DomainEvent event) { // <3>
        Objects.requireNonNull(event,"event must not be null");
        this.domainEvents.add(event);
    }

    /**
     * Called by the persistence framework to clear all registered domain events once they have been published
     */
    @AfterDomainEventPublication // <4>
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }

    /**
     *Returns all domain events that have been registred for publication. Intented to be used by the persistence
     * framework only
     */
    @DomainEvents // <5>
    protected Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}