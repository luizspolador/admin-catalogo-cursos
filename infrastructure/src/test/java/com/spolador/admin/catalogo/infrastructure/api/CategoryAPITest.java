package com.spolador.admin.catalogo.infrastructure.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spolador.admin.catalogo.ControllerTest;
import com.spolador.admin.catalogo.application.category.create.CreateCategoryOutput;
import com.spolador.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.spolador.admin.catalogo.domain.category.CategoryID;
import com.spolador.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import io.vavr.API;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static io.vavr.API.Right;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        final var expectedName = "Backend";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var anInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Right(CreateCategoryOutput.from("321")));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(anInput));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/categories/321"),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        jsonPath("$.id", Matchers.equalTo("321"))
                );
        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                && Objects.equals(expectedDescription, cmd.description())
                && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }
}
