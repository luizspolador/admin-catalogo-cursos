package com.spolador.admin.catalogo.domain.level;

import com.spolador.admin.catalogo.domain.validation.Error;
import com.spolador.admin.catalogo.domain.validation.ValidationHandler;
import com.spolador.admin.catalogo.domain.validation.Validator;

public class LevelValidator extends Validator {
    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 1;

    private final Level level;

    protected LevelValidator(final Level aLevel, final ValidationHandler aHandler) {
        super(aHandler);
        this.level = aLevel;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.level.getName();
        if(name == null) {
            this.validationHandler().append(new com.spolador.admin.catalogo.domain.validation.Error("'name' should not be null"));
            return;
        }
        if(name.isBlank()) {
            this.validationHandler().append(new com.spolador.admin.catalogo.domain.validation.Error("'name' should not be empty"));
            return;
        }
        final int length = name.trim().length();
        if(length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 1 and 255 characters"));
            return;
        }
    }
}
