package com.spolador.admin.catalogo.domain.level;

import com.spolador.admin.catalogo.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class LevelID extends Identifier {

    private final String value;

    private LevelID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }
    public static LevelID unique() {
        return LevelID.from(UUID.randomUUID());
    }

    public static LevelID from(final String anId) {
        return new LevelID(anId);
    }

    public static LevelID from (final UUID anId) {
        return new LevelID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final LevelID that = (LevelID) object;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
