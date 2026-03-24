package com.spolador.admin.catalogo.application.category.retrieve.list;

import com.spolador.admin.catalogo.IntegrationTest;
import com.spolador.admin.catalogo.domain.category.Category;
import com.spolador.admin.catalogo.domain.category.CategorySearchQuery;
import com.spolador.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.spolador.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.stream.Stream;

@IntegrationTest
public class ListCategoriesUseCaseIT {

    @Autowired
    private ListCategoriesUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void muckUp() {
        final var categories = Stream.of(
                Category.newCategory("Backend", null, true),
                Category.newCategory("Frontend", "teste descrição", true),
                Category.newCategory("Devops", "teste devops", true),
                Category.newCategory("Cybersecurity", "teste cybersecurity", true),
                Category.newCategory("Fullstack", "teste fullstack", true),
                Category.newCategory("Management", "teste management", true),
                Category.newCategory("Database", "teste database", true)
        ).map(CategoryJpaEntity::from).toList();
        final var now = Instant.now();

        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setCreatedAt(now.minusSeconds(categories.size() - i));
        }
        categoryRepository.saveAllAndFlush(categories);
    }

    @Test
    public void givenAValidTerm_whenTermDoesNotMatchPrePersisted_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "saasa asasas";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 0;
        final var expectedITotal = 0;

        final var aQuery =
                new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedITotal, actualResult.total());
    }

    @ParameterizedTest
    @CsvSource({
        "back,0,10,1,1,Backend",
        "tack,0,10,1,1,Fullstack",
        "base,0,10,1,1,Database",
        "security,0,10,1,1,Cybersecurity",
        "ment,0,10,1,1,Management",
        "te devo,0,10,1,1,Devops",
    })
    public void givenAValidTerm_whenCallsListCategories_shouldReturnCategoriesFiltered(
            final String expectedTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedITotal,
            final String expectedCategoryName
    ) {
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var aQuery =
                new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedITotal, actualResult.total());
        Assertions.assertEquals(expectedCategoryName, actualResult.items().get(0).name());
    }

    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,7,7,Backend",
            "name,desc,0,10,7,7,Management",
            "createdAt,asc,0,10,7,7,Backend",
            "createdAt,desc,0,10,7,7,Database",
    })
    public void givenAValidSortAndDirection_whenCallsListCategories_thenShouldReturnCategoriesOrdered(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedITotal,
            final String expectedCategoryName
    ) {
        final var expectedTerms = "";

        final var aQuery =
                new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedITotal, actualResult.total());
        Assertions.assertEquals(expectedCategoryName, actualResult.items().get(0).name());
    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,7,Backend;Cybersecurity",
            "1,2,2,7,Database;Devops",
            "2,2,2,7,Frontend;Fullstack",
            "3,2,1,7,Management",
    })
    public void givenAValidPage_whenCallsListCategories_shouldReturnCategoriesPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedITotal,
            final String expectedCategoriesName
    ) {
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTerms = "";

        final var aQuery =
                new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedITotal, actualResult.total());

        int index = 0;
        for (final String expectedName : expectedCategoriesName.split(";")) {
            Assertions.assertEquals(expectedName, actualResult.items().get(index).name());
            index++;
        }

    }
}


