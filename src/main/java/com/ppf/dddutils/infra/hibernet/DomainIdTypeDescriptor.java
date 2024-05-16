package com.ppf.dddutils.infra.hibernet;

import com.ppf.dddutils.Domain.DomainObjectId.DomainObjectId;
import com.sun.istack.NotNull;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.util.Objects;
import java.util.function.Function;

/**
 * Hibernet type descriptor for à {@Link DomainObjectId} subtype.
 *
 * @param <ID> the ID type
 * @see DomainObjectIdType
 */
public class DomainIdTypeDescriptor<ID extends DomainObjectId> extends AbstractTypeDescriptor<ID> {

    private final Function<String,ID> factory;

    /**
     * create a new {@code DomainIdTypeDescriptor}
     *
     * @param type the ID type
     * @param factory a factory for creating nex ID instances
     */
    protected DomainIdTypeDescriptor(@NotNull Class<ID> type, @NotNull Function<String,ID> factory) {
        super(type);
        this.factory = Objects.requireNonNull(factory, "factory must not be null");
    }

    @Override
    public String toString(ID value) {
        return value.toString();
    }

    @Override
    public ID fromString(String string) {
        return factory.apply(string);
    }

    @Override
    public <X> X unwrap(ID value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return  null;
        }
        if (type.isAssignableFrom(getJavaType())) {
            return type.cast(value);
        }
        if (type.isAssignableFrom(String.class)) {
            return type.cast(toString(value));
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> ID wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (getJavaType().isInstance(value)) {
            return getJavaType().cast(value);
        }
        if (value instanceof DomainObjectId) {
            return fromString((String) value);
        }
        throw unknownWrap(value.getClass());
    }
}
