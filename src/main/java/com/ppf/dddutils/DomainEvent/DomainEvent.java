package com.ppf.dddutils.DomainEvent;

import lombok.NonNull;

import java.io.Serializable;
import java.time.Instant;

public interface DomainEvent extends Serializable {
    /**
     * Returns the time and date on which the event occurred
     */
    @NonNull
    Instant occurredOn();
}
