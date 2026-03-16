package com.spolador.admin.catalogo.application.category.create;

import com.spolador.admin.catalogo.application.UseCase;
import com.spolador.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
