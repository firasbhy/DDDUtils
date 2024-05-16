package com.ppf.dddutils.Repository;

import com.ppf.dddutils.AggregateRoot.BaseAggregateRoot;
import com.ppf.dddutils.Domain.DomainObjectId.DomainObjectId;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean // <1>
public interface BaseRepository<Aggregate extends BaseAggregateRoot<ID>, ID extends DomainObjectId> // <2>
        extends JpaRepository<Aggregate, ID>,  // <3>
        JpaSpecificationExecutor<Aggregate> { // <4>

    default @NotNull Aggregate getById(@NotNull ID id) { // <5>
        return findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
