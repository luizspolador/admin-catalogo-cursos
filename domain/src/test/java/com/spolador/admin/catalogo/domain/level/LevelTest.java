package com.spolador.admin.catalogo.domain.level;

import com.spolador.admin.catalogo.domain.exceptions.DomainException;
import com.spolador.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LevelTest {

    @Test
    public void givenValidParams_whenCallNewLevel_shouldInstantiateALevel() {
        final var expectedName = "Beginner";
        final var expectedIsActive = true;
        final var expectedCategories = 0;

        final var actualLevel = Level.newLevel(expectedName, expectedIsActive);

        Assertions.assertNotNull(actualLevel);
        Assertions.assertNotNull(actualLevel.getId());
        Assertions.assertEquals(expectedName, actualLevel.getName());
        Assertions.assertEquals(expectedIsActive, actualLevel.isActive());
        Assertions.assertEquals(expectedCategories, actualLevel.getCategories().size());
        Assertions.assertNotNull(actualLevel.getCreatedAt());
        Assertions.assertNotNull(actualLevel.getUpdatedAt());
        Assertions.assertNull(actualLevel.getDeletedAt());
    }

    @Test
    public void givenInvalidNullName_whenCallNewLevelAndValidate_shouldReceiveAnError() {
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualLevel = Level.newLevel(expectedName, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            actualLevel.validate(new ThrowsValidationHandler());
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnEmptyName_whenCallNewLevelAndValidate_shouldReceiveAnError() {
        final var expectedName = " ";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualLevel = Level.newLevel(expectedName, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            actualLevel.validate(new ThrowsValidationHandler());
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameWithLengthGreaterThan255_whenCallNewLevelAndValidate_shouldReceiveAnError() {
        final var expectedName = """
                asssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssa
                sadasdsdsadasdasdasdasdasdasdaddasfsdfdfdasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
                ssssssssssssssssssssssssssssssssssssssssasadasdsd""";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";

        final var actualLevel = Level.newLevel(expectedName, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            actualLevel.validate(new ThrowsValidationHandler());
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
