package com.spolador.admin.catalogo.domain.level;

import com.spolador.admin.catalogo.domain.AggregateRoot;
import com.spolador.admin.catalogo.domain.category.CategoryID;
import com.spolador.admin.catalogo.domain.exceptions.NotificationException;
import com.spolador.admin.catalogo.domain.validation.ValidationHandler;
import com.spolador.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level extends AggregateRoot<LevelID> {
    private String  name;
    private boolean active;
    private List<CategoryID> categories;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public Level(
            final LevelID anId,
            final String aName,
            final boolean isActive,
            final List<CategoryID> cateogires,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        super(anId);
        this.name = aName;
        this.categories = cateogires;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        this.deletedAt = aDeletedAt;

        final var notification = Notification.create();
        validate(notification);

        if(notification.hasError()) {
            throw new NotificationException("Failed to create an Aggregate Level", notification);
        }
    }

    public static Level newLevel(final String aName, final boolean isActive) {
        final var anId  = LevelID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Level(anId, aName, isActive, new ArrayList<>(), now, now, deletedAt);
    }

    public static Level with(final Level aLevel) {
        return new Level(
                aLevel.id,
                aLevel.name,
                aLevel.active,
                new ArrayList<>(aLevel.categories),
                aLevel.createdAt,
                aLevel.updatedAt,
                aLevel.deletedAt
        );
    }

    public static Level with(
            final LevelID anId,
            final String aName,
            final boolean isActive,
            final List<CategoryID> cateogires,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        return new Level(anId, aName, isActive, cateogires, aCreatedAt, aUpdatedAt, aDeletedAt);
    }

    @Override
    public void validate(final ValidationHandler handler) {
    new LevelValidator(this, handler).validate();
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
