package com.spolador.admin.catalogo.domain.category;

import com.spolador.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {
    Category create(Category category);
    void deleteById(CategoryID anId);
    Optional<Category> findById(CategoryID anId);

    Category update(Category category);

     Pagination<Category> findAll(CategorySearchQuery aQuery);
}
