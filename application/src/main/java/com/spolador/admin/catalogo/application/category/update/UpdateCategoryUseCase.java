package com.spolador.admin.catalogo.application.category.update;

import com.spolador.admin.catalogo.application.UseCase;
import com.spolador.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
