package ru.clevertec.news.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static generators.factories.PageableFactory.getDefaultPageable;
import static generators.factories.PageableFactory.getDefaultPageableWithSortingByUsernameDesc;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.news.utils.PageableHelper.setPageableUnsorted;

class PageableHelperTest {

    @Test
    void checkSetPageableUnsortedShouldNotChangeWhenAlreadyUnsorted() {
        Pageable actual = setPageableUnsorted(getDefaultPageable());
        Pageable expected = getDefaultPageable();

        assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    void checkSetPageableUnsortedShouldNotChangeAndReferToSameObject() {
        Pageable actual = getDefaultPageable();
        Pageable expected = setPageableUnsorted(actual);

        assertThat(actual).isSameAs(expected);
    }

    @Test
    void checkSetPageableUnsortedShouldChangeToUnsorted() {
        Pageable actual = setPageableUnsorted(getDefaultPageableWithSortingByUsernameDesc());
        Pageable expected = getDefaultPageable();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkSetPageableUnsortedShouldNotChangeAndNotReferToSameObject() {
        Pageable actual = getDefaultPageableWithSortingByUsernameDesc();
        Pageable expected = setPageableUnsorted(actual);

        assertThat(actual).isNotSameAs(expected);
    }
}