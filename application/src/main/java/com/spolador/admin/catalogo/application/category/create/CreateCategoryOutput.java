package com.spolador.admin.catalogo.application.category.create;

import com.spolador.admin.catalogo.domain.category.Category;
import com.spolador.admin.catalogo.domain.category.CategoryID;

public record CreateCategoryOutput(
        String id
) {
    public static CreateCategoryOutput from(final String anId) {
        return new CreateCategoryOutput(anId);
    }

    public static CreateCategoryOutput from(final Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId().getValue());
    }
}
