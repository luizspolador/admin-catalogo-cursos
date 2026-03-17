package com.spolador.admin.catalogo.application.category.update;

import com.spolador.admin.catalogo.domain.category.Category;
import com.spolador.admin.catalogo.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {
    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId());
    }
}
