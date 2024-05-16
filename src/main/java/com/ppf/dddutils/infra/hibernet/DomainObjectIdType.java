package com.ppf.dddutils.infra.hibernet;

import com.ppf.dddutils.Domain.DomainObjectId.DomainObjectId;
import com.sun.istack.NotNull;
import lombok.NonNull;
import org.hibernate.id.ResultSetIdentifierConsumer;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hibernet custom type for {@link DomainObjectId} subtype. You need to be able to use {@link DomainObjectId} subtype
 * as primary key . YOu have to create one subclass per {@link DomainObjectId} subtype
 *
 * @param <ID> the ID type
 * @see DomainIdTypeDescriptor
 */
public abstract class DomainObjectIdType<ID extends DomainObjectId> extends AbstractSingleColumnStandardBasicType<ID> implements ResultSetIdentifierConsumer {

    /**
     * creates a new {@code DomainObjectIdType}. In your subclass , you should create a default constructor and
     * invoke this constructor from there
     *
     * @param typeDescriptor the {@link DomainIdTypeDescriptor} for the ID type
     */
    public DomainObjectIdType(@NonNull DomainIdTypeDescriptor<ID> typeDescriptor) {
        super((JdbcType) VarcharTypeDescriptor.INSTANCE, (JavaType<ID>) typeDescriptor);
    }

    @Override
    public Serializable consumeIdentifier(ResultSet resultSet) {
        try {
            var id = resultSet.getString(1);
            return (Serializable) getJavaTypeDescriptor().fromString(id);
        }
        catch (SQLException ex){
            throw new IllegalStateException("Could not extract ID from ResultSet",ex);
        }
    }

    @Override
    public String getName() {
        return getJavaTypeDescriptor().getClass().getSimpleName();
    }
}
