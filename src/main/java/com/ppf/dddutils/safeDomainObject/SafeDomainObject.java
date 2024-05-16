package com.ppf.dddutils.safeDomainObject;

import jakarta.annotation.Nullable;

import java.io.Serializable;

/**
 *Interface for domain objects that use optimistic locking to prevent multiple concurrent sessions from updating the
 * object at the same time
 */
public interface SafeDomainObject extends Serializable {
    /**
     * Returns the optistic looking version of this domain object
     *
     * @return the version or {@code null} if no version has been assigned yet
     */
    @Nullable
    Long version();
}
