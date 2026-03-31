package com.spolador.admin.catalogo.infrastructure.category.presenters;

import com.spolador.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import com.spolador.admin.catalogo.application.category.retrieve.list.CategoryListOutput;
import com.spolador.admin.catalogo.infrastructure.category.models.CategoryResponse;
import com.spolador.admin.catalogo.infrastructure.category.models.CategoryListResponse;

public interface CategoryApiPresenter {



    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
