package com.spolador.admin.catalogo.application.category.retrieve.list;

import com.spolador.admin.catalogo.application.UseCase;
import com.spolador.admin.catalogo.domain.category.CategorySearchQuery;
import com.spolador.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
